package com.example.nutrifit.ui.screens.dailylog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nutrifit.data.model.ConsumedMeal
import com.example.nutrifit.data.model.ConsumedWorkout
import com.example.nutrifit.viewmodel.DailyLogViewModel
import com.example.nutrifit.viewmodel.LogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogScreen(navController: NavController) {
    val viewModel: DailyLogViewModel = viewModel()
    val logState by viewModel.logState.collectAsState()
    val showDeleteConfirmationDialog = remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bữa ăn", "Bài tập")

    // Confirmation Dialog
    if (showDeleteConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog.value = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa tất cả các mục trong nhật ký hôm nay không?") },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedTabIndex == 0) {
                            viewModel.clearAllMeals()
                        } else {
                            viewModel.clearAllWorkouts()
                        }
                        showDeleteConfirmationDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Xóa tất cả")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmationDialog.value = false }) {
                    Text("Hủy")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nhật ký hôm nay") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val showButton = when (val state = logState) {
                        is LogState.Success -> if (selectedTabIndex == 0) state.meals.isNotEmpty() else state.workouts.isNotEmpty()
                        else -> false
                    }
                    if (showButton) {
                        IconButton(onClick = { showDeleteConfirmationDialog.value = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete All")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) })
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = logState) {
                    is LogState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is LogState.Success -> {
                        if (selectedTabIndex == 0) { // Meals
                            if (state.meals.isEmpty()) {
                                EmptyLogView("bữa ăn")
                            } else {
                                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(state.meals) { meal ->
                                        MealLogItem(meal = meal) {
                                            viewModel.deleteMeal(meal)
                                        }
                                    }
                                }
                            }
                        } else { // Workouts
                            if (state.workouts.isEmpty()) {
                                EmptyLogView("bài tập")
                            } else {
                                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(state.workouts) { workout ->
                                        WorkoutLogItem(workout = workout) {
                                            viewModel.deleteWorkout(workout)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is LogState.Error -> {
                        Text(state.message, modifier = Modifier.align(Alignment.Center), color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyLogView(logType: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Chưa có $logType nào trong nhật ký của bạn", fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Hãy thêm $logType để theo dõi nhé!", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
    }
}

@Composable
fun MealLogItem(meal: ConsumedMeal, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(meal.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text("${meal.calories} kcal", fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Meal", tint = Color.Red)
            }
        }
    }
}

@Composable
fun WorkoutLogItem(workout: ConsumedWorkout, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(workout.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text("${workout.caloriesBurned} kcal", fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Workout", tint = Color.Red)
            }
        }
    }
}
