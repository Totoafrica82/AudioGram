package com.example.audiogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.audiogram.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(R.layout.fragment_main) {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }




}


