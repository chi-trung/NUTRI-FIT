package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.MealSuggestion
import com.example.nutrifit.data.model.TargetState
import com.example.nutrifit.data.model.WorkoutSuggestion
import com.example.nutrifit.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    // State for target
    private val _targetState = MutableStateFlow(TargetState("Tăng cơ", "Mục tiêu: 3000 calo/ngày", true))
    val targetState: StateFlow<TargetState> = _targetState.asStateFlow()

    // State for meal suggestions
    private val _mealSuggestions = MutableStateFlow<List<MealSuggestion>>(emptyList())
    val mealSuggestions: StateFlow<List<MealSuggestion>> = _mealSuggestions.asStateFlow()

    // State for workout suggestions
    private val _workoutSuggestions = MutableStateFlow<List<WorkoutSuggestion>>(emptyList())
    val workoutSuggestions: StateFlow<List<WorkoutSuggestion>> = _workoutSuggestions.asStateFlow()

    // Selected meal and goal
    private val _selectedMeal = MutableStateFlow("Sáng")
    val selectedMeal: StateFlow<String> = _selectedMeal.asStateFlow()

    private val _selectedGoal = MutableStateFlow("Tăng cơ")
    val selectedGoal: StateFlow<String> = _selectedGoal.asStateFlow()

    init {
        loadTarget()
        loadSuggestions()
    }

    fun loadTarget() {
        _targetState.value = repository.getTarget()
    }

    fun updateSelectedMeal(meal: String) {
        _selectedMeal.value = meal
        loadMealSuggestions()
    }

    fun updateSelectedGoal(goal: String) {
        _selectedGoal.value = goal
        loadMealSuggestions()
        loadWorkoutSuggestions()
    }

    private fun loadMealSuggestions() {
        viewModelScope.launch {
            repository.getMealSuggestions(_selectedMeal.value, _selectedGoal.value)
                .collectLatest { suggestions ->
                    _mealSuggestions.value = suggestions
                }
        }
    }

    private fun loadWorkoutSuggestions() {
        viewModelScope.launch {
            repository.getWorkoutSuggestions(_selectedGoal.value)
                .collectLatest { suggestions ->
                    _workoutSuggestions.value = suggestions
                }
        }
    }

    private fun loadSuggestions() {
        loadMealSuggestions()
        loadWorkoutSuggestions()
    }

    fun updateProgress(newCalories: Int) {
        val current = _targetState.value
        val newProgress = newCalories.toFloat() / current.caloriesGoal
        _targetState.value = current.copy(currentCalories = newCalories, progress = newProgress)
        repository.saveTarget(_targetState.value)
    }
}
