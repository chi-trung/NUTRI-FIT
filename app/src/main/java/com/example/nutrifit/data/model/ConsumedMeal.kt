package com.example.nutrifit.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Represents a single meal item that a user has consumed on a specific day.
 */
data class ConsumedMeal(
    val id: String = "", 
    val mealId: Int = 0,      // The ID of the original meal from the main meal list
    val name: String = "",
    val calories: Int = 0,
    val mealType: String = "", // e.g., "Buổi sáng", "Buổi trưa"
    @ServerTimestamp
    val consumedAt: Date = Date()
)
