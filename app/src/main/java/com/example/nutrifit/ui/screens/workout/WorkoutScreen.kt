package com.example.nutrifit.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.Preview
import com.example.nutrifit.ui.theme.NutriFitTheme
import com.example.nutrifit.R
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.Locale
import androidx.compose.material.icons.filled.CheckCircle
// Dữ liệu cho một bài tập cụ thể
data class Exercise(
    val nameVn: String,
    val nameEn: String,
    val details: String,
    val iconResourceId: Int,
    var isCompleted: Boolean = false
)

// Dữ liệu cho một buổi tập trong ngày
data class DailyWorkout(
    val date: LocalDate,
    val workoutName: String,
    val exercises: SnapshotStateList<Exercise>
)

// --- HÀM CHÍNH ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen() {
    // Ngày hôm nay cố định
    val today = LocalDate.of(2025, 10, 2)

    // Quản lý ngày được chọn
    var selectedDate by remember { mutableStateOf(today) }

    // Lấy ngày đầu tiên của tuần (thứ Hai) từ ngày được chọn
    val startOfWeek = remember(selectedDate) {
        selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }

    // Tạo danh sách các buổi tập trong tuần
    val weeklyWorkouts = remember(startOfWeek) {
        generateWeeklyWorkouts(startOfWeek)
    }

    // Lấy buổi tập của ngày được chọn
    val todayWorkout = weeklyWorkouts.find { it.date == selectedDate }

    // Định dạng ngày để hiển thị
    val dateFormatter = DateTimeFormatter.ofPattern("dd - MM - yyyy", Locale.getDefault())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
// Nút quay lại trong hộp có màu nền
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp) // Tăng padding để nút không bị sát viền
                .clip(RoundedCornerShape(12.dp)) // Bo tròn góc cho hộp
                .background(Color.White) // Thêm nền trắng để nổi bật
                .clickable { /* TODO: Navigate back */ } // Đặt hiệu ứng click cho cả hộp
                .padding(horizontal = 12.dp, vertical = 8.dp) // Padding bên trong hộp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically // Căn giữa icon và chữ theo chiều dọc
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Thêm icon mũi tên
                    contentDescription = "Quay lại",
                    tint = Color(0xFF4CAF50), // Đặt màu cho icon
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp)) // Khoảng cách giữa icon và chữ
                Text(
                    text = "Quay lại",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4CAF50) // Giữ màu chữ như cũ
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp)) // Space cho nút back

            // --- PHẦN HEADER ---
            WorkoutHeader(
                today = today,
                selectedDate = selectedDate,
                onDateChanged = { newDate -> selectedDate = newDate },
                onWeekChanged = { direction ->
                    // Chuyển sang tuần trước/sau
                    selectedDate = if (direction > 0) {
                        selectedDate.plusWeeks(1)
                    } else {
                        selectedDate.minusWeeks(1)
                    }
                },
                weeklyWorkouts = weeklyWorkouts,
                dateFormatter = dateFormatter
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- PHẦN NỘI DUNG (THẺ BÀI TẬP) ---
            todayWorkout?.let {
                WorkoutDetailsCard(workout = it)
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- PHẦN FOOTER ---
            todayWorkout?.let {
                TodayProgressFooter(workout = it)
                Spacer(modifier = Modifier.height(16.dp))

                // Chỉ hiển thị tiến độ tuần khi đang ở ngày hôm nay
                if (selectedDate == today) {
                    WeeklyProgressFooter(weeklyWorkouts = weeklyWorkouts)
                }
            }
        }
    }
}

// --- CÁC COMPOSABLE CON ---

@Composable
fun WorkoutHeader(
    today: LocalDate,
    selectedDate: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    onWeekChanged: (Int) -> Unit,
    weeklyWorkouts: List<DailyWorkout>,
    dateFormatter: DateTimeFormatter
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Nutri-Fit Logo",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tiêu đề
        Text(
            text = "Lịch tập của bạn",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nút điều hướng tuần và ngày tháng trong box
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onWeekChanged(-1) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Tuần trước",
                        tint = Color(0xFF4CAF50)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = selectedDate.format(dateFormatter),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.widthIn(min = 120.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { onWeekChanged(1) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Tuần sau",
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nút "Quay về hôm nay" (chỉ hiển thị khi không phải ngày hôm nay)
        if (selectedDate != today) {
            Button(
                onClick = { onDateChanged(today) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800), // Màu cam nổi bật
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Quay về hôm nay",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lịch tuần
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weeklyWorkouts.forEach { workout ->
                // Kiểm tra xem buổi tập này đã hoàn thành 100% chưa
                val isWorkoutComplete = workout.exercises.isNotEmpty() && workout.exercises.all { it.isCompleted }

                DayItem(
                    workout = workout,
                    isToday = workout.date == today,
                    isSelected = workout.date == selectedDate,
                    isCompleted = isWorkoutComplete, // <-- Truyền trạng thái hoàn thành
                    today = today,                   // <-- Truyền ngày hôm nay để so sánh
                    onSelected = { onDateChanged(workout.date) }
                )
            }
        }
    }
}

@Composable
fun DayItem(
    workout: DailyWorkout,
    isToday: Boolean,
    isSelected: Boolean,
    isCompleted: Boolean, // <-- Thêm tham số mới
    today: LocalDate,     // <-- Thêm tham số mới
    onSelected: () -> Unit
) {
    val dayFormatter = DateTimeFormatter.ofPattern("E", Locale("vi", "VN"))
    val dateFormatter = DateTimeFormatter.ofPattern("dd")

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color(0xFF4CAF50) else Color.Transparent)
            .clickable { onSelected() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = workout.date.format(dayFormatter),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 12.sp
            )

            // Ngày với dấu chấm đỏ nếu là hôm nay
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = workout.date.format(dateFormatter),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 16.sp
                )

                // Dấu chấm đỏ cho ngày hôm nay
                if (isToday && !isSelected) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-2).dp)
                    )
                }
            }

            if (isSelected) {
                Text(
                    text = workout.workoutName,
                    color = Color.White,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
        // Thêm dấu tick xanh nếu đã hoàn thành và là ngày trước hôm nay
        if (isCompleted && workout.date.isBefore(today)) {
            Icon(
                imageVector = Icons.Default.CheckCircle, // <-- Cần import icon này
                contentDescription = "Đã hoàn thành",
                tint = Color(0xFF4CAF50),
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 4.dp, y = (-4).dp)
            )
        }
    }
}

@Composable
fun WorkoutDetailsCard(workout: DailyWorkout) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = workout.workoutName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            workout.exercises.forEachIndexed { index, exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onCheckedChange = { isChecked ->
                        workout.exercises[index] =
                            workout.exercises[index].copy(isCompleted = isChecked)
                    }
                )
                if (index < workout.exercises.size - 1) {
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Icon trong ô nền tròn
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = exercise.iconResourceId),
                contentDescription = "Icon bài tập",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${exercise.nameVn} (${exercise.nameEn})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (exercise.isCompleted) Color.Gray else Color.Black
            )
            Text(
                text = exercise.details,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Checkbox(
            checked = exercise.isCompleted,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF4CAF50),
                uncheckedColor = Color.Gray
            )
        )
    }
}

@Composable
fun TodayProgressFooter(workout: DailyWorkout) {
    val completedCount = workout.exercises.count { it.isCompleted }
    val totalCount = workout.exercises.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tiến độ hôm nay",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = Color(0xFF4CAF50),
            trackColor = Color(0xFFE0E0E0)
        )
    }
}

@Composable
fun WeeklyProgressFooter(weeklyWorkouts: List<DailyWorkout>) {
    val completedDays = weeklyWorkouts.count { workout ->
        workout.exercises.isNotEmpty() && workout.exercises.all { it.isCompleted }
    }
    val totalDaysWithWorkout = weeklyWorkouts.count { it.exercises.isNotEmpty() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF4CAF50))
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ĐÃ HOÀN THÀNH ${completedDays}/${totalDaysWithWorkout} BUỔI TẬP",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

// --- HÀM HỖ TRỢ TẠO DỮ LIỆU ---

fun generateWeeklyWorkouts(startDate: LocalDate): List<DailyWorkout> {
    val workouts = mutableListOf<DailyWorkout>()
    val legWorkout = listOf(
        Exercise("Ngồi xổm", "Squats", "4 hiệp x 8 - 12 lần", iconResourceId = R.drawable.ic_squats),
        Exercise("Đẩy chân", "Leg Press", "3 hiệp x 10 - 12 lần", iconResourceId = R.drawable.ic_leg_press),
        Exercise("Chùng chân", "Lunges", "3 hiệp x 12 lần mỗi chân", iconResourceId = R.drawable.ic_lunges),
        Exercise("Nhón gót", "Calf Raises", "4 hiệp x 15 - 20", iconResourceId = R.drawable.ic_calf_raises)
    )
    val armWorkout = listOf(
        Exercise("Đẩy ngực", "Bench Press", "4 hiệp x 8 - 10 lần", iconResourceId = R.drawable.ic_bench_press),
        Exercise("Kéo xà", "Pull-ups", "3 hiệp x tối đa", iconResourceId = R.drawable.ic_pull_ups),
        Exercise("Đẩy vai", "Overhead Press", "3 hiệp x 10 - 12 lần", iconResourceId = R.drawable.ic_overhead_press),
        Exercise("Gập bắp tay", "Bicep Curls", "3 hiệp x 12 - 15 lần", iconResourceId = R.drawable.ic_bicep_curls)
    )

    for (i in 0..6) {
        val currentDate = startDate.plusDays(i.toLong())
        when (currentDate.dayOfWeek) {
            DayOfWeek.MONDAY, DayOfWeek.THURSDAY -> workouts.add(
                DailyWorkout(
                    currentDate,
                    "Tay Bùng",
                    mutableStateListOf<Exercise>().apply { addAll(armWorkout.map { it.copy() }) }
                )
            )
            DayOfWeek.TUESDAY, DayOfWeek.FRIDAY -> workouts.add(
                DailyWorkout(
                    currentDate,
                    "Chân & Mông",
                    mutableStateListOf<Exercise>().apply { addAll(legWorkout.map { it.copy() }) }
                )
            )
            else -> workouts.add(DailyWorkout(currentDate, "Nghỉ ngơi", mutableStateListOf()))
        }
    }
    return workouts
}

@Preview(showBackground = true)
@Composable
fun WorkoutScreenPreview() {
    NutriFitTheme {
        WorkoutScreen()
    }
}