package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    // State for user profile
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userGender = MutableStateFlow("...")
    val userGender: StateFlow<String> = _userGender.asStateFlow()

    private val _userAge = MutableStateFlow("...")
    val userAge: StateFlow<String> = _userAge.asStateFlow()

    private val _userHeight = MutableStateFlow("...")
    val userHeight: StateFlow<String> = _userHeight.asStateFlow()

    private val _userWeight = MutableStateFlow("...")
    val userWeight: StateFlow<String> = _userWeight.asStateFlow()

    // Loading and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        _isLoading.value = true
        val profile = repository.getUserProfile()
        _userName.value = profile["name"] ?: ""
        _userGender.value = profile["gender"] ?: "..."
        _userAge.value = profile["age"] ?: "..."
        _userHeight.value = profile["height"] ?: "..."
        _userWeight.value = profile["weight"] ?: "..."
        _isLoading.value = false
    }

    fun saveUserProfile(name: String, gender: String, age: String, height: String, weight: String) {
        if (name.isBlank()) {
            _errorMessage.value = "Tên không được để trống"
            return
        }
        if (age != "..." && age.toIntOrNull() == null) {
            _errorMessage.value = "Tuổi phải là số"
            return
        }
        // Add more validations as needed

        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.saveUserProfile(name, gender, age, height, weight)
                _userName.value = name
                _userGender.value = gender
                _userAge.value = age
                _userHeight.value = height
                _userWeight.value = weight
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi lưu hồ sơ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
