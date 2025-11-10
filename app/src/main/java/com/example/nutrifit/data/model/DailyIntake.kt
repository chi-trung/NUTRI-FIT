package com.example.nutrifit.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Represents a user's total dietary intake for a single day.
 */
data class DailyIntake(
    val id: String = "",
    val userId: String = "",
    @ServerTimestamp
    val date: Date? = null,
    val consumedMeals: List<ConsumedMeal> = emptyList(),
    // totalCalories can now be calculated on the fly
) {
    // Helper function to calculate total calories
    fun getTotalCalories(): Int = consumedMeals.sumOf { it.calories }
}
