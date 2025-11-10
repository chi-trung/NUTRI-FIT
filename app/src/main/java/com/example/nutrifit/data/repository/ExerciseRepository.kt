package com.example.nutrifit.data.repository

import com.example.nutrifit.data.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExerciseRepository {

    private val db = FirebaseFirestore.getInstance().collection("exercises")

    suspend fun getAllExercises(): Result<List<Exercise>> {
        return try {
            val querySnapshot = db.get().await()
            val exercises = querySnapshot.documents.mapNotNull { document ->
                val exercise = document.toObject(Exercise::class.java)
                // Gán ID của document vào đối tượng Exercise
                exercise?.copy(id = document.id)
            }
            Result.success(exercises)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}