package com.example.nutrifit.data.model

data class Meal(
    val id: Int,
    val name: String,
    val description: String,
    val imageRes: String,
    var imageResId: Int = 0,
    val calories: Int,
    val time: String,
    val category: String,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
    val difficulty: String = "Dễ",
    val instructions: String = ""
)
