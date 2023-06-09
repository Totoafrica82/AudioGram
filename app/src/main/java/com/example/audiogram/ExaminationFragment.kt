package com.example.audiogram

import android.media.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.audiogram.placeholder.Audiogram
import kotlin.math.pow
import kotlin.math.sin

class ExaminationFragment : Fragment() {
    private val frequency: IntArray = intArrayOf(
        125, 500, 1000, 2000,
        3000, 4000, 6000, 8000, 10000
    )
    private val originalAmplitude = 1.0 // początkowa amplituda dźwięku

    private lateinit var audioTrack: AudioTrack
    private lateinit var earTextView: TextView
    private lateinit var examinationButton: Button
    private lateinit var notHearButton: Button
    private lateinit var endButton: Button

    private val leftEarAmplitudeLevels: ArrayList<Pair<Int, Double>> = ArrayList()
    private val rightEarAmplitudeLevels: ArrayList<Pair<Int, Double>> = ArrayList()

    private var leftEarMode = true // tryb lewego ucha
    private var tabCounter = 0 // zmienna do iteracji przez tablicę częstotliwości
    private var amplitude = originalAmplitude // aktualna amplituda dźwięku

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_examination, container, false)

        earTextView = view.findViewById(R.id.earTextView)
        examinationButton = view.findViewById(R.id.examinationButton)
        notHearButton = view.findViewById(R.id.notHearButton)
        endButton = view.findViewById(R.id.endButton)

        initializeAudioTrack()

        examinationButton.visibility = View.INVISIBLE
        notHearButton.visibility = View.INVISIBLE
        endButton.visibility = View.INVISIBLE

        val startButton: Button = view.findViewById(R.id.startButton)
        startButton.setOnClickListener {
            leftEarMode = true
            tabCounter = 0
            amplitude = originalAmplitude
            earTextView.text = "Lewe ucho"
            generateSound(frequency[tabCounter], true)
            examinationButton.visibility = View.VISIBLE
            notHearButton.visibility = View.VISIBLE
            startButton.visibility = View.INVISIBLE
        }

        examinationButton.setOnClickListener {
            if (leftEarMode) {
                leftEarAmplitudeLevels.add(frequency[tabCounter] to amplitude)
            } else {
                rightEarAmplitudeLevels.add(frequency[tabCounter] to amplitude)
            }
            tabCounter += 1
            if (tabCounter < frequency.size) {
                amplitude = originalAmplitude
                generateSound(frequency[tabCounter], leftEarMode)
            } else {
                if (leftEarMode) {
                    leftEarMode = false
                    tabCounter = 0
                    amplitude = originalAmplitude
                    earTextView.text = "Prawe ucho"
                    generateSound(frequency[tabCounter], leftEarMode)
                } else {
                    earTextView.text = "Koniec pomiaru"
                    endButton.visibility = View.VISIBLE
                    examinationButton.visibility = View.INVISIBLE
                    notHearButton.visibility = View.INVISIBLE
                }
            }
        }

        notHearButton.setOnClickListener {
            increaseAmplitudeBy10dB()
            generateSound(frequency[tabCounter], leftEarMode)
        }

        endButton.setOnClickListener {
            val data = Audiogram(leftEarAmplitudeLevels, rightEarAmplitudeLevels)
            findNavController().navigate(ExaminationFragmentDirections.actionExaminationFragmentToAnalyticsFragment(data))

        }

        return view
    }

    private fun initializeAudioTrack() {
        val sampleRate = 44100
        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        audioTrack.play()
    }

    private fun generateSound(frequency: Int, leftEarMode: Boolean) {
        val sampleRate = 44100
        val duration = 2
        val numSamples = duration * sampleRate
        val sample = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val value =
                (amplitude * sin(2 * Math.PI * i / (sampleRate.toDouble() / frequency)) * Short.MAX_VALUE).toInt()
                    .toShort()
            sample[i] = value
        }

        val buffer = ShortArray(numSamples * 2)
        if (leftEarMode) {
            System.arraycopy(sample, 0, buffer, 0, numSamples)
            System.arraycopy(sample, 0, buffer, numSamples, numSamples)
        } else {
            System.arraycopy(sample, 0, buffer, numSamples, numSamples)
            System.arraycopy(sample, 0, buffer, 0, numSamples)
        }

        audioTrack.write(buffer, 0, buffer.size)
    }

    private fun increaseAmplitudeBy10dB() {
        val amplificationFactor =
            10.0.pow(0.5) // Obliczenie współczynnika wzmacniania

        amplitude = (amplitude * amplificationFactor)// Zwiększenie amplitudy o 10dB
    }

    override fun onDestroyView() {
        super.onDestroyView()
        audioTrack.release()
    }
}