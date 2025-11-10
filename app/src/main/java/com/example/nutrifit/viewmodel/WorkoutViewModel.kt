package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.R
import com.example.nutrifit.data.model.Workout
import com.example.nutrifit.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class WorkoutsState {
    object Loading : WorkoutsState()
    data class Success(val workouts: Map<String, List<Workout>>) : WorkoutsState()
    data class Error(val message: String) : WorkoutsState()
}

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository = WorkoutRepository()

    private val _workoutsState = MutableStateFlow<WorkoutsState>(WorkoutsState.Loading)
    val workoutsState: StateFlow<WorkoutsState> = _workoutsState

    init {
        fetchAllWorkouts()
    }

    private fun fetchAllWorkouts() {
        viewModelScope.launch {
            _workoutsState.value = WorkoutsState.Loading
            workoutRepository.getAllWorkouts().onSuccess { workouts ->
                if (workouts.isEmpty()) {
                    _workoutsState.value = WorkoutsState.Error("Không tìm thấy bài tập nào. Vui lòng kiểm tra dữ liệu trên Firestore.")
                } else {
                    val workoutsWithResources = mapWorkoutResources(workouts)
                    val groupedWorkouts = workoutsWithResources.groupBy { workout -> workout.muscleGroup }
                    _workoutsState.value = WorkoutsState.Success(groupedWorkouts)
                }
            }.onFailure {
                _workoutsState.value = WorkoutsState.Error(it.message ?: "Failed to fetch workouts.")
            }
        }
    }

    private fun mapWorkoutResources(workouts: List<Workout>): List<Workout> {
        val context = getApplication<Application>().applicationContext
        return workouts.map { workout ->
            // Chỉ map videoUrl thành ID, giữ nguyên imageUrl là một String
            val videoResId = if (workout.videoUrl.isNotEmpty()) {
                context.resources.getIdentifier(workout.videoUrl, "raw", context.packageName)
            } else { 0 } // 0 indicates no video

            workout.copy(
                videoResId = videoResId
            )
        }
    }
}
