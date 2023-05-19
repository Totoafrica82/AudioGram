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
import kotlin.math.pow
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
        val amplitude = 1.0
        val zmiana = 10.0.pow(0.25)
        tone1 = generateTone(amplitude)
        tone2 = generateTone(amplitude * zmiana)
    }

    private fun generateTone(amplitude: Double): ShortArray {
        val frequency = 1000.0
        val sampleRate = 44100
        val toneDuration = 1.0 // 1 second
        val bufferSize = (sampleRate * toneDuration).toInt()
        val samples = ShortArray(bufferSize)

        val angularFrequency = 2.0 * Math.PI * frequency / sampleRate
        for (i in 0 until bufferSize) {
            val sample = amplitude*(frequency * sin(angularFrequency * i)).toInt().toShort()
            samples[i] = sample.toInt().toShort()
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

        val bufferSize = tone1.size*2
        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(attributes)
            .setAudioFormat(format)
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        audioTrack?.play()

        while (true) {
                for (i in 1..32767) {
                    if (i % 2 == 0) {
                        Thread.sleep(1000)
                        audioTrack?.write(tone1, 0, tone1.size)
                    } else {
                        Thread.sleep(1000)
                        audioTrack?.write(tone2, 0, tone2.size)
                    }
                }
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
