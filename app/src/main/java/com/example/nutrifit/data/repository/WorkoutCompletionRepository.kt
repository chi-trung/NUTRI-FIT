package com.example.nutrifit.data.repository

import com.example.nutrifit.data.model.CompletedWorkout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutCompletionRepository {

    private val db = FirebaseFirestore.getInstance().collection("completed_workouts")

    suspend fun markWorkoutAsComplete(completedWorkout: CompletedWorkout): Result<Unit> {
        return try {
            db.add(completedWorkout).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
