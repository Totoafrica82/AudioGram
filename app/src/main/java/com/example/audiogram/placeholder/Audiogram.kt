package com.example.audiogram.placeholder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audiogram(val leftEarAmplitudeLevels: ArrayList<Pair<Int, Double>>,
                     val rightEarAmplitudeLevels: ArrayList<Pair<Int, Double>>) : Parcelable
