//package com.example.nutrifit.ui.screens.meal
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.nutrifit.R
//
//@Composable
//fun MealDetailScreen(navController: NavController) { // THÊM NAVCONTROLLER PARAMETER
//    val meal = Meal(1, "Khoai lang & Ức gà", "Bữa ăn giàu protein", R.drawable.klug, "350 cal")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .verticalScroll(rememberScrollState())
//    ) {
//        // Header Image
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(250.dp)
//        ) {
//            Image(
//                painter = painterResource(id = meal.imageRes),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//
//            // Back button - SỬA ICON VÀ THÊM CLICK LISTENER
//            Icon(
//                imageVector = Icons.Default.ArrowBack, // SỬA THÀNH ICON VECTOR
//                contentDescription = "Back",
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(24.dp)
//                    .background(Color.White.copy(alpha = 0.8f), CircleShape)
//                    .padding(4.dp)
//                    .clickable { navController.popBackStack() }, // THÊM CLICK LISTENER
//                tint = Color.Black
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Meal title
//        Text(
//            text = "Khoai lang & Ức gà",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            textAlign = TextAlign.Center,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Divider line
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(Color.LightGray)
//                .padding(horizontal = 16.dp)
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Nutrition info section
//        Text(
//            text = "Một tế phân ăn",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Một phân ăn gồm 130g khoai lang và 70g ức gà, cung cấp 75% khẩu phần ăn được khuyến nghị, 35% protein và 1% chất béo. Đây là bữa ăn giàu protein, ít chất béo, giàu chất xơ và hỗ trợ tiêu hóa. Khoai lang đem lại nguồn tinh bột tốt cùng với protein từ ức gà, dễ tiêu hóa.",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            fontSize = 14.sp,
//            lineHeight = 20.sp,
//            color = Color.Gray,
//            textAlign = TextAlign.Justify
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Preparation section
//        Text(
//            text = "Cách chế biến",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Preparation steps
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        ) {
//            PreparationStep("1. Sơ chế", "- Rửa sạch 130g khoai lang, để nguyên vỏ\n- Chuẩn bị 70g ức gà\n- Ướp gia vị trong 5 phút")
//            Spacer(modifier = Modifier.height(12.dp))
//            PreparationStep("2. Nấu chín", "- Hấp khoai lang trong 25-30 phút\n- Áp chảo ức gà trong 7-8 phút\n- Nướng ở 180-200°C trong 15-20 phút")
//            Spacer(modifier = Modifier.height(12.dp))
//            PreparationStep("3. Hoàn thiện", "- Trình bày ra đĩa\n- Thêm gia vị nếu cần\n- Thưởng thức ngay khi còn nóng")
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Nutrition facts table
//        NutritionTable()
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Action buttons
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            ActionButton("Lưu công thức", Color(0xFF4CAF50))
//            ActionButton("Chia sẻ", Color(0xFF2196F3))
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//    }
//}
//
//@Composable
//fun PreparationStep(stepTitle: String, stepDescription: String) {
//    Column {
//        Text(
//            text = stepTitle,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = stepDescription,
//            fontSize = 14.sp,
//            color = Color.Gray,
//            lineHeight = 20.sp
//        )
//    }
//}
//
//@Composable
//fun ActionButton(text: String, backgroundColor: Color) {
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .background(backgroundColor)
//            .padding(horizontal = 24.dp, vertical = 12.dp)
//    ) {
//        Text(
//            text = text,
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Medium,
//            color = Color.White
//        )
//    }
//}