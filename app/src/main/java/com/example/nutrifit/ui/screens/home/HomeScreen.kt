package com.example.nutrifit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.nutrifit.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.navOptions
import android.R.attr.onClick
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.nutrifit.ui.navigation.NavRoutes
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrifit.viewmodel.HomeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Collect states from ViewModel
    val targetState by viewModel.targetState.collectAsState()
    val mealSuggestions by viewModel.mealSuggestions.collectAsState()
    val workoutSuggestions by viewModel.workoutSuggestions.collectAsState()
    val selectedMeal by viewModel.selectedMeal.collectAsState()
    val selectedGoal by viewModel.selectedGoal.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF0F1F3))
        .padding(WindowInsets.statusBars.asPaddingValues())
    )
    {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                // khung nutri fit va avt
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.home1),
                        contentDescription = "khung chua chu nutri fit va avt",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Row(){
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFF1AC9AC),
                                        fontWeight = FontWeight.Bold
                                    )
                                ) { append("NUTRI") }
                                withStyle(
                                    style = SpanStyle(
                                        color = colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) { append(" - ") }
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) { append("FIT") }
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 37.sp,
                            modifier = Modifier.padding(top = 16.dp, start = 30.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Box(modifier = Modifier.padding(top = 10.dp, end = 30.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.bgavt),
                                contentDescription = "avt",
                                modifier = Modifier
                                    .size(63.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.avt),
                                contentDescription = "avt",
                                modifier = Modifier
                                    .size(63.dp)
                            )
                        }
                    }
                }
            }
            // dong chu "chao ban!", co the thay doi thanh "chao (username)"
            item{
                //khoang cach voi thanh phan o tren
                Spacer(modifier = Modifier.height(25.dp))
                // tao column de can le trai
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Chào bạn!",
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 25.dp)
                    )
                }
            }

            //khung muc tieu
            item{
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier .fillMaxSize()
                        .padding(start = 17.dp, end = 17.dp)
                ){
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.khungmuctieu),
                            contentDescription = "khung muc tieu",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(420.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box() {
                                    Image(
                                        painter = painterResource(id = R.drawable.bieutuonglop1),
                                        contentDescription = "bieu tuong lop thu 1",
                                        modifier = Modifier
                                            .size(60.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.bieutuonglop2),
                                        contentDescription = "bieu tuong lop thu 2",
                                        modifier = Modifier
                                            .offset(x = 3.dp, y = 3.dp)
                                            .size(55.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.bieutuonglop3),
                                        contentDescription = "bieu tuong lop thu 3",
                                        modifier = Modifier
                                            .size(60.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column() {
                                    Text(
                                        text = "Mục tiêu: ${targetState.title}",
                                        fontSize = 13.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(7.dp))
                                    Text(
                                        text = "Mục tiêu: ${targetState.caloriesGoal} calo/ngày",
                                        fontSize = 11.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { navController.navigate(NavRoutes.Schedule)},
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(top = 10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF293BB1)
                                    )
                                ) {
                                    Text(
                                        "xem lịch",
                                        fontSize = 13.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            // Progress value (0f -> 1f)
                            val progress = targetState.progress
                            val backgroundColor = Color(0xFFD9D9D9)
                            val progressBarColor = Color(0xFF4CAF50)
                            Spacer(modifier = Modifier .height(20.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(15.dp)
                                    .background(backgroundColor, shape = RoundedCornerShape(50))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(progress)
                                        .fillMaxHeight()
                                        .background(progressBarColor, shape = RoundedCornerShape(50))
                                )
                            }

                            Spacer(modifier = Modifier .height(20.dp))
                            Row() {
                                Text(
                                    text = "Đã đạt: ${targetState.currentCalories}",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "${(targetState.progress * 100).toInt()}%",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier .height(20.dp))
                            Text(
                                text = "Tiến trình hàng tuần",
                                fontSize = 17.sp,
                                color = Color.Black,
                            )

                            // ===== BIỂU ĐỒ TIẾN TRÌNH =====
                            Spacer(modifier = Modifier.height(6.dp))

                            val days = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
                            val values = listOf(2.9f, 3.0f, 5.1f, 7.1f, 3.2f, 3.0f, 0f)
                            val targetValue = 3.0f

                            val maxValue = maxOf(values.maxOrNull() ?: 0f, targetValue)
                            val chartProgressColor = Color(0xFF3BAE4A)
                            val targetColor = Color(0xFFFF6B6B)
                            val gridColor = Color(0xFFE0E0E0)

                            val calorieLevels = listOf(3.2f, 3.0f, 2.8f, 2.6f)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .background(Color.White, RoundedCornerShape(20.dp))
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Cột hiển thị số calo bên trái
                                    Column(
                                        modifier = Modifier.width(32.dp),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        calorieLevels.forEach { calorie ->
                                            Text(
                                                text = "%.1f".format(calorie),
                                                fontSize = 9.sp,
                                                color = Color.Gray,
                                                modifier = Modifier.padding(bottom = 12.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    // Biểu đồ chính
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp)
                                        ) {
                                            Canvas(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                val width = size.width
                                                val height = size.height

                                                // Vẽ đường lưới ngang
                                                calorieLevels.forEach { calorie ->
                                                    val y = height - (calorie / maxValue) * height
                                                    drawLine(
                                                        color = gridColor,
                                                        start = Offset(0f, y),
                                                        end = Offset(width, y),
                                                        strokeWidth = 1f
                                                    )
                                                }

                                                // Vẽ đường mục tiêu
                                                val targetY = height - (targetValue / maxValue) * height
                                                drawLine(
                                                    color = targetColor,
                                                    start = Offset(0f, targetY),
                                                    end = Offset(width, targetY),
                                                    strokeWidth = 1.5f,
                                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 3f))
                                                )

                                                // Tính toán vị trí các điểm
                                                val barWidth = width / (days.size * 2 - 1)
                                                val points = values.mapIndexed { index, value ->
                                                    Offset(
                                                        x = barWidth + (index * 2 * barWidth),
                                                        y = if (value > 0) height - (value / maxValue) * height else height
                                                    )
                                                }

                                                // Vẽ đường nối các điểm
                                                if (points.size > 1) {
                                                    for (i in 0 until points.size - 1) {
                                                        if (values[i] > 0 && values[i + 1] > 0) {
                                                            drawLine(
                                                                color = chartProgressColor,
                                                                start = points[i],
                                                                end = points[i + 1],
                                                                strokeWidth = 2f,
                                                                cap = StrokeCap.Round
                                                            )
                                                        }
                                                    }
                                                }

                                                // Vẽ các điểm
                                                points.forEachIndexed { index, point ->
                                                    if (values[index] > 0) {
                                                        // Vòng tròn lớn màu xanh nhạt
                                                        drawCircle(
                                                            color = chartProgressColor.copy(alpha = 0.2f),
                                                            radius = 10f,
                                                            center = point
                                                        )
                                                        // Điểm tròn màu xanh đậm
                                                        drawCircle(
                                                            color = chartProgressColor,
                                                            radius = 5f,
                                                            center = point
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        // Hiển thị các ngày
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            days.forEach { day ->
                                                Text(
                                                    text = day,
                                                    fontSize = 11.sp,
                                                    color = Color.Gray,
                                                    modifier = Modifier.weight(1f),
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        // Chú thích
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Chú thích lượng calo
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 6.dp)
                                            ) {
                                                Canvas(modifier = Modifier.size(10.dp)) {
                                                    drawCircle(color = chartProgressColor, radius = size.height / 2)
                                                }
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Lượng calo", fontSize = 11.sp, color = Color.Gray)
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            // Chú thích mục tiêu
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 6.dp)
                                            ) {
                                                Canvas(modifier = Modifier.size(10.dp)) {
                                                    drawLine(
                                                        color = targetColor,
                                                        start = Offset(0f, size.height / 2),
                                                        end = Offset(size.width, size.height / 2),
                                                        strokeWidth = 1.5f,
                                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f))
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Mục tiêu", fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // khung chua 4 nut bam: thuc don, bai tap, kiem tra, lich tap
            item {
                Spacer(modifier = Modifier.height(7.dp))
                Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp) .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.khung4nut),
                            contentDescription = "",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Nut1: thuc don
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(6.dp)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.khungnho4nut),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = R.drawable.thucdon),
                                        contentDescription = "Thực đơn",
                                        modifier = Modifier.size(45.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Thực đơn", fontSize = 11.sp, color = Color.Black)
                                }
                            }

                            // Nut 2: baitap
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(6.dp)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.khungnho4nut),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = R.drawable.baitap),
                                        contentDescription = "baitap",
                                        modifier = Modifier.size(45.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Bài tập", fontSize = 11.sp, color = Color.Black)
                                }
                            }

                            // Nut3: Kiem tra dinh duong
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(6.dp)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.khungnho4nut),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = R.drawable.kiemtra),
                                        contentDescription = "kiemtra",
                                        modifier = Modifier.size(45.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Kiểm tra", fontSize = 11.sp, color = Color.Black)
                                }
                            }

                            // Nut4: lichtap
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(6.dp)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.khungnho4nut),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = R.drawable.lichtap),
                                        contentDescription = "lichtap",
                                        modifier = Modifier.size(45.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Lịch tập", fontSize = 11.sp, color = Color.Black)
                                }
                            }
                        }
                    }

                }
            }

            //de xuat bua an
            item{
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Đề xuất bữa ăn",
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 25.dp)
                    )
                }
            }

            //sang trua chieu toi - SỬA THÀNH selectedMeal
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Nút Sáng
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedMeal == "Sáng") Color.Black else Color(0xFFEFF6F0))
                            .clickable { viewModel.updateSelectedMeal("Sáng") }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sáng",
                            fontSize = 16.sp,
                            color = if (selectedMeal == "Sáng") Color.White else Color.Gray
                        )
                    }

                    // Nút Trưa
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedMeal == "Trưa") Color.Black else Color(0xFFEFF6F0))
                            .clickable { viewModel.updateSelectedMeal("Trưa") }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Trưa",
                            fontSize = 16.sp,
                            color = if (selectedMeal == "Trưa") Color.White else Color.Gray
                        )
                    }

                    // Nút Chiều
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedMeal == "Chiều") Color.Black else Color(0xFFEFF6F0))
                            .clickable { viewModel.updateSelectedMeal("Chiều") }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Chiều",
                            fontSize = 16.sp,
                            color = if (selectedMeal == "Chiều") Color.White else Color.Gray
                        )
                    }

                    // Nút Tối
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedMeal == "Tối") Color.Black else Color(0xFFEFF6F0))
                            .clickable { viewModel.updateSelectedMeal("Tối") }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tối",
                            fontSize = 16.sp,
                            color = if (selectedMeal == "Tối") Color.White else Color.Gray
                        )
                    }
                }
            }

            // tang co, tang can, giam can, giu dang - SỬA THÀNH selectedGoal
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Nút Tăng cơ
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedGoal == "Tăng cơ") Color.Black else Color(0xFFF2F6F3))
                            .clickable { viewModel.updateSelectedGoal("Tăng cơ") }
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tăng cơ",
                            fontSize = 13.sp,
                            color = if (selectedGoal == "Tăng cơ") Color.White else Color.Gray
                        )
                    }

                    // Nút Tăng cân
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedGoal == "Tăng cân") Color.Black else Color(0xFFF2F6F3))
                            .clickable { viewModel.updateSelectedGoal("Tăng cân") }
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tăng cân",
                            fontSize = 13.sp,
                            color = if (selectedGoal == "Tăng cân") Color.White else Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedGoal == "Giảm cân") Color.Black else Color(0xFFF2F6F3))
                            .clickable { viewModel.updateSelectedGoal("Giảm cân") }
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Giảm cân",
                            fontSize = 13.sp,
                            color = if (selectedGoal == "Giảm cân") Color.White else Color.Gray
                        )
                    }

                    // Nút Giữ dáng
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedGoal == "Giữ dáng") Color.Black else Color(0xFFF2F6F3))
                            .clickable { viewModel.updateSelectedGoal("Giữ dáng") }
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Giữ dáng",
                            fontSize = 13.sp,
                            color = if (selectedGoal == "Giữ dáng") Color.White else Color.Gray
                        )
                    }
                }
            }

            // dieu kien hien thi cac bua an
            item {
                // Điều kiện hiển thị (Sáng và Tăng cơ)
                if (selectedMeal == "Sáng" && selectedGoal == "Tăng cơ") {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color(0xFFF7FCFF), RoundedCornerShape(12.dp))
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                        ){
                            Column(
                                modifier = Modifier
                                    .offset(x = -10.dp)
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(Color(0xFFF7FCFF), RoundedCornerShape(12.dp))
                            ) {
                                // Hàng 1 - 2 món
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    // Món 1: Trứng ốp la & Bánh mì
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.trungvabanhmi),
                                                contentDescription = "trungvabanhmi",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Trứng ốp la & Bánh mì",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•2 trứng",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•1 ổ bánh mì",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•dưa leo",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "228 kcal",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -40.dp)
                                                )
                                            }
                                        }
                                    }

                                    // Món 2: Yến mạch & Sữa tươi
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.yenmachsuatuoi),
                                                contentDescription = "yenmachsuatuoi",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Yến mạch & Sữa tươi",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•4 muỗng yến mạch",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•200ml sữa tươi",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = " không đường",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "250 kcal",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -40.dp)
                                                )
                                            }
                                        }
                                    }
                                }



                                // Hàng 2 - 2 món
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = -50.dp)
                                        .padding(horizontal = 10.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    // Món 3: Khoai lang & Ức Gà
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.khoailangucga),
                                                contentDescription = "khoailangucga",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Khoai lang & Ức Gà",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•150g khoai lang",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•80g ức gà áp chảo",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•rau xà lách",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "260 kcal",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -40.dp)
                                                )
                                            }
                                        }
                                    }

                                    // Món 4: Sữa chua Hy Lạp Trái cây
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.suachuatraicay),
                                                contentDescription = "sua chua trai cay",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = " Sữa chua Hy Lạp Trái cây",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•100g sữa chua Hy Lạp",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•50g việt quất",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•1 thìa hạt chia",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "210 kcal",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -40.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Nút Xem thêm
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = -30.dp)
                                        .padding(vertical = 16.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Xem thêm",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4CAF50),
                                        modifier = Modifier.clickable(onClick = { })
                                    )
                                }
                            }
                        }
                    }
                }

                // Có thể thêm các điều kiện khác ở đây
                if (selectedMeal == "Trưa" && selectedGoal == "Tăng cơ") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color.White, RoundedCornerShape(12.dp))
                    ) {
                        Text(
                            text = "🍲 Thực đơn trưa cho Tăng cơ",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("• Cơm trắng: 2 bát", fontSize = 14.sp)
                        Text("• Ức gà: 200g", fontSize = 14.sp)
                        Text("• Rau xanh các loại", fontSize = 14.sp)
                        Text("• Đậu phụ: 150g", fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Tổng calorie: ~750 kcal",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF4CAF50))
                    }
                }
            }

            item{
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -60.dp)
                        .padding(start = 20.dp, end = 20.dp)) {
                    Image(
                            painter = painterResource(id = R.drawable.hinhminhhoa),
                        contentDescription = "anh minh hoa",
                        modifier = Modifier.size(390.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        modifier = Modifier
                            .offset(y = -130.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFF7FCFF),
                                RoundedCornerShape(12.dp))
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Bài tập đề xuất",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Xem tất cả",
                                    fontSize = 15.sp,
                                    modifier = Modifier
                                        .clickable(onClick = { })
                                        .padding(end = 30.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50),
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(modifier = Modifier
                                .fillMaxWidth()
                            ){
                                // Hàng 1 - 2 món
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    //Phan 1 Squat
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.squat),
                                                contentDescription = "Squat với tạ",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Squat với tạ",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•4 hiệp × 8–10 lần",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•Tập cơ đùi, mông, bụng",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "Thời gian: 20 phút",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                            }
                                        }
                                    }

                                    // Phan 2 Bench Press
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.bench),
                                                contentDescription = "Bench Press",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Bench Press",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•4 hiệp × 8–10 lần",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•Tập cơ ngực, tay sau,",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = " vai trước",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "Thời gian: 30 phút",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                            }
                                        }
                                    }
                                }



                                // Hàng 2 - 2 món
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .offset(y= -50.dp ),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    // Phan 3: Deadlift
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.deadlift),
                                                contentDescription = "Deadlift",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Deadlift",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•4 hiệp × 6–8 lần",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•Tập cơ lưng, mông,",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = " đùi sau",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "Thời gian: 10 phút",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                            }
                                        }
                                    }

                                    // phan 4 pullup
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(170.dp)
                                    ) {
                                        Box(  contentAlignment = Alignment.Center ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.pullup),
                                                contentDescription = "pullup",
                                                modifier = Modifier
                                                    .size(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier .padding(top = 130.dp)) {
                                                Text(
                                                    text = "Pull-up",
                                                    fontSize = 10.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -6.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•4 hiệp × tối đa lần",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -15.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "•Tập cơ lưng xô,",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -30.dp)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = " tay trước",
                                                    fontSize = 9.sp,
                                                    color = Color.Gray,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                                Text(
                                                    text = "Thời gian: 15 phút",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4CAF50),
                                                    modifier = Modifier
                                                        .offset(y = -45.dp)
                                                )
                                            }
                                        }
                                    }
                                }


                            }

                            Button(
                                onClick = {
                                    scope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF293BB1),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Quay lại đầu trang")
                            }

                        }










                    }
                }


            }

        }
    }
}