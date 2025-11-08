package com.example.nutrifit.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.Calendar

class DailyIntakeRepository {

    private val db = FirebaseFirestore.getInstance().collection("daily_intakes")

    suspend fun getDailyIntake(userId: String, date: Date): Result<DailyIntake?> {
        return try {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val startOfDay = calendar.apply { set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0) }.time
            val endOfDay = calendar.apply { set(Calendar.HOUR_OF_DAY, 23); set(Calendar.MINUTE, 59); set(Calendar.SECOND, 59) }.time

            val query = db
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("date", startOfDay)
                .whereLessThanOrEqualTo("date", endOfDay)
                .limit(1)
                .get()
                .await()

            val document = query.documents.firstOrNull()
            val dailyIntake = document?.toObject(DailyIntake::class.java)?.copy(id = document.id)
            Result.success(dailyIntake)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addOrUpdateDailyIntake(userId: String, caloriesToAdd: Int): Result<Unit> {
        return try {
            val today = Date()
            val existingIntakeResult = getDailyIntake(userId, today)
            
            if (existingIntakeResult.isSuccess) {
                val existingIntake = existingIntakeResult.getOrNull()
                if (existingIntake != null) {
                    // Update existing
                    val newTotal = existingIntake.totalCalories + caloriesToAdd
                    db.document(existingIntake.id).update("totalCalories", newTotal).await()
                } else {
                    // Create new
                    val newIntake = DailyIntake(
                        userId = userId,
                        date = today,
                        totalCalories = caloriesToAdd
                    )
                    db.add(newIntake).await()
                }
                Result.success(Unit)
            } else {
                Result.failure(existingIntakeResult.exceptionOrNull() ?: Exception("Failed to get daily intake"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
