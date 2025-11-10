package com.example.nutrifit.data.repository

import com.example.nutrifit.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {

    // Đổi tên collection từ "workouts" thành "exercises"
    private val db = FirebaseFirestore.getInstance().collection("exercises")

    suspend fun getAllWorkouts(): Result<List<Workout>> {
        return try {
            val querySnapshot = db.get().await()
            val workouts = querySnapshot.toObjects(Workout::class.java)
            Result.success(workouts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
