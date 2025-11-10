package com.example.nutrifit.ui.screens.dailylog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.nutrifit.viewmodel.DailyLogViewModel
import com.example.nutrifit.viewmodel.LogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogScreen(navController: NavController) {
    val viewModel: DailyLogViewModel = viewModel()
    val logState by viewModel.logState.collectAsState()
    val showDeleteConfirmationDialog = remember { mutableStateOf(false) }

    // Confirmation Dialog
    if (showDeleteConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog.value = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa tất cả các món ăn trong nhật ký hôm nay không?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllMeals()
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
                title = { Text("Nhật ký bữa ăn hôm nay") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Show the delete all button only if there are meals
                    if (logState is LogState.Success && (logState as LogState.Success).meals.isNotEmpty()) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            when (val state = logState) {
                is LogState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is LogState.Success -> {
                    if (state.meals.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Chưa có gì trong nhật ký của bạn", fontSize = 18.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Hãy thêm món ăn để theo dõi nhé!", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
                        }
                    } else {
                        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(state.meals) { meal ->
                                MealLogItem(meal = meal) {
                                    viewModel.deleteMeal(meal)
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
