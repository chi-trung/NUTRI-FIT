package com.example.nutrifit.data.repository

import com.example.nutrifit.data.model.CompletedWorkout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.Date

class WorkoutCompletionRepository {

    private fun getUserWorkoutsCollection(userId: String) = 
        FirebaseFirestore.getInstance().collection("users").document(userId).collection("completed_workouts")

    suspend fun markWorkoutAsComplete(userId: String, completedWorkout: CompletedWorkout): Result<Unit> {
        return try {
            // Sử dụng set() và document() để có ID dễ đoán hơn (tùy chọn) hoặc add() để có ID tự động
            getUserWorkoutsCollection(userId).add(completedWorkout).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCompletedWorkouts(userId: String, date: LocalDate): Result<List<CompletedWorkout>> {
        return try {
            val startOfDay = Date(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)
            val endOfDay = Date(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)

            val querySnapshot = getUserWorkoutsCollection(userId)
                .whereGreaterThanOrEqualTo("completedAt", startOfDay)
                .whereLessThan("completedAt", endOfDay)
                .get()
                .await()
            
            val workouts = querySnapshot.toObjects(CompletedWorkout::class.java)
            Result.success(workouts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun unmarkWorkoutAsComplete(userId: String, workoutName: String, date: LocalDate): Result<Unit> {
        return try {
            val startOfDay = Date(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)
            val endOfDay = Date(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)

            // Tìm TẤT CẢ các bản ghi của bài tập này trong ngày
            val querySnapshot = getUserWorkoutsCollection(userId)
                .whereEqualTo("workoutName", workoutName)
                .whereGreaterThanOrEqualTo("completedAt", startOfDay)
                .whereLessThan("completedAt", endOfDay)
                .get()
                .await()

            // Nếu có bản ghi nào được tìm thấy, xóa tất cả chúng trong một batch
            if (!querySnapshot.isEmpty) {
                val batch = FirebaseFirestore.getInstance().batch()
                querySnapshot.documents.forEach { document ->
                    batch.delete(document.reference)
                }
                batch.commit().await() // Thực thi việc xóa
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
