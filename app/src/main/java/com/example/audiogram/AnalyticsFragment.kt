package com.example.audiogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AnalyticsFragment : Fragment(), ExaminationFragment.DataListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicjalizacja widoku fragmentu
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onDataReceived(
        leftEarAmplitudeLevels: ArrayList<Pair<Int, Int>>,
        rightEarAmplitudeLevels: ArrayList<Pair<Int, Int>>
    ) {
        TODO("Not yet implemented")
    }
}