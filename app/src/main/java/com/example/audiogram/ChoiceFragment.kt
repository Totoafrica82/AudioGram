package com.example.audiogram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class ChoiceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choice, container, false)
        val startButton: Button = view.findViewById(R.id.Button4)
        val calButton: Button = view.findViewById(R.id.Button5)
        if (startButton != null) {
            startButton.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.action_choiceFragment_to_calibrationFragment)
            }
        }
            if (calButton != null) {
                calButton.setOnClickListener {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_choiceFragment_to_examinationFragment)
                }
            }


        return view
    }
}


