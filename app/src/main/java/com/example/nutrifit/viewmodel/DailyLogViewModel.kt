package com.example.nutrifit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.ConsumedMeal
import com.example.nutrifit.data.DailyIntakeRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class DailyLogViewModel : ViewModel() {

    private val repository = DailyIntakeRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _logState = MutableStateFlow<LogState>(LogState.Loading)
    val logState: StateFlow<LogState> = _logState

    init {
        fetchDailyLog()
    }

    fun fetchDailyLog() {
        viewModelScope.launch {
            _logState.value = LogState.Loading
            val userId = auth.currentUser?.uid
            if (userId == null) {
                _logState.value = LogState.Error("User not logged in")
                return@launch
            }

            val result = repository.getDailyIntake(userId, Date())
            if (result.isSuccess) {
                val intake = result.getOrNull()
                _logState.value = LogState.Success(intake?.consumedMeals ?: emptyList())
            } else {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to fetch log")
            }
        }
    }

    fun deleteMeal(meal: ConsumedMeal) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid
            if (userId == null) {
                return@launch
            }

            val result = repository.removeConsumedMeal(userId, meal)
            if (result.isSuccess) {
                fetchDailyLog()
            } else {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to delete meal")
            }
        }
    }

    fun clearAllMeals() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid
            if (userId == null) {
                return@launch
            }

            val result = repository.clearAllConsumedMeals(userId, Date())
            if (result.isSuccess) {
                fetchDailyLog()
            } else {
                _logState.value = LogState.Error(result.exceptionOrNull()?.message ?: "Failed to clear meals")
            }
        }
    }
}

sealed class LogState {
    object Loading : LogState()
    data class Success(val meals: List<ConsumedMeal>) : LogState()
    data class Error(val message: String) : LogState()
}
