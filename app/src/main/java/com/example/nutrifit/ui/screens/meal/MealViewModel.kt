package com.example.nutrifit.ui.screens.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.ConsumedMeal
import com.example.nutrifit.data.DailyIntakeRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MealViewModel : ViewModel() {

    private val dailyIntakeRepository = DailyIntakeRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _addMealState = MutableStateFlow<AddMealState>(AddMealState.Idle)
    val addMealState: StateFlow<AddMealState> = _addMealState

    fun addMealToIntake(meal: Meal, mealType: String) {
        viewModelScope.launch {
            _addMealState.value = AddMealState.Loading
            val userId = auth.currentUser?.uid
            if (userId == null) {
                _addMealState.value = AddMealState.Error("User not logged in")
                return@launch
            }

            val consumedMeal = ConsumedMeal(
                id = UUID.randomUUID().toString(),
                mealId = meal.id,
                name = meal.name,
                calories = meal.calories,
                mealType = mealType
            )

            val result = dailyIntakeRepository.addConsumedMeal(userId, consumedMeal)
            if (result.isSuccess) {
                _addMealState.value = AddMealState.Success
            } else {
                _addMealState.value = AddMealState.Error(result.exceptionOrNull()?.message ?: "Failed to add meal")
            }
        }
    }
    
    // Reset state to avoid re-triggering toast on configuration change
    fun resetAddMealState() {
        _addMealState.value = AddMealState.Idle
    }
}

sealed class AddMealState {
    object Idle : AddMealState()
    object Loading : AddMealState()
    object Success : AddMealState()
    data class Error(val message: String) : AddMealState()
}
