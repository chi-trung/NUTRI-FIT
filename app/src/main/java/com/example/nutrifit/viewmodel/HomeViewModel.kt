package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.R
import com.example.nutrifit.data.DailyIntakeRepository
import com.example.nutrifit.data.MealRepository
import com.example.nutrifit.data.UserRepository
import com.example.nutrifit.ui.screens.meal.Meal
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository = UserRepository()
    private val dailyIntakeRepository: DailyIntakeRepository = DailyIntakeRepository()
    private val mealRepository: MealRepository = MealRepository()

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState

    private val _dailyIntakeState = MutableStateFlow<DailyIntakeState>(DailyIntakeState.Loading)
    val dailyIntakeState: StateFlow<DailyIntakeState> = _dailyIntakeState

    private val _suggestedMealsState = MutableStateFlow<MealsState>(MealsState.Loading)
    val suggestedMealsState: StateFlow<MealsState> = _suggestedMealsState

    init {
        fetchData()
    }

    fun fetchData() { // Made public to allow refresh
        viewModelScope.launch {
            _userState.value = UserState.Loading
            _dailyIntakeState.value = DailyIntakeState.Loading
            _suggestedMealsState.value = MealsState.Loading

            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                // Fetch User Profile
                userRepository.getUser(firebaseUser.uid).onSuccess { user ->
                    if (user != null) {
                        _userState.value = UserState.Success(user)
                        user.goal?.let { fetchMealSuggestions(it) } // Fetch suggestions after getting user goal
                    } else {
                        _userState.value = UserState.Error("User data is null.")
                    }
                }.onFailure {
                    _userState.value = UserState.Error(it.message ?: "Failed to fetch user data.")
                }

                // Fetch Daily Intake
                dailyIntakeRepository.getDailyIntake(firebaseUser.uid, Date()).onSuccess { 
                    _dailyIntakeState.value = DailyIntakeState.Success(it)
                }.onFailure {
                    _dailyIntakeState.value = DailyIntakeState.Error(it.message ?: "Failed to fetch daily intake.")
                }

            } else {
                val error = "No user logged in."
                _userState.value = UserState.Error(error)
                _dailyIntakeState.value = DailyIntakeState.Error(error)
                _suggestedMealsState.value = MealsState.Error(error)
            }
        }
    }

    private fun fetchMealSuggestions(goal: String) {
        viewModelScope.launch {
             mealRepository.getMealsByGoal(goal).onSuccess { meals ->
                 val mappedMeals = mapMealImages(meals)
                _suggestedMealsState.value = MealsState.Success(mappedMeals)
             }.onFailure {
                 _suggestedMealsState.value = MealsState.Error(it.message ?: "Failed to fetch suggestions")
             }
        }
    }
    
    private fun mapMealImages(meals: List<Meal>): List<Meal> {
        val context = getApplication<Application>().applicationContext
        return meals.map { meal ->
            val imageResName = meal.imageRes // imageRes is a String
            val imageIdentifier = if (imageResName.isNotEmpty()) {
                 context.resources.getIdentifier(imageResName, "drawable", context.packageName)
            } else { 0 }
            
            // Use a default placeholder if the resource is not found
            val finalImageResId = if (imageIdentifier == 0) R.drawable.logo else imageIdentifier 
            
            meal.copy(imageResId = finalImageResId)
        }
    }
}
