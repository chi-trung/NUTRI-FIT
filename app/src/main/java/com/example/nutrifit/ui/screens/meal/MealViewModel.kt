package com.example.nutrifit.ui.screens.meal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.R
import com.example.nutrifit.data.ConsumedMeal
import com.example.nutrifit.data.DailyIntakeRepository
import com.example.nutrifit.data.MealRepository
import com.example.nutrifit.viewmodel.AddMealState
import com.example.nutrifit.viewmodel.MealsState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

// New state for a single meal detail
sealed class MealDetailState {
    object Loading : MealDetailState()
    data class Success(val meal: Meal) : MealDetailState()
    data class Error(val message: String) : MealDetailState()
}

class MealViewModel(application: Application) : AndroidViewModel(application) {

    private val dailyIntakeRepository = DailyIntakeRepository()
    private val mealRepository = MealRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _addMealState = MutableStateFlow<AddMealState>(AddMealState.Idle)
    val addMealState: StateFlow<AddMealState> = _addMealState

    private val _mealsState = MutableStateFlow<MealsState>(MealsState.Loading)
    val mealsState: StateFlow<MealsState> = _mealsState

    private val _mealDetailState = MutableStateFlow<MealDetailState>(MealDetailState.Loading)
    val mealDetailState: StateFlow<MealDetailState> = _mealDetailState

    init {
        fetchAllMeals()
    }

    fun fetchMealById(mealId: Int) {
        viewModelScope.launch {
            _mealDetailState.value = MealDetailState.Loading
            mealRepository.getMealById(mealId).onSuccess { meal ->
                if (meal != null) {
                    val mappedMeal = mapMealImages(listOf(meal)).first()
                    _mealDetailState.value = MealDetailState.Success(mappedMeal)
                } else {
                    _mealDetailState.value = MealDetailState.Error("Meal not found.")
                }
            }.onFailure {
                _mealDetailState.value = MealDetailState.Error(it.message ?: "Failed to fetch meal details.")
            }
        }
    }

    fun fetchAllMeals() {
        viewModelScope.launch {
            _mealsState.value = MealsState.Loading
            mealRepository.getAllMeals().onSuccess { meals ->
                val mappedMeals = mapMealImages(meals)
                _mealsState.value = MealsState.Success(mappedMeals)
            }.onFailure {
                _mealsState.value = MealsState.Error(it.message ?: "Failed to fetch meals")
            }
        }
    }
    
    private fun mapMealImages(meals: List<Meal>): List<Meal> {
        val context = getApplication<Application>().applicationContext
        return meals.map { meal ->
            val imageResName = meal.imageRes
            val imageIdentifier = if (imageResName.isNotEmpty()) {
                 context.resources.getIdentifier(imageResName, "drawable", context.packageName)
            } else { 0 }
            
            val finalImageResId = if (imageIdentifier == 0) R.drawable.logo else imageIdentifier 
            
            meal.copy(imageResId = finalImageResId)
        }
    }

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

            dailyIntakeRepository.addConsumedMeal(userId, consumedMeal).onSuccess {
                _addMealState.value = AddMealState.Success
            }.onFailure {
                _addMealState.value = AddMealState.Error(it.message ?: "Failed to add meal")
            }
        }
    }

    fun resetAddMealState() {
        _addMealState.value = AddMealState.Idle
    }
}