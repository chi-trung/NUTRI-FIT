package com.example.nutrifit.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class CompletedWorkout(
    val id: String = "",
    val userId: String = "",
    val workoutName: String = "",
    val muscleGroup: String = "",
    @ServerTimestamp
    val completedAt: Date = Date()
)
