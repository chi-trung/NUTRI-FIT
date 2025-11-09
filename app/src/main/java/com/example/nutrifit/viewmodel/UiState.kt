package com.example.nutrifit.viewmodel

import com.example.nutrifit.data.DailyIntake
import com.example.nutrifit.data.User
import com.example.nutrifit.ui.screens.meal.Meal

// A single source of truth for all UI states

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message: String) : UserState()
}

sealed class DailyIntakeState {
    object Loading : DailyIntakeState()
    data class Success(val intake: DailyIntake?) : DailyIntakeState()
    data class Error(val message: String) : DailyIntakeState()
}

sealed class MealsState {
    object Loading : MealsState()
    data class Success(val meals: List<Meal>) : MealsState()
    data class Error(val message: String) : MealsState()
}

sealed class AddMealState {
    object Idle : AddMealState()
    object Loading : AddMealState()
    object Success : AddMealState()
    data class Error(val message: String) : AddMealState()
}
