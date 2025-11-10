package com.example.nutrifit.data.model

data class Exercise(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val difficulty: String = "",
    val imageUrl: String = "",
    val muscleGroup: String = "",
    val targets: List<String> = emptyList(),
    val videoUrl: String = "",
    var isCompleted: Boolean = false
)
