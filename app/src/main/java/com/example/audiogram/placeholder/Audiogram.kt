package com.example.audiogram.placeholder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audiogram(val leftEarAmplitudeLevels: ArrayList<Pair<Int, Int>>,
                     val rightEarAmplitudeLevels: ArrayList<Pair<Int, Int>>) : Parcelable
