package com.example.nutrifit.ui.screens.target

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrifit.R
import com.example.nutrifit.viewmodel.TargetViewModel

@Composable
fun TargetScreen(
    onBack: () -> Unit,
    onNextClicked: () -> Unit
) {
    val viewModel: TargetViewModel = viewModel()
    val context = LocalContext.current
    val saveState by viewModel.saveState.collectAsState()

    var selectedGoal by remember { mutableStateOf("") }

    LaunchedEffect(saveState) {
        when (val state = saveState) {
            is TargetViewModel.SaveState.Success -> {
                Toast.makeText(context, "Goal saved!", Toast.LENGTH_SHORT).show()
                onNextClicked()
            }
            is TargetViewModel.SaveState.Error -> {
                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F1F3))
            .padding(WindowInsets.statusBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // thanh tien trinh
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .offset(y = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector_2),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(1.dp))

            Image(
                painter = painterResource(id = R.drawable.vector_1),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row() {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .offset(x = (-5).dp)
            )
            Text(
                text = "Mục tiêu của bạn là gì?",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .offset(y = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Chúng tôi sẽ cá nhân hóa lộ trình tập luyện và chế độ ăn uống theo mục tiêu của bạn.",
                fontSize = 14.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .padding(bottom = 110.dp)
                .padding(start = 17.dp, end = 17.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.khungnho2),
                contentDescription = null,
                modifier = Modifier
                    .size(800.dp)
                    .clip(RoundedCornerShape(25.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val goals = listOf(
                    "Ăn uống lành mạnh",
                    "Giảm cân",
                    "Tăng cơ / Tăng cân",
                    "Giữ dáng / Duy trì sức khỏe"
                )

                goals.forEach { goal ->
                    GoalOption(goal, selectedGoal == goal) {
                        selectedGoal = goal
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Quay lại",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .clickable {
                            onBack()
                        }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .offset(y = 60.dp)
                    .fillMaxSize()

            ) {
                if (selectedGoal.isEmpty()) {
                    Text(
                        text = "Tiếp tục",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                    )
                } else {
                    Button(
                        onClick = {
                            viewModel.saveGoal(selectedGoal)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // xanh dương
                        shape = RoundedCornerShape(8.dp), // bo nhẹ góc
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(48.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "Tiếp tục",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoalOption(goal: String, isSelected: Boolean, onClick: () -> Unit) {
    val description = when (goal) {
        "Ăn uống lành mạnh" -> "Đốt mỡ, thon gọn một cách khoa học"
        "Giảm cân" -> "Phát triển cơ bắp, tăng cân lành mạnh"
        "Tăng cơ / Tăng cân" -> "Cân bằng tập luyện và dinh dưỡng luôn khỏe mạnh"
        "Giữ dáng / Duy trì sức khỏe" -> "Xây dựng thói quen ăn uống khoa học và bền vững"
        else -> ""
    }

    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Image(
            painter = painterResource(id = if (isSelected) R.drawable.khungnutbam2_2_ else R.drawable.khungnutbam2),
            contentDescription = null,
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.offset(x = 50.dp, y = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = goal,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(x = 10.dp)
                )
                Text(
                    text = description,
                    fontSize = 9.sp,
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .padding(end = 40.dp)
                    .size(27.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) Color(0xFF4CAF50) else Color.Transparent
                    )
                    .border(
                        width = 2.dp,
                        color = if (isSelected) Color(0xFF4CAF50) else Color.Gray,
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checked",
                        tint = Color.White
                    )
                }
            }
        }
    }
}