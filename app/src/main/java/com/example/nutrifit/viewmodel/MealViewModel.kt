package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.R
import com.example.nutrifit.data.model.ConsumedMeal
import com.example.nutrifit.data.repository.DailyIntakeRepository
import com.example.nutrifit.data.model.Meal
import com.example.nutrifit.data.repository.MealRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

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

    private var allMeals: List<Meal> = emptyList()

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
                allMeals = mappedMeals // Cache the full list
                _mealsState.value = MealsState.Success(mappedMeals)
            }.onFailure {
                _mealsState.value = MealsState.Error(it.message ?: "Failed to fetch meals")
            }
        }
    }

    fun searchMeals(query: String) {
        val filtered = if (query.isBlank()) {
            allMeals
        } else {
            allMeals.filter { it.name.contains(query, ignoreCase = true) }
        }
        _mealsState.value = MealsState.Success(filtered)
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
                mealType = mealType,
                imageRes = meal.imageRes // <-- THE FIX IS HERE
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
