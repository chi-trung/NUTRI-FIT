package com.example.nutrifit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.User
import com.example.nutrifit.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SettingUiState {
    object Loading : SettingUiState()
    data class Success(val user: User, val providers: List<String>) : SettingUiState()
    data class Error(val message: String) : SettingUiState()
}

class SettingViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<SettingUiState>(SettingUiState.Loading)
    val uiState: StateFlow<SettingUiState> = _uiState

    init {
        fetchUserData()
    }

    fun fetchUserData() {
        viewModelScope.launch {
            _uiState.value = SettingUiState.Loading
            val firebaseUser = auth.currentUser
            if (firebaseUser == null) {
                _uiState.value = SettingUiState.Error("User not logged in")
                return@launch
            }
            val userId = firebaseUser.uid
            val providerIds = firebaseUser.providerData.map { it.providerId }

            val result = userRepository.getUser(userId)
            _uiState.value = result.fold(
                onSuccess = { user -> SettingUiState.Success(user, providerIds) },
                onFailure = { exception -> SettingUiState.Error(exception.message ?: "Failed to fetch user data") }
            )
        }
    }

    fun saveUserData(
        name: String,
        email: String
    ) {
        viewModelScope.launch {
            val currentUiState = _uiState.value
            if (currentUiState is SettingUiState.Success) {
                val updatedUser = currentUiState.user.copy(
                    name = name,
                    email = email
                )
                userRepository.saveUser(updatedUser).fold(
                    onSuccess = { _uiState.value = SettingUiState.Success(updatedUser, currentUiState.providers) },
                    onFailure = { exception -> _uiState.value = SettingUiState.Error(exception.message ?: "Failed to save user data") }
                )
            }
        }
    }
}