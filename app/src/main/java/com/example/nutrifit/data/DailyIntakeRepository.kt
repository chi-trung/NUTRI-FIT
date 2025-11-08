package com.example.nutrifit.data

import com.google.firebase.firestore.FieldValue
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
            if (document == null) {
                return Result.success(null)
            }
            
            // Manually deserialize due to nested custom object
            val id = document.id
            val docUserId = document.getString("userId") ?: ""
            val docDate = document.getDate("date")
            val consumedMealsList = document.get("consumedMeals") as? List<HashMap<String, Any>> ?: emptyList()

            val meals = consumedMealsList.mapNotNull { mealMap ->
                // Firestore converts Int to Long, so we need to handle that
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
                    // Document exists, update it by adding the new meal to the array.
                    db.document(existingIntake.id).update("consumedMeals", FieldValue.arrayUnion(meal)).await()
                } else {
                    // No document for today, create a new one.
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
    
    // We will need this function later to remove meals
    suspend fun removeConsumedMeal(userId: String, meal: ConsumedMeal): Result<Unit> {
        return try {
            val today = Date()
            val existingIntakeResult = getDailyIntake(userId, today)

            if (existingIntakeResult.isSuccess) {
                val existingIntake = existingIntakeResult.getOrNull()
                if (existingIntake != null && existingIntake.id.isNotEmpty()) {
                    // Document exists, remove the meal from the array.
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
}