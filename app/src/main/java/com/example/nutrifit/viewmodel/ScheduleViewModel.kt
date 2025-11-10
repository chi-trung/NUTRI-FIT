package com.example.nutrifit.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.CompletedWorkout
import com.example.nutrifit.data.model.ConsumedWorkout
import com.example.nutrifit.data.model.Exercise
import com.example.nutrifit.data.repository.DailyIntakeRepository
import com.example.nutrifit.data.repository.ExerciseRepository
import com.example.nutrifit.data.repository.WorkoutCompletionRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.Date
import java.util.UUID

// Lớp dữ liệu cho lịch trình hàng ngày, sử dụng Exercise từ model
data class DailySchedule(
    val date: LocalDate,
    val scheduleName: String,
    val exercises: SnapshotStateList<Exercise>
)

// Các trạng thái của ViewModel
sealed class ScheduleState {
    object Loading : ScheduleState()
    data class Success(val schedules: List<DailySchedule>) : ScheduleState()
    data class Error(val message: String) : ScheduleState()
}

sealed class CompletionState {
    object Idle : CompletionState()
    data class Success(val message: String) : CompletionState()
    data class Error(val message: String) : CompletionState()
}

class ScheduleViewModel : ViewModel() {

    private val workoutCompletionRepository = WorkoutCompletionRepository()
    private val exerciseRepository = ExerciseRepository()
    private val dailyIntakeRepository = DailyIntakeRepository() // Thêm repository
    private val auth = FirebaseAuth.getInstance()

    private val _scheduleState = MutableStateFlow<ScheduleState>(ScheduleState.Loading)
    val scheduleState: StateFlow<ScheduleState> = _scheduleState.asStateFlow()

    private val _completionState = MutableStateFlow<CompletionState>(CompletionState.Idle)
    val completionState: StateFlow<CompletionState> = _completionState.asStateFlow()

    private var allExercises: List<Exercise> = emptyList()

    init {
        loadAllExercises()
    }

    private fun loadAllExercises() {
        viewModelScope.launch {
            _scheduleState.value = ScheduleState.Loading
            exerciseRepository.getAllExercises().onSuccess { exercises ->
                allExercises = exercises
                updateScheduleForDate(LocalDate.now())
            }.onFailure {
                _scheduleState.value = ScheduleState.Error(it.message ?: "Lỗi khi tải bài tập")
            }
        }
    }

    fun updateScheduleForDate(date: LocalDate) {
        if (allExercises.isEmpty()) return

        viewModelScope.launch {
            val weeklySchedules = generateWeekSchedule(date)
            syncScheduleState(weeklySchedules)
            _scheduleState.value = ScheduleState.Success(weeklySchedules)
        }
    }

    private fun generateWeekSchedule(dateInWeek: LocalDate): List<DailySchedule> {
        val schedules = mutableListOf<DailySchedule>()
        val startOfWeek = dateInWeek.with(DayOfWeek.MONDAY)

        for (i in 0..6) {
            val currentDate = startOfWeek.plusDays(i.toLong())
            val dayOfWeek = currentDate.dayOfWeek

            val (scheduleName, dailyExercises) = when (dayOfWeek) {
                DayOfWeek.MONDAY -> "Ngực & Tay" to allExercises.filter { it.muscleGroup == "Ngực" || it.muscleGroup == "Tay trước" }
                DayOfWeek.TUESDAY -> "Lưng & Vai" to allExercises.filter { it.muscleGroup == "Lưng" || it.muscleGroup == "Vai" }
                DayOfWeek.WEDNESDAY -> "Chân & Mông" to allExercises.filter { it.muscleGroup == "Chân" || it.muscleGroup == "Mông" }
                DayOfWeek.THURSDAY -> "Vai & Core" to allExercises.filter { it.muscleGroup == "Vai" || it.muscleGroup == "Bụng" }
                DayOfWeek.FRIDAY -> "Tay & Bụng" to allExercises.filter { it.muscleGroup == "Tay sau" || it.muscleGroup == "Bụng" }
                DayOfWeek.SATURDAY -> "Cardio" to allExercises.filter { it.muscleGroup == "Toàn thân" }
                DayOfWeek.SUNDAY -> "Nghỉ ngơi" to emptyList()
            }

            schedules.add(
                DailySchedule(
                    date = currentDate,
                    scheduleName = scheduleName,
                    exercises = mutableStateListOf<Exercise>().apply { addAll(dailyExercises.map { it.copy() }) }
                )
            )
        }
        return schedules
    }

    private fun syncScheduleState(weeklySchedules: List<DailySchedule>) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch

            weeklySchedules.forEach { dailySchedule ->
                if (dailySchedule.exercises.isNotEmpty()) {
                    workoutCompletionRepository.getCompletedWorkouts(userId, dailySchedule.date).onSuccess { completedWorkouts ->
                        val updatedExercises = dailySchedule.exercises.map { exercise ->
                            val isCompletedOnServer = completedWorkouts.any { it.workoutName == exercise.name }
                            if (exercise.isCompleted != isCompletedOnServer) {
                                exercise.copy(isCompleted = isCompletedOnServer)
                            } else {
                                exercise
                            }
                        }
                        dailySchedule.exercises.clear()
                        dailySchedule.exercises.addAll(updatedExercises)
                    }
                }
            }
        }
    }

    fun handleCheckChanged(exercise: Exercise, isChecked: Boolean, date: LocalDate) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: run {
                _completionState.value = CompletionState.Error("Bạn chưa đăng nhập!")
                return@launch
            }

            val updatedExercise = exercise.copy(isCompleted = isChecked)

            (_scheduleState.value as? ScheduleState.Success)?.schedules
                ?.find { it.date == date }?.exercises?.let { exercises ->
                    val index = exercises.indexOfFirst { it.id == exercise.id }
                    if (index != -1) {
                        exercises[index] = updatedExercise
                    }
                }

            if (isChecked) {
                val completedWorkout = CompletedWorkout(
                    userId = userId,
                    workoutName = exercise.name,
                    muscleGroup = exercise.muscleGroup,
                    completedAt = java.util.Date(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)
                )
                workoutCompletionRepository.markWorkoutAsComplete(userId, completedWorkout).onSuccess {
                    _completionState.value = CompletionState.Success("Đã hoàn thành: ${exercise.name}")

                    // Thêm vào nhật ký DailyIntake
                    val consumedWorkout = ConsumedWorkout(
                        id = UUID.randomUUID().toString(),
                        name = exercise.name,
                        caloriesBurned = 150, // Ước tính calo, sẽ cải thiện sau
                        timestamp = System.currentTimeMillis()
                    )
                    viewModelScope.launch {
                        dailyIntakeRepository.addConsumedWorkout(userId, consumedWorkout)
                    }

                }.onFailure {
                    _completionState.value = CompletionState.Error(it.message ?: "Lỗi khi lưu")
                }
            } else {
                workoutCompletionRepository.unmarkWorkoutAsComplete(userId, exercise.name, date).onSuccess {
                    _completionState.value = CompletionState.Success("Đã bỏ hoàn thành: ${exercise.name}")

                    // Xóa khỏi nhật ký DailyIntake
                     viewModelScope.launch {
                         val toDate = DateTimeUtils.toDate(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
                        dailyIntakeRepository.removeConsumedWorkoutByName(userId, exercise.name, toDate)
                    }

                }.onFailure {
                    _completionState.value = CompletionState.Error(it.message ?: "Lỗi khi bỏ lưu")
                }
            }
        }
    }

    fun resetState() {
        _completionState.value = CompletionState.Idle
    }
}
