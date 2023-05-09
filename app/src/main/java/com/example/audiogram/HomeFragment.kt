package com.example.audiogram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.navigation.Navigation


class HomeFragment : Fragment() {

//    private lateinit var button2 : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var button2 = getView()?.findViewById<Button>(R.id.button2)
        if (button2 != null) {
            button2.setOnClickListener{
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mainFragment)
            }
        }
    }


    }
