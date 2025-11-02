package com.example.nutrifit.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    onBackClick: () -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Quay về",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                NutriFitLogo()
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Title
            item {
                Text(
                    text = "Lịch tập của bạn",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Calendar Section
            item {
                CalendarSection(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Progress Section
            item {
                ProgressSection()
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Summary Button
            item {
                SummaryButton()
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun NutriFitLogo() {
    // Placeholder for logo - bạn sẽ thay bằng logo thật
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color(0xFF4CAF50), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NUTRI-FIT",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CalendarSection(
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date picker header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val newDate = selectedDate.clone() as Calendar
                    newDate.add(Calendar.WEEK_OF_YEAR, -1)
                    onDateSelected(newDate)
                }) {
                    Text("<", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = dateFormat.format(selectedDate.time),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                IconButton(onClick = {
                    val newDate = selectedDate.clone() as Calendar
                    newDate.add(Calendar.WEEK_OF_YEAR, 1)
                    onDateSelected(newDate)
                }) {
                    Text(">", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Week days
            WeekCalendar(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
        }
    }
}

@Composable
fun WeekCalendar(
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
) {
    val weekDays = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")

    // Tìm ngày đầu tuần (Thứ 2)
    val startOfWeek = selectedDate.clone() as Calendar
    val dayOfWeek = startOfWeek.get(Calendar.DAY_OF_WEEK)
    val daysToSubtract = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - Calendar.MONDAY
    startOfWeek.add(Calendar.DAY_OF_MONTH, -daysToSubtract)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        weekDays.forEachIndexed { index, day ->
            val date = startOfWeek.clone() as Calendar
            date.add(Calendar.DAY_OF_MONTH, index)

            val isSelected = isSameDay(date, selectedDate)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = day,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isSelected) Color(0xFF5E7BF9) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.get(Calendar.DAY_OF_MONTH).toString(),
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else Color.Black
                    )
                }

                // Workout indicator dot (giả sử có workout)
                if (index % 2 == 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Color(0xFF4CAF50), shape = RoundedCornerShape(3.dp))
                    )
                }
            }
        }
    }
}

// Helper function để so sánh 2 ngày
fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

@Composable
fun ProgressSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tiến độ tuần",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = "70%",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF5E7BF9)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = 0.7f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF5E7BF9),
                trackColor = Color(0xFFE0E0E0),
            )
        }
    }
}

@Composable
fun SummaryButton() {
    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE8F5E9)
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(
            text = "Đã hoàn thành: 4/7 buổi tập",
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

// PREVIEW - Để xem giao diện
@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScheduleScreenPreview() {
    MaterialTheme {
        ScheduleScreen()
    }
}
