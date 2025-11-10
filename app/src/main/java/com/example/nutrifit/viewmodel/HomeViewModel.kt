package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.R
import com.example.nutrifit.data.model.Meal
import com.example.nutrifit.data.repository.DailyIntakeRepository
import com.example.nutrifit.data.repository.MealRepository
import com.example.nutrifit.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
        // Tách biệt việc lấy dữ liệu 1 lần và lắng nghe liên tục
        fetchUserDataAndSuggestions()
        listenForDailyIntakeChanges()
    }

    fun refresh() {
        // Chỉ cần tải lại dữ liệu người dùng và gợi ý, vì nhật ký đã được lắng nghe
        fetchUserDataAndSuggestions()
    }

    private fun fetchUserDataAndSuggestions() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            _suggestedMealsState.value = MealsState.Loading

            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                userRepository.getUser(firebaseUser.uid).onSuccess { user ->
                    _userState.value = UserState.Success(user)
                    user.goal?.let { fetchMealSuggestions(it) }
                }.onFailure {
                    _userState.value = UserState.Error(it.message ?: "Failed to fetch user data.")
                }
            } else {
                val error = "No user logged in."
                _userState.value = UserState.Error(error)
                _suggestedMealsState.value = MealsState.Error(error)
            }
        }
    }

    private fun listenForDailyIntakeChanges() {
        viewModelScope.launch {
            _dailyIntakeState.value = DailyIntakeState.Loading
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                // *** THAY ĐỔI CHÍNH: Sử dụng getDailyIntakeFlow để lắng nghe thay đổi ***
                dailyIntakeRepository.getDailyIntakeFlow(firebaseUser.uid, Date()).collect { result ->
                    result.onSuccess {
                        _dailyIntakeState.value = DailyIntakeState.Success(it)
                    }.onFailure {
                        _dailyIntakeState.value = DailyIntakeState.Error(it.message ?: "Failed to fetch daily intake.")
                    }
                }
            } else {
                _dailyIntakeState.value = DailyIntakeState.Error("No user logged in.")
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
            
            val finalImageResId = if (imageIdentifier == 0) R.drawable.logo else imageIdentifier 
            
            meal.copy(imageResId = finalImageResId)
        }
    }
}
