package com.example.audiogram

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlin.math.sin

class CalibrationFragment : Fragment() {
    private lateinit var tone1: ShortArray
    private lateinit var tone2: ShortArray
    private var audioTrack: AudioTrack? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calibration, container, false)
        val startButton: Button = view.findViewById(R.id.startButton)

        //tu wyzej masz przycisk, to wez go tam podepnij

        startButton.setOnClickListener {
            startCalibration()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        initializeTones()
    }

    override fun onPause() {
        super.onPause()
        releaseAudioTrack()
    }

    private fun initializeTones() {
        val toneFrequency = 1000.0

        tone1 = generateTone(toneFrequency)
        tone2 = generateTone(toneFrequency * 2)
    }

    private fun generateTone(frequency: Double): ShortArray {
        val sampleRate = 44100
        val toneDuration = 1.0 // 1 second
        val amplitude = 0.5
        val bufferSize = (sampleRate * toneDuration).toInt()
        val samples = ShortArray(bufferSize)

        val angularFrequency = 2.0 * Math.PI * frequency / sampleRate
        for (i in 0 until bufferSize) {
            val sample = (amplitude * sin(angularFrequency * i)).toInt().toShort()
            samples[i] = sample
        }

        return samples
    }

    private fun startCalibration() {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val format = AudioFormat.Builder()
            .setSampleRate(44100)
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()

        val bufferSize = tone1.size * 2
        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(attributes)
            .setAudioFormat(format)
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        audioTrack?.play()

        // Play alternating tones until button is pressed
        while (true) {
            audioTrack?.write(tone1, 0, tone1.size)
            audioTrack?.write(tone2, 0, tone2.size)
            if (audioTrack?.playState == AudioTrack.PLAYSTATE_STOPPED) {
                break
            }
        }
    }

    private fun releaseAudioTrack() {
        audioTrack?.stop()
        audioTrack?.release()
        audioTrack = null
    }


}
