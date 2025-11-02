package com.example.nutrifit.ui.screens.meal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutrifit.R
import androidx.compose.foundation.BorderStroke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(mealId: Int, navController: NavController?) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.meal_khoai_lang_uc_ga),
                contentDescription = "Khoai lang & ức gà",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)),
                contentScale = ContentScale.Crop
            )

            Card(
                onClick = { navController?.popBackStack() },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .offset(y = 25.dp)
                    .width(100.dp)
                    .height(36.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.6f)
                )
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Quay về",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Khoai lang & Ức gà",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                fontSize = 26.sp,
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(3.dp)
                    .offset(y = (-15).dp)
                    .background(Color(0xFFC9B5F8))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NutritionCard(
                title = "110 kcal",
                subtitle = "Năng lượng",
                color = Color(0xFFFFE9CC),
                textColor = Color(0xFF333333),
                icon = Icons.Default.Whatshot,
                iconColor = Color(0xFFFF6B35),
                modifier = Modifier.weight(1.5f)
            )

            NutritionCard(
                title = "30 phút",
                subtitle = "Thời gian",
                color = Color(0xFFF2E7FB),
                textColor = Color(0xFF333333),
                icon = Icons.Default.Schedule,
                iconColor = Color(0xFF9C27B0),
                modifier = Modifier.weight(1.3f)
            )

            NutritionCard(
                title = "Rất dễ",
                subtitle = "Mức độ",
                color = Color(0xFFE9F8E6),
                textColor = Color(0xFF333333),
                icon = Icons.Default.Check,
                iconColor = Color(0xFF4CAF50),
                modifier = Modifier.weight(1.3f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NutritionRingCard(
                percentage = 48,
                title = "Protein",
                grams = "33g",
                ringColor = Color(0xFFFFA25C),
                backgroundColor = Color(0xFFF9D8E0),
                modifier = Modifier.weight(1f)
            )

            NutritionRingCard(
                percentage = 38,
                title = "Tinh bột",
                grams = "26g",
                ringColor = Color(0xFFA5E3A4),
                backgroundColor = Color(0xFFE4F9E3),
                modifier = Modifier.weight(1f)
            )

            NutritionRingCard(
                percentage = 11,
                title = "Chất béo",
                grams = "3.5g",
                ringColor = Color(0xFFFFA25C),
                backgroundColor = Color(0xFFFFE8D1),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        DescriptionSection()

        Spacer(modifier = Modifier.height(24.dp))

        CookingInstructionsSection()

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController?.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 20.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp),
                    clip = false
                ),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1C3C64)
            )
        ) {
            Text(
                text = "Xem thêm món ăn khác",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}

@Composable
fun NutritionCard(
    title: String,
    subtitle: String,
    color: Color,
    textColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = subtitle,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun NutritionRingCard(
    percentage: Int,
    title: String,
    grams: String,
    ringColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.TopCenter)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 6.dp.toPx()
                    val radius = (size.minDimension - strokeWidth) / 2
                    val center = Offset(size.width / 2, size.height / 2)

                    drawCircle(
                        color = backgroundColor,
                        radius = radius,
                        center = center,
                        style = Stroke(strokeWidth)
                    )

                    val sweepAngle = (percentage * 360f) / 100f
                    drawArc(
                        color = ringColor,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(strokeWidth)
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A),
                        fontSize = 14.sp
                    )
                    Text(
                        text = grams,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF666666),
                        fontSize = 10.sp
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun DescriptionSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(2.dp, Color(0xFFF8D766).copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mô tả phần ăn",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(3.dp)
                        .offset(y = (-12).dp)
                        .background(Color(0xFFC9B5F8))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Một phần ăn gồm 130g khoai lang và 100g ức gà, cung cấp ~275 kcal với 48% protein, 38% tinh bột và 11% chất béo. Đây là bữa ăn giàu protein, ít chất béo, giúp phục hồi năng lượng nhanh chóng và hỗ trợ xây dựng cơ bắp sau khi tập luyện. Khoai lang đem lại tinh bột tốt, trong khi ức gà cung cấp protein nạc, dễ tiêu hóa.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666),
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
fun CookingInstructionsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(2.dp, Color(0xFF5877A4))

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cách chế biến",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(3.dp)
                        .offset(y = (-12).dp)
                        .background(Color(0xFFC9B5F8))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            NumberedInstruction(
                number = 1,
                title = "Sơ chế",
                description = "• Rửa sạch 130g khoai lang, để nguyên vỏ hoặc gọt tùy ý.\n• Chuẩn bị 100g ức gà, rửa sạch, để ráo, có thể ướp với muối, tiêu, tỏi trong 5-10 phút."
            )

            Spacer(modifier = Modifier.height(8.dp))

            NumberedInstruction(
                number = 2,
                title = "Nấu khoai lang",
                description = "• Luộc hoặc hấp khoai trong 20-25 phút cho mềm.\n• Hoặc nướng 180°C trong 25-30 phút (thơm ngon, ngọt hơn)."
            )

            Spacer(modifier = Modifier.height(8.dp))

            NumberedInstruction(
                number = 3,
                title = "Chế biến ức gà",
                description = "• Luộc đun sôi nước, cho ức gà vào, luộc 15-20 phút, để nguội thái lát.\n• Nướng lò 180-200°C trong 18-20 phút.\n• Áp chảo cho ít dầu ăn, áp chảo 7-8 phút mỗi mặt."
            )

            Spacer(modifier = Modifier.height(8.dp))

            NumberedInstruction(
                number = 4,
                title = "Hoàn thành",
                description = "• Ăn kèm ức gà với khoai lang.\n• Có thể thêm rau xanh hoặc salad để cân bằng dinh dưỡng."
            )
        }
    }
}

@Composable
fun NumberedInstruction(number: Int, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$number.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C3C64),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1C3C64),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF333333),
                lineHeight = 20.sp,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=877dp")
@Composable
fun MealDetailScreenPreview() {
    MaterialTheme {
        MealDetailScreen(mealId = 1, navController = null)
    }
}