package com.example.audiogram

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.viewModels
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TextView
import com.example.audiogram.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

}

class FragmentActivity : AppCompatActivity() {

    private lateinit var ageSlider: SeekBar
    private lateinit var ageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ageSlider = findViewById(R.id.age_slider)
        ageText = findViewById(R.id.age_text)

        ageSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                ageText.text = progress.toString()
            }
            // nie działa zmiana wieku
            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

    }
}
