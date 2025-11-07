package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.User
import com.example.nutrifit.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _saveState = MutableStateFlow<SaveState>(SaveState.Idle)
    val saveState: StateFlow<SaveState> = _saveState

    sealed class SaveState {
        object Idle : SaveState()
        object Loading : SaveState()
        object Success : SaveState()
        data class Error(val message: String) : SaveState()
    }

    fun saveUserProfile(
        name: String,
        gender: String,
        age: String,
        height: String,
        weight: String
    ) {
        viewModelScope.launch {
            _saveState.value = SaveState.Loading
            val currentUser = auth.currentUser
            if (currentUser == null) {
                _saveState.value = SaveState.Error("User not logged in")
                return@launch
            }

            try {
                val user = User(
                    id = currentUser.uid,
                    email = currentUser.email ?: "",
                    name = name,
                    gender = gender,
                    age = age,
                    height = height,
                    weight = weight
                )

                userRepository.saveUser(user).getOrThrow()
                _saveState.value = SaveState.Success
            } catch (e: Exception) {
                _saveState.value = SaveState.Error(e.message ?: "Failed to save profile")
            }
        }
    }
}
