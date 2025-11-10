package com.example.nutrifit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.ConsumedMeal
import com.example.nutrifit.data.model.ConsumedWorkout
import com.example.nutrifit.data.repository.DailyIntakeRepository
import com.example.nutrifit.data.repository.WorkoutCompletionRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import java.util.Date

class DailyLogViewModel : ViewModel() {

    private val dailyIntakeRepository = DailyIntakeRepository()
    private val workoutCompletionRepository = WorkoutCompletionRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _logState = MutableStateFlow<LogState>(LogState.Loading)
    val logState: StateFlow<LogState> = _logState

    init {
        // Bắt đầu lắng nghe các thay đổi trong thời gian thực
        listenForDailyLogChanges()
    }

    private fun listenForDailyLogChanges() {
        viewModelScope.launch {
            _logState.value = LogState.Loading
            val userId = auth.currentUser?.uid
            if (userId == null) {
                _logState.value = LogState.Error("User not logged in")
                return@launch
            }

            // Lắng nghe Flow từ repository
            dailyIntakeRepository.getDailyIntakeFlow(userId, Date()).collect { result ->
                if (result.isSuccess) {
                    val intake = result.getOrNull()
                    _logState.value = LogState.Success(
                        meals = intake?.consumedMeals ?: emptyList(),
                        workouts = intake?.consumedWorkouts ?: emptyList()
                    )
                } else {
                    _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to fetch log")
                }
            }
        }
    }

    fun deleteMeal(meal: ConsumedMeal) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch

            val result = dailyIntakeRepository.removeConsumedMeal(userId, meal)
            if (result.isFailure) {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to delete meal")
            }
        }
    }

    fun deleteWorkout(workout: ConsumedWorkout) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch

            // 1. Xóa workout khỏi nhật ký
            val result = dailyIntakeRepository.removeConsumedWorkout(userId, workout)
            if (result.isFailure) {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to delete workout")
                return@launch
            }

            // 2. Bỏ đánh dấu hoàn thành trong lịch tập
            val date = Instant.ofEpochMilli(workout.timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
            workoutCompletionRepository.unmarkWorkoutAsComplete(userId, workout.name, date)
        }
    }

    fun clearAllMeals() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch

            val result = dailyIntakeRepository.clearAllConsumedMeals(userId, Date())
            if (result.isFailure) {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to clear meals")
            }
        }
    }

    fun clearAllWorkouts() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            val workoutsToUnmark = (logState.value as? LogState.Success)?.workouts ?: emptyList()
            
            // 1. Xóa tất cả workout khỏi nhật ký
            val result = dailyIntakeRepository.clearAllConsumedWorkouts(userId, Date())
            if (result.isFailure) {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to clear workouts")
                return@launch
            }

            // 2. Bỏ đánh dấu tất cả workout trong lịch tập của ngày hôm nay
            val today = org.threeten.bp.LocalDate.now()
            workoutsToUnmark.forEach {
                workoutCompletionRepository.unmarkWorkoutAsComplete(userId, it.name, today)
            }
        }
    }
}

sealed class LogState {
    object Loading : LogState()
    data class Success(
        val meals: List<ConsumedMeal>,
        val workouts: List<ConsumedWorkout>
    ) : LogState()
    data class Error(val message: String) : LogState()
}
