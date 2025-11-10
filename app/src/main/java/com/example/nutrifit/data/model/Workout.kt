package com.example.nutrifit.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val name: String = "",
    val description: String = "",
    val muscleGroup: String = "",
    val difficulty: String = "",
    val targets: List<String> = emptyList(),
    val imageUrl: String = "",
    val videoUrl: String = "",
    var imageResId: Int = 0,
    var videoResId: Int = 0
) : Parcelable
