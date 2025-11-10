package com.example.nutrifit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.CompletedWorkout
import com.example.nutrifit.data.repository.WorkoutCompletionRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CompletionState {
    object Idle : CompletionState()
    object Loading : CompletionState()
    data class Success(val message: String) : CompletionState()
    data class Error(val message: String) : CompletionState()
}

class ScheduleViewModel : ViewModel() {

    private val repository = WorkoutCompletionRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _completionState = MutableStateFlow<CompletionState>(CompletionState.Idle)
    val completionState: StateFlow<CompletionState> = _completionState

    fun markWorkoutAsComplete(workoutName: String, muscleGroup: String) {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser == null) {
                _completionState.value = CompletionState.Error("Bạn cần đăng nhập để thực hiện hành động này.")
                return@launch
            }

            _completionState.value = CompletionState.Loading

            val completedWorkout = CompletedWorkout(
                userId = currentUser.uid,
                workoutName = workoutName,
                muscleGroup = muscleGroup
            )

            repository.markWorkoutAsComplete(completedWorkout).onSuccess {
                _completionState.value = CompletionState.Success("Đã hoàn thành bài tập '$workoutName'!")
            }.onFailure {
                _completionState.value = CompletionState.Error(it.message ?: "Đã có lỗi xảy ra.")
            }
        }
    }

    fun resetState() {
        _completionState.value = CompletionState.Idle
    }
}
