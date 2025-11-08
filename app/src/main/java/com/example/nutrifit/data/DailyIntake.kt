package com.example.nutrifit.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class DailyIntake(
    val id: String = "",
    val userId: String = "",
    @ServerTimestamp
    val date: Date? = null,
    val totalCalories: Int = 0
)