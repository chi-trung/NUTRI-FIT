package com.example.nutrifit.ui.screens.meal

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutrifit.R

// Data classes remain the same, but ensure calories is Int
data class Meal(
    val id: Int,
    val name: String,
    val description: String,
    val imageRes: Int,
    val calories: Int, // Keep as Int
    val time: String,
    val category: String
)

data class FoodCategory(
    val id: Int,
    val name: String,
    val iconRes: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealScreen(navController: NavController) {

    // Data is still static for now
    val featuredMeals = listOf(
        Meal(1, "Khoai lang & Ức gà", "Món ăn được thích nhiều nhất", R.drawable.klug, 350, "15 phút", "Main"),
        Meal(2, "Salmon & Broccoli", "Món ăn phù hợp với bạn", R.drawable.stbo, 420, "20 phút", "Main"),
        Meal(3, "Sinh tố Xoài", "Món uống bổ dưỡng", R.drawable.stxoai, 180, "5 phút", "Drink")
    )

    val categories = listOf(
        FoodCategory(1, "Tất cả", R.drawable.ic_all),
        FoodCategory(2, "Rau củ", R.drawable.ic_vegetable),
        FoodCategory(3, "Cơm", R.drawable.ic_rice),
        FoodCategory(4, "Món nước", R.drawable.ic_soup),
        FoodCategory(5, "Nước uống", R.drawable.ic_drink),
        FoodCategory(6, "Sinh tố", R.drawable.ic_smoothie)
    )

    val allMeals = listOf(
        Meal(1, "Khoai lang & Ức gà", "Bữa ăn giàu protein", R.drawable.klug, 350, "15 phút", "Main"),
        Meal(2, "Salmon & Broccoli", "Omega-3 và chất xơ", R.drawable.stbo, 420, "20 phút", "Main"),
        Meal(3, "Salad đậu", "Protein thực vật", R.drawable.stdau, 280, "10 phút", "Main"),
        Meal(4, "Táo & Hạnh nhân", "Ăn nhẹ lành mạnh", R.drawable.sttao, 200, "5 phút", "Snack"),
        Meal(5, "Sinh tố Xoài", "Vitamin và khoáng chất", R.drawable.stxoai, 180, "5 phút", "Drink"),
        Meal(6, "Sinh tố Dâu", "Chất chống oxy hóa", R.drawable.stdau, 150, "5 phút", "Drink"),
        Meal(7, "Sinh tố Chuối", "Năng lượng tự nhiên", R.drawable.sttao, 200, "5 phút", "Drink")
    )

    val selectedCategory = remember { mutableStateOf("Tất cả") }
    val searchQuery = remember { mutableStateOf("") }
    val pagerState = rememberPagerState(pageCount = { featuredMeals.size })
    val showMealTimeDialog = remember { mutableStateOf(false) }
    val selectedMealTime = remember { mutableStateOf("Buổi sáng") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(WindowInsets.statusBars.asPaddingValues()),
        contentPadding = PaddingValues(bottom = 150.dp)
    ) {
        item {
            Text(
                text = "Những món ăn hấp dẫn",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                placeholder = { Text("Tìm kiếm món ăn của bạn", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF9F9F9),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color.Transparent,
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            HorizontalPager(state = pagerState) { page ->
                FeaturedMealCard(meal = featuredMeals[page])
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(featuredMeals.size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color(0xFF4CAF50) else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Loại thức ăn & nước uống (${selectedMealTime.value})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                IconButton(onClick = { showMealTimeDialog.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Settings",
                        tint = Color.Unspecified
                    )
                }
            }

            if (showMealTimeDialog.value) {
                AlertDialog(
                    onDismissRequest = { showMealTimeDialog.value = false },
                    title = { Text("Chọn buổi ăn") },
                    text = {
                        Column {
                            listOf("Buổi sáng", "Buổi trưa", "Buổi chiều", "Buổi tối", "Cả ngày").forEach { time ->
                                Text(
                                    text = time,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedMealTime.value = time
                                            showMealTimeDialog.value = false
                                        }
                                        .padding(vertical = 8.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showMealTimeDialog.value = false }) {
                            Text("Đóng")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { category ->
                    FoodCategoryItem(
                        category = category,
                        isSelected = selectedCategory.value == category.name,
                        onClick = { selectedCategory.value = category.name }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = selectedCategory.value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val filteredMeals = allMeals.filter { meal ->
                    val matchCategory = when (selectedCategory.value) {
                        "Tất cả" -> true
                        "Sinh tố", "Nước uống" -> meal.category == "Drink"
                        "Rau củ" -> meal.name.contains("Salad", ignoreCase = true)
                        else -> true
                    }
                    val matchSearch = meal.name.contains(searchQuery.value, ignoreCase = true)
                    matchCategory && matchSearch
                }

                if (filteredMeals.isEmpty()) {
                    Text(
                        text = "Không tìm thấy món nào phù hợp 😢",
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredMeals) { meal ->
                            MealCard(meal = meal) {
                                // Navigate to detail screen on click
                                navController.navigate("mealdetail/${meal.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FeaturedMealCard(meal: Meal) {
    val gradientColors = when (meal.name) {
        "Khoai lang & Ức gà" -> listOf(Color(0xFFFF9800), Color(0xFFFFC107))
        "Salmon & Broccoli" -> listOf(Color(0xFF4CAF50), Color(0xFFA5D6A7))
        "Sinh tố Xoài" -> listOf(Color(0xFFFFC107), Color(0xFFFFE082))
        else -> listOf(Color(0xFF313131), Color(0xFF979797))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(180.dp)
            .background(
                brush = Brush.horizontalGradient(gradientColors),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = meal.description,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = meal.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Time",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = meal.time, fontSize = 12.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calories),
                        contentDescription = "Calories",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${meal.calories} kcal", fontSize = 12.sp, color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .padding(start = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = meal.imageRes),
                    contentDescription = meal.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun FoodCategoryItem(category: FoodCategory, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = if (isSelected) Color(0xFF4CAF50) else Color(0xFFF5F5F5),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFF4CAF50) else Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun MealCard(meal: Meal, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .height(240.dp)
            .clickable { onClick() }
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(14.dp))
            .background(Color.White, RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = meal.imageRes),
                contentDescription = meal.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = meal.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 2,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${meal.calories} kcal",
                fontSize = 13.sp,
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = "Time",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = meal.time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}