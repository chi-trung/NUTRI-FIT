package com.example.nutrifit.data

// This is the single source of truth for the Meal data structure.
// All other files will import this.
data class Meal(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val imageRes: String = "",      // Name from Firestore (e.g., "klug")
    var imageResId: Int = 0,       // Resolved drawable ID, defaults to 0
    val calories: Int = 0,
    val time: String = "",
    val category: String = "",
    val suitableGoals: List<String> = emptyList() // To hold the goals
)
