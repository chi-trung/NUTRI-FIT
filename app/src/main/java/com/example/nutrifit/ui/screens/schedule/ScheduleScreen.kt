package com.example.nutrifit.ui.screens.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
data class DailySchedule(
    val date: LocalDate,
    val scheduleName: String,
    val exercises: SnapshotStateList<Exercise>
)

// --- HÀM CHÍNH ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen() {
    // Ngày hôm nay cố định
    val today = LocalDate.of(2025, 10, 2)

    // Quản lý ngày được chọn
    var selectedDate by remember { mutableStateOf(today) }

    // Lấy ngày đầu tiên của tuần (thứ Hai) từ ngày được chọn
    val startOfWeek = remember(selectedDate) {
        selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }

    // Tạo danh sách các buổi tập trong tuần
    val weeklySchedules = remember(startOfWeek) {
        generateWeeklySchedules(startOfWeek)
    }

    // Lấy buổi tập của ngày được chọn
    val todaySchedule = weeklySchedules.find { it.date == selectedDate }

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
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .clickable { /* TODO: Navigate back */ }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Quay lại",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4CAF50)
                )
            }
        }

        // Sử dụng LazyColumn thay vì Column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp)) // Space cho nút back
            }

            // --- PHẦN HEADER ---
            item {
                ScheduleHeader(
                    today = today,
                    selectedDate = selectedDate,
                    onDateChanged = { newDate -> selectedDate = newDate },
                    onWeekChanged = { direction ->
                        selectedDate = if (direction > 0) {
                            selectedDate.plusWeeks(1)
                        } else {
                            selectedDate.minusWeeks(1)
                        }
                    },
                    weeklySchedules = weeklySchedules,
                    dateFormatter = dateFormatter
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- PHẦN NỘI DUNG (THẺ BÀI TẬP) ---
            item {
                todaySchedule?.let {
                    ScheduleDetailsCard(schedule = it)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- PHẦN FOOTER ---
            item {
                todaySchedule?.let {
                    DailyProgressFooter(schedule = it)
                    Spacer(modifier = Modifier.height(16.dp))
                    WeeklyProgressFooter(weeklySchedules = weeklySchedules)
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// --- CÁC COMPOSABLE CON ---

@Composable
fun ScheduleHeader(
    today: LocalDate,
    selectedDate: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    onWeekChanged: (Int) -> Unit,
    weeklySchedules: List<DailySchedule>,
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

        // Tên app NUTRI - FIT
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NUTRI",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1AC9AC)
            )
            Text(
                text = " - ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "FIT",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF0044)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Tiêu đề
        Text(
            text = "Lịch tập của bạn",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                // Nút mũi tên trái trong ô tròn
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0))
                        .clickable { onWeekChanged(-1) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Tuần trước",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = selectedDate.format(dateFormatter),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.widthIn(min = 120.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Nút mũi tên phải trong ô tròn
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0))
                        .clickable { onWeekChanged(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Tuần sau",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(24.dp)
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
                    containerColor = Color(0xFFFF9800),
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

        Spacer(modifier = Modifier.height(8.dp))

        // Lịch tuần với giao diện mới
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weeklySchedules.forEach { schedule ->
                val isScheduleComplete = schedule.exercises.isNotEmpty() && schedule.exercises.all { it.isCompleted }

                DayItemNew(
                    schedule = schedule,
                    isToday = schedule.date == today,
                    isSelected = schedule.date == selectedDate,
                    isCompleted = isScheduleComplete,
                    today = today,
                    onSelected = { onDateChanged(schedule.date) }
                )
            }
        }
    }
}

@Composable
fun DayItemNew(
    schedule: DailySchedule,
    isToday: Boolean,
    isSelected: Boolean,
    isCompleted: Boolean,
    today: LocalDate,
    onSelected: () -> Unit
) {
    val dayOfWeekMap = mapOf(
        DayOfWeek.MONDAY to "T2",
        DayOfWeek.TUESDAY to "T3",
        DayOfWeek.WEDNESDAY to "T4",
        DayOfWeek.THURSDAY to "T5",
        DayOfWeek.FRIDAY to "T6",
        DayOfWeek.SATURDAY to "T7",
        DayOfWeek.SUNDAY to "CN"
    )

    val fullDayOfWeekMap = mapOf(
        DayOfWeek.MONDAY to "Thứ 2",
        DayOfWeek.TUESDAY to "Thứ 3",
        DayOfWeek.WEDNESDAY to "Thứ 4",
        DayOfWeek.THURSDAY to "Thứ 5",
        DayOfWeek.FRIDAY to "Thứ 6",
        DayOfWeek.SATURDAY to "Thứ 7",
        DayOfWeek.SUNDAY to "CN"
    )

    // Map icon theo ngày trong tuần
    val iconMap = mapOf(
        DayOfWeek.MONDAY to R.drawable.nguc_tay,
        DayOfWeek.TUESDAY to R.drawable.lung_vai,
        DayOfWeek.WEDNESDAY to R.drawable.chan_mong,
        DayOfWeek.THURSDAY to R.drawable.vai_core,
        DayOfWeek.FRIDAY to R.drawable.tay_bung,
        DayOfWeek.SATURDAY to R.drawable.cardio,
        DayOfWeek.SUNDAY to R.drawable.nghi_ngoi
    )

    val dateFormatter = DateTimeFormatter.ofPattern("dd")

    // Tách tên bài tập thành các dòng
    val scheduleNameLines = schedule.scheduleName.split("&").map { it.trim() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(48.dp)
    ) {
        // Chữ T2, T3, T4... nằm trên box
        Text(
            text = dayOfWeekMap[schedule.date.dayOfWeek] ?: "",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Box chứa thông tin với chiều cao cố định
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) Color(0xFF4361EE) else Color.White)
                .clickable { onSelected() }
                .padding(vertical = 8.dp, horizontal = 4.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ngày
                Text(
                    text = schedule.date.format(dateFormatter),
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Thứ 2, Thứ 3...
                Text(
                    text = fullDayOfWeekMap[schedule.date.dayOfWeek] ?: "",
                    fontSize = 9.sp,
                    color = if (isSelected) Color.White else Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Icon tròn với icon riêng cho từng ngày
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.White.copy(alpha = 0.3f) else Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconMap[schedule.date.dayOfWeek] ?: R.drawable.baitap),
                        contentDescription = "Icon bài tập",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Tên bài tập (mỗi phần một dòng)
                if (schedule.scheduleName == "Nghỉ ngơi") {
                    // Trường hợp đặc biệt: Nghỉ ngơi - chỉ hiển thị "Nghỉ"
                    Text(
                        text = "Nghỉ",
                        fontSize = 8.sp,
                        color = if (isSelected) Color.White else Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 10.sp,
                        maxLines = 1
                    )
                } else {
                    // Các bài tập khác có dấu &
                    scheduleNameLines.forEachIndexed { index, line ->
                        Text(
                            text = line,
                            fontSize = 8.sp,
                            color = if (isSelected) Color.White else Color.Black,
                            textAlign = TextAlign.Center,
                            lineHeight = 10.sp,
                            maxLines = 1
                        )
                        // Thêm dấu & giữa các phần (trừ dòng cuối)
                        if (index < scheduleNameLines.size - 1) {
                            Text(
                                text = "&",
                                fontSize = 8.sp,
                                color = if (isSelected) Color.White else Color.Black,
                                textAlign = TextAlign.Center,
                                lineHeight = 10.sp
                            )
                        }
                    }
                }
            }

            // Dấu chấm đỏ cho ngày hôm nay
            if (isToday && !isSelected) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .align(Alignment.TopEnd)
                        .offset(x = (-2).dp, y = 2.dp)
                )
            }

            // Dấu tick xanh nếu đã hoàn thành và là ngày trước hôm nay
            if (isCompleted && schedule.date.isBefore(today)) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Đã hoàn thành",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-2).dp, y = (-2).dp)
                )
            }
        }
    }
}

@Composable
fun ScheduleDetailsCard(schedule: DailySchedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = schedule.scheduleName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            schedule.exercises.forEachIndexed { index, exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onCheckedChange = { isChecked ->
                        schedule.exercises[index] =
                            schedule.exercises[index].copy(isCompleted = isChecked)
                    }
                )
                if (index < schedule.exercises.size - 1) {
                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
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
fun DailyProgressFooter(schedule: DailySchedule) {
    // Nếu là ngày nghỉ ngơi thì hiển thị "Đã hoàn thành"
    if (schedule.exercises.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF4CAF50))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ĐÃ HOÀN THÀNH",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    } else {
        val completedCount = schedule.exercises.count { it.isCompleted }
        val totalCount = schedule.exercises.size

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF4CAF50))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ĐÃ HOÀN THÀNH ${completedCount}/${totalCount} BÀI TẬP",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WeeklyProgressFooter(weeklySchedules: List<DailySchedule>) {
    // Tính tổng số bài tập đã hoàn thành và tổng số bài tập trong tuần (không tính ngày nghỉ)
    val totalCompletedExercises = weeklySchedules.sumOf { schedule ->
        schedule.exercises.count { it.isCompleted }
    }
    val totalExercises = weeklySchedules.sumOf { it.exercises.size }

    val progress = if (totalExercises > 0) totalCompletedExercises.toFloat() / totalExercises else 0f

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
                text = "Tiến độ tuần",
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
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = Color(0xFF4CAF50),
            trackColor = Color(0xFFE0E0E0)
        )
    }
}

// --- HÀM HỖ TRỢ TẠO DỮ LIỆU ---

fun generateWeeklySchedules(startDate: LocalDate): List<DailySchedule> {
    val schedules = mutableListOf<DailySchedule>()
    val chestArmSchedule = listOf(
        Exercise("Đẩy ngực", "Bench Press", "4 hiệp x 8 - 10 lần", iconResourceId = R.drawable.ic_bench_press),
        Exercise("Kéo xà", "Pull-ups", "3 hiệp x tối đa", iconResourceId = R.drawable.ic_pull_ups),
        Exercise("Gập bắp tay", "Bicep Curls", "3 hiệp x 12 - 15 lần", iconResourceId = R.drawable.ic_bicep_curls)
    )
    val backShoulderSchedule = listOf(
        Exercise("Chèo thuyền", "Rows", "4 hiệp x 10 - 12 lần", iconResourceId = R.drawable.ic_bench_press),
        Exercise("Đẩy vai", "Overhead Press", "3 hiệp x 10 - 12 lần", iconResourceId = R.drawable.ic_overhead_press)
    )
    val legSchedule = listOf(
        Exercise("Ngồi xổm", "Squats", "4 hiệp x 8 - 12 lần", iconResourceId = R.drawable.ic_squats),
        Exercise("Đẩy chân", "Leg Press", "3 hiệp x 10 - 12 lần", iconResourceId = R.drawable.ic_leg_press),
        Exercise("Chùng chân", "Lunges", "3 hiệp x 12 lần mỗi chân", iconResourceId = R.drawable.ic_lunges),
        Exercise("Nhón gót", "Calf Raises", "4 hiệp x 15 - 20", iconResourceId = R.drawable.ic_calf_raises)
    )
    val shoulderCoreSchedule = listOf(
        Exercise("Đẩy vai", "Overhead Press", "4 hiệp x 10 - 12 lần", iconResourceId = R.drawable.ic_overhead_press),
        Exercise("Plank", "Plank", "3 hiệp x 60 giây", iconResourceId = R.drawable.ic_bench_press)
    )
    val armAbsSchedule = listOf(
        Exercise("Gập bắp tay", "Bicep Curls", "3 hiệp x 12 - 15 lần", iconResourceId = R.drawable.ic_bicep_curls),
        Exercise("Gập bụng", "Crunches", "3 hiệp x 20 lần", iconResourceId = R.drawable.ic_bench_press)
    )
    val cardioSchedule = listOf(
        Exercise("Chạy bộ", "Running", "30 phút", iconResourceId = R.drawable.ic_bench_press),
        Exercise("Nhảy dây", "Jump Rope", "3 hiệp x 5 phút", iconResourceId = R.drawable.ic_bench_press)
    )

    for (i in 0..6) {
        val currentDate = startDate.plusDays(i.toLong())
        when (currentDate.dayOfWeek) {
            DayOfWeek.MONDAY -> schedules.add(
                DailySchedule(
                    currentDate,
                    "Ngực & Tay",
                    mutableStateListOf<Exercise>().apply { addAll(chestArmSchedule.map { it.copy() }) }
                )
            )
            DayOfWeek.TUESDAY -> schedules.add(
                DailySchedule(
                    currentDate,
                    "Lưng & Vai",
                    mutableStateListOf<Exercise>().apply { addAll(backShoulderSchedule.map { it.copy() }) }
                )
            )
            DayOfWeek.WEDNESDAY -> schedules.add(
                DailySchedule(
                    currentDate,
                    "Chân & Mông",
                    mutableStateListOf<Exercise>().apply { addAll(legSchedule.map { it.copy() }) }
                )
            )
            DayOfWeek.THURSDAY -> schedules.add(
                DailySchedule(
                    currentDate,
                    "Vai & Core",
                    mutableStateListOf<Exercise>().apply { addAll(shoulderCoreSchedule.map { it.copy() }) }
                )
            )
            DayOfWeek.FRIDAY -> schedules.add(
                DailySchedule(
                    currentDate,
                    "Tay & Bụng",
                    mutableStateListOf<Exercise>().apply { addAll(armAbsSchedule.map { it.copy() }) }
                )
            )
            DayOfWeek.SATURDAY -> schedules.add(
                DailySchedule(
                    currentDate,
                    "Cardio",
                    mutableStateListOf<Exercise>().apply { addAll(cardioSchedule.map { it.copy() }) }
                )
            )
            DayOfWeek.SUNDAY -> schedules.add(
                DailySchedule(currentDate, "Nghỉ ngơi", mutableStateListOf())
            )
        }
    }
    return schedules
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    NutriFitTheme {
        ScheduleScreen()
    }
}