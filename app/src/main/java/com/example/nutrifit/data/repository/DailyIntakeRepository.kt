package com.example.nutrifit.data.repository

import com.example.nutrifit.data.model.ConsumedMeal
import com.example.nutrifit.data.model.DailyIntake
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.Calendar

class DailyIntakeRepository {

    private val db = FirebaseFirestore.getInstance().collection("daily_intakes")

    // *** CODE MỚI: Sử dụng Flow để lắng nghe thay đổi thời gian thực ***
    fun getDailyIntakeFlow(userId: String, date: Date): Flow<Result<DailyIntake?>> = callbackFlow {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val startOfDay = calendar.apply { set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0) }.time
        val endOfDay = calendar.apply { set(Calendar.HOUR_OF_DAY, 23); set(Calendar.MINUTE, 59); set(Calendar.SECOND, 59) }.time

        val query = db
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", startOfDay)
            .whereLessThanOrEqualTo("date", endOfDay)
            .limit(1)

        val listener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                trySend(Result.failure(e))
                return@addSnapshotListener
            }

            val document = snapshot?.documents?.firstOrNull()
            if (document == null) {
                trySend(Result.success(null))
            } else {
                val id = document.id
                val docUserId = document.getString("userId") ?: ""
                val docDate = document.getDate("date")
                @Suppress("UNCHECKED_CAST")
                val consumedMealsList = document.get("consumedMeals") as? List<HashMap<String, Any>> ?: emptyList()

                val meals = consumedMealsList.mapNotNull { mealMap ->
                    val mealCalories = (mealMap["calories"] as? Long)?.toInt() ?: 0
                    ConsumedMeal(
                        id = mealMap["id"] as? String ?: "",
                        mealId = (mealMap["mealId"] as? Long)?.toInt() ?: 0,
                        name = mealMap["name"] as? String ?: "",
                        calories = mealCalories,
                        mealType = mealMap["mealType"] as? String ?: "",
                        consumedAt = (mealMap["consumedAt"] as? com.google.firebase.Timestamp)?.toDate() ?: Date()
                    )
                }

                val dailyIntake = DailyIntake(
                    id = id,
                    userId = docUserId,
                    date = docDate,
                    consumedMeals = meals
                )
                trySend(Result.success(dailyIntake))
            }
        }

        awaitClose { listener.remove() }
    }

    // Hàm cũ (getDailyIntake) vẫn được giữ lại để các hàm khác sử dụng
    private suspend fun getDailyIntake(userId: String, date: Date): Result<DailyIntake?> {
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
            if (document == null) {
                return Result.success(null)
            }
            
            val id = document.id
            val docUserId = document.getString("userId") ?: ""
            val docDate = document.getDate("date")
            @Suppress("UNCHECKED_CAST")
            val consumedMealsList = document.get("consumedMeals") as? List<HashMap<String, Any>> ?: emptyList()

            val meals = consumedMealsList.mapNotNull { mealMap ->
                val mealCalories = (mealMap["calories"] as? Long)?.toInt() ?: 0
                ConsumedMeal(
                    id = mealMap["id"] as? String ?: "",
                    mealId = (mealMap["mealId"] as? Long)?.toInt() ?: 0,
                    name = mealMap["name"] as? String ?: "",
                    calories = mealCalories,
                    mealType = mealMap["mealType"] as? String ?: "",
                    consumedAt = (mealMap["consumedAt"] as? com.google.firebase.Timestamp)?.toDate() ?: Date()
                )
            }

            val dailyIntake = DailyIntake(
                id = id,
                userId = docUserId,
                date = docDate,
                consumedMeals = meals
            )

            Result.success(dailyIntake)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addConsumedMeal(userId: String, meal: ConsumedMeal): Result<Unit> {
        return try {
            val today = Date()
            val existingIntakeResult = getDailyIntake(userId, today)

            if (existingIntakeResult.isSuccess) {
                val existingIntake = existingIntakeResult.getOrNull()
                if (existingIntake != null && existingIntake.id.isNotEmpty()) {
                    db.document(existingIntake.id).update("consumedMeals", FieldValue.arrayUnion(meal)).await()
                } else {
                    val newIntake = DailyIntake(
                        userId = userId,
                        date = today,
                        consumedMeals = listOf(meal)
                    )
                    db.add(newIntake).await()
                }
                Result.success(Unit)
            } else {
                Result.failure(existingIntakeResult.exceptionOrNull() ?: Exception("Failed to check for existing intake."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeConsumedMeal(userId: String, meal: ConsumedMeal): Result<Unit> {
        return try {
            val today = Date()
            val existingIntakeResult = getDailyIntake(userId, today)

            if (existingIntakeResult.isSuccess) {
                val existingIntake = existingIntakeResult.getOrNull()
                if (existingIntake != null && existingIntake.id.isNotEmpty()) {
                    db.document(existingIntake.id).update("consumedMeals", FieldValue.arrayRemove(meal)).await()
                    Result.success(Unit)
                } else {
                     Result.failure(Exception("No intake document found for today to remove meal from."))
                }
            } else {
                 Result.failure(existingIntakeResult.exceptionOrNull() ?: Exception("Failed to check for existing intake."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun clearAllConsumedMeals(userId: String, date: Date): Result<Unit> {
        return try {
            val existingIntakeResult = getDailyIntake(userId, date)

            if (existingIntakeResult.isSuccess) {
                val existingIntake = existingIntakeResult.getOrNull()
                if (existingIntake != null && existingIntake.id.isNotEmpty()) {
                    db.document(existingIntake.id).update("consumedMeals", emptyList<ConsumedMeal>()).await()
                }
                Result.success(Unit)
            } else {
                Result.failure(existingIntakeResult.exceptionOrNull() ?: Exception("Failed to check for existing intake."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}