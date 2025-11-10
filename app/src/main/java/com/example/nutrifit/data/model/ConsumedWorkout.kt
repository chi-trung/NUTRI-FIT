package com.example.nutrifit.data.model

data class ConsumedWorkout(
    val id: String = "",
    val name: String = "",
    val caloriesBurned: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
