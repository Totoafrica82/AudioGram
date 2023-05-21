package com.example.audiogram

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlin.math.pow
import kotlin.math.sin

class CalibrationFragment : Fragment() {
    private lateinit var tone1: ShortArray
    private lateinit var tone2: ShortArray
    private var audioTrack: AudioTrack? = null
    private lateinit var calibrationMessage: TextView
    private lateinit var calibrationTimer: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calibration, container, false)
        val startButton: Button = view.findViewById(R.id.startButton)
        val exitButton: Button = view.findViewById(R.id.stopButton)
        calibrationMessage = view.findViewById(R.id.calibrationMessage)
        calibrationTimer = view.findViewById(R.id.calibrationTimer)

        startButton.setOnClickListener {
            startCalibration()
            startButton.isEnabled = false
            calibrationMessage.visibility = View.VISIBLE
            calibrationTimer.visibility = View.VISIBLE
        }

        exitButton.setOnClickListener {
            findNavController().navigate(R.id.action_calibrationFragment_to_choiceFragment)
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
            val sample = amplitude * (frequency * sin(angularFrequency * i)).toInt().toShort()
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

        val bufferSize = tone1.size * 2
        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(attributes)
            .setAudioFormat(format)
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        val timer = object : CountDownTimer(30000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                calibrationTimer.text = seconds.toString()

                if (seconds.toInt() % 2 == 0) {
                    audioTrack?.write(tone1, 0, tone1.size)
                } else {
                    audioTrack?.write(tone2, 0, tone2.size)
                }
            }

            override fun onFinish() {
                calibrationMessage.visibility = View.GONE
                calibrationTimer.visibility = View.GONE
                releaseAudioTrack()
            }
        }

        audioTrack?.play()
        timer.start()
    }

    private fun releaseAudioTrack() {
        audioTrack?.stop()
        audioTrack?.release()
        audioTrack = null
    }
}
