package com.example.nutrifit.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.systemBarsPadding
import com.example.nutrifit.R

@Composable
fun WorkoutScreen() {
    // Sử dụng Scaffold với systemBarsPadding để xử lý camera notch
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding(), // QUAN TRỌNG: Thêm padding cho system bars
        content = { paddingValues ->
            WorkoutContent(paddingValues = paddingValues)
        }
    )
}

@Composable
fun WorkoutContent(paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Tiêu đề chính - đã được bảo vệ bởi systemBarsPadding
        item {
            Text(
                text = "Chọn nhóm cơ bạn muốn luyện tập",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        // Lựa chọn nhóm cơ
        item {
            MuscleGroupSelection()
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bài tập phù hợp
        item {
            Text(
                text = "Bài tập phù hợp cho sự lựa chọn của bạn",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Hình ảnh bài tập
        item {
            WorkoutImage(contentDescription = "Bài tập Squats")
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Tên bài tập và hướng dẫn
        item {
            ExerciseDescription()
        }

        // Video hướng dẫn
        item {
            VideoSection()
        }

        // Phòng gym gần đây
        item {
            GymSection()
        }

        // Bản đồ
        item {
            MapSection()
        }
    }
}

@Composable
fun MuscleGroupSelection() {
    val selectedGroup = remember { mutableStateOf("") }

    val groups = listOf(
        MuscleGroup("Vai", R.drawable.ic_muscle_vai),
        MuscleGroup("Ngực", R.drawable.ic_muscle_nguc),
        MuscleGroup("Lưng", R.drawable.ic_muscle_lung),
        MuscleGroup("Tay sau", R.drawable.ic_muscle_taysau),
        MuscleGroup("Tay trước", R.drawable.ic_muscle_taytruoc),
        MuscleGroup("Bắp chân", R.drawable.ic_muscle_bapchan),
        MuscleGroup("Đùi trước", R.drawable.ic_muscle_duitruoc),
        MuscleGroup("Đùi sau", R.drawable.ic_muscle_duisau),
        MuscleGroup("Mông", R.drawable.ic_muscle_mong)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFFE3F2FD)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val chunkedGroups = groups.chunked(3)
            chunkedGroups.forEach { rowGroups ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowGroups.forEach { muscle ->
                        MuscleBox(
                            muscle = muscle,
                            isSelected = selectedGroup.value == muscle.name,
                            onClick = { selectedGroup.value = muscle.name }
                        )
                    }
                    // Thêm Spacer cho các hàng không đủ 3 item
                    repeat(3 - rowGroups.size) {
                        Spacer(modifier = Modifier.width(100.dp))
                    }
                }
                if (rowGroups != chunkedGroups.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun MuscleBox(muscle: MuscleGroup, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF2196F3) else Color.White
    val textColor = if (isSelected) Color.White else Color(0xFF333333)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    if (isSelected) Color.White else Color.Transparent,
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = muscle.imageRes),
                contentDescription = muscle.name,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = muscle.name,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ExerciseDescription() {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(
            text = "SQUATS VỚI TẠ",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Đứng thẳng, hai chân rộng bằng vai, giữ tạ trên vai (barbell) hoặc hai bên tay (dumbbell). Hít sâu, hạ người xuống bằng cách đẩy hông ra sau, lưng thẳng, gối không vượt quá mũi chân. Khi đùi song song mặt đất thì hô ra, đẩy gót chân đứng lên lại.",
            fontSize = 14.sp,
            color = Color(0xFF666666),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun VideoSection() {
    Column {
        Text(
            text = "Video hướng dẫn",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        VideoItem(
            title = "Hướng dẫn Squats cơ bản",
            duration = "5:30",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        VideoItem(
            title = "Kỹ thuật Squats nâng cao",
            duration = "7:15",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun GymSection() {
    Column {
        Text(
            text = "Các phòng gym gần đây",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        gymList.forEach { gym ->
            GymRowItem(gym = gym)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF2196F3)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Các phòng gym ở phạm vi 5 km gần đây",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MapSection() {
    Column {
        MapImage(contentDescription = "Bản đồ phòng gym")
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun WorkoutImage(contentDescription: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_workout),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun VideoItem(title: String, duration: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        elevation = 2.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFFF8F9FA)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF2196F3), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Thời lượng: $duration",
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
            }

            Text(
                text = duration,
                fontSize = 12.sp,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun GymRowItem(gym: Gym) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color(0xFF2196F3),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = gym.name,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = gym.distance,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun MapImage(contentDescription: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8E8E8))
        ) {
            Image(
                painter = painterResource(id = R.drawable.sample_map),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Card(
                    backgroundColor = Color.White,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Bản đồ phòng gym",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

data class MuscleGroup(val name: String, val imageRes: Int)

data class Gym(val name: String, val distance: String)

val gymList = listOf(
    Gym("Saigon Sports Club", "600m"),
    Gym("Fit24 - Fitness", "850m"),
    Gym("The New Gym", "1.2km"),
    Gym("Transform GYM", "1.7km"),
    Gym("CityGym", "2.5km"),
    Gym("FTC Fitness", "3km"),
    Gym("Ways Station Gym", "3.9km")
)