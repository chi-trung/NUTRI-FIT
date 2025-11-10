package com.example.nutrifit.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Represents a single meal item that a user has consumed on a specific day.
 */
data class ConsumedMeal(
    // We can use a random UUID for this if needed, but it's often not necessary if it's only a sub-collection item.
    val id: String = "", 
    val mealId: Int,      // The ID of the original meal from the main meal list
    val name: String,
    val calories: Int,
    val mealType: String, // e.g., "Buổi sáng", "Buổi trưa"
    @ServerTimestamp
    val consumedAt: Date = Date()
)
