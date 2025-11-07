package com.example.nutrifit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TargetViewModel : ViewModel() {

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

    fun saveGoal(goal: String) {
        viewModelScope.launch {
            _saveState.value = SaveState.Loading
            val currentUser = auth.currentUser
            if (currentUser == null) {
                _saveState.value = SaveState.Error("User not logged in")
                return@launch
            }

            try {
                // Lấy thông tin người dùng hiện tại
                val userResult = userRepository.getUser(currentUser.uid)
                if (userResult.isSuccess) {
                    val user = userResult.getOrNull()!!
                    // Cập nhật mục tiêu
                    val updatedUser = user.copy(goal = goal)
                    // Lưu lại
                    userRepository.saveUser(updatedUser).getOrThrow()
                    _saveState.value = SaveState.Success
                } else {
                    _saveState.value = SaveState.Error("User profile not found")
                }
            } catch (e: Exception) {
                _saveState.value = SaveState.Error(e.message ?: "Failed to save goal")
            }
        }
    }
}