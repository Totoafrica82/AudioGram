package com.example.audiogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class MainFragment : Fragment() {
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var seekBar: SeekBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        textView = view.findViewById(R.id.textView1)
        progressBar = view.findViewById(R.id.progressBar)
        seekBar = view.findViewById(R.id.seekBar)

        seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressBar?.progress = progress
                textView?.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonMain = getView()?.findViewById<Button>(R.id.button_main)
        if (buttonMain != null) {
            buttonMain.setOnClickListener{
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_choiceFragment)
            }
        }
    }
}
