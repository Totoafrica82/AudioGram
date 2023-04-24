package com.example.audiogram

import android.os.Bundle
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
    private var _biding: FragmentMainBinding? = null
    private val binding by lazy { _biding!! }

   // private val viewModel: MainFragmentViewModel by viewModels()
    //todo add view model,destroy view model

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View{
        _biding = FragmentMainBinding.inflate(inflater,container, false)
        return binding.root

    }
    class MainFragment: AppCompatActivity(){

        private lateinit var ageSlider: SeekBar
        private lateinit var ageText: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_main)

            ageSlider = findViewById(R.id.age_slider)
            ageText = findViewById(R.id.age_text)

            ageSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    ageText.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

}
/*
class MainActivity : AppCompatActivity() {
    private lateinit var ageSlider: SeekBar
    private lateinit var ageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ageSlider = findViewById(R.id.age_slider)
        ageText = findViewById(R.id.age_text)

        ageSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                ageText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}
 */