package com.example.nutrifit.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.DailyIntake
import com.example.nutrifit.data.DailyIntakeRepository
import com.example.nutrifit.data.User
import com.example.nutrifit.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userRepository: UserRepository = UserRepository()
    private val dailyIntakeRepository: DailyIntakeRepository = DailyIntakeRepository()

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState

    private val _dailyIntakeState = MutableStateFlow<DailyIntakeState>(DailyIntakeState.Loading)
    val dailyIntakeState: StateFlow<DailyIntakeState> = _dailyIntakeState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            _dailyIntakeState.value = DailyIntakeState.Loading

            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                // Fetch User Profile
                val userResult = userRepository.getUser(firebaseUser.uid)
                if (userResult.isSuccess) {
                    userResult.getOrNull()?.let {
                        _userState.value = UserState.Success(it)
                    }
                } else {
                    _userState.value = UserState.Error(userResult.exceptionOrNull()?.message ?: "Failed to fetch user data.")
                }

                // Fetch Daily Intake
                val intakeResult = dailyIntakeRepository.getDailyIntake(firebaseUser.uid, Date())
                if (intakeResult.isSuccess) {
                    _dailyIntakeState.value = DailyIntakeState.Success(intakeResult.getOrNull())
                } else {
                     _dailyIntakeState.value = DailyIntakeState.Error(intakeResult.exceptionOrNull()?.message ?: "Failed to fetch daily intake.")
                }

            } else {
                _userState.value = UserState.Error("No user logged in.")
                _dailyIntakeState.value = DailyIntakeState.Error("No user logged in.")
            }
        }
    }
}

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
