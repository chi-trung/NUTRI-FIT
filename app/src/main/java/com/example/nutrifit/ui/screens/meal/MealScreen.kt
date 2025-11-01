package com.example.nutrifit.ui.screens.meal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutrifit.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars

data class Meal(
    val id: Int,
    val name: String,
    val description: String,
    val imageRes: Int,
    val calories: String,
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
    val featuredMeals = listOf(
        Meal(1, "Khoai lang & Ức gà", "Món ăn được thích nhiều nhất", R.drawable.klug, "350 kcal", "15 phút", "Main"),
        Meal(2, "Salad Cá Hồi", "Món ăn phù hợp với bạn", R.drawable.stbo, "420 kcal", "20 phút", "Main"),
        Meal(3, "Sinh tố Xoài", "Món uống bổ dưỡng", R.drawable.stxoai, "180 kcal", "5 phút", "Drink")
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
        Meal(1, "Khoai lang & Ức gà", "Bữa ăn giàu protein", R.drawable.klug, "350 kcal", "15 phút", "Main"),
        Meal(2, "Salmon & Broccoli", "Omega-3 và chất xơ", R.drawable.stbo, "420 kcal", "20 phút", "Main"),
        Meal(3, "Salad đậu", "Protein thực vật", R.drawable.stdau, "280 kcal", "10 phút", "Main"),
        Meal(4, "Táo & Hạnh nhân", "Ăn nhẹ lành mạnh", R.drawable.sttao, "200 kcal", "5 phút", "Snack"),
        Meal(5, "Sinh tố Xoài", "Vitamin và khoáng chất", R.drawable.stxoai, "180 kcal", "5 phút", "Drink"),
        Meal(6, "Sinh tố Dâu", "Chất chống oxy hóa", R.drawable.stdau, "150 kcal", "5 phút", "Drink"),
        Meal(7, "Sinh tố Chuối", "Năng lượng tự nhiên", R.drawable.sttao, "200 kcal", "5 phút", "Drink")
    )

    val selectedCategory = remember { mutableStateOf("Tất cả") }
    val pagerState = rememberPagerState(pageCount = { featuredMeals.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Đổi background sang màu sáng
            .padding(WindowInsets.statusBars.asPaddingValues())

    ) {
        // Header
        Text(
            text = "Những món ăn hấp dẫn",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black // Đổi màu chữ sang đen
        )

        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(25.dp)) // Đổi màu search bar
                .clickable { /* Handle search click */ },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tìm kiếm món ăn của bạn",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Featured Meals Pager
        HorizontalPager(state = pagerState) { page ->
            FeaturedMealCard(meal = featuredMeals[page])
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pager Indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(featuredMeals.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFF4CAF50) else Color.LightGray
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

        // Food Categories Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Loại thức ăn & nước uống",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black // Đổi màu chữ sang đen
            )

            IconButton(
                onClick = { /* Handle settings */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    tint = Color.Black // Đổi màu icon sang đen
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Food Categories
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

        // Meals Section based on selected category
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

            // Filter meals based on selected category
            val filteredMeals = when (selectedCategory.value) {
                "Tất cả" -> allMeals
                "Sinh tố" -> allMeals.filter { it.category == "Drink" }
                "Nước uống" -> allMeals.filter { it.category == "Drink" }
                "Rau củ" -> allMeals.filter { it.name.contains("Salad", ignoreCase = true) }
                else -> allMeals
            }

            // Meals Grid
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredMeals) { meal ->
                    MealCard(meal = meal) {
                        navController.navigate("mealdetail/${meal.id}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FeaturedMealCard(meal: Meal) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(180.dp) // Giảm chiều cao để hình tròn nằm ngang
    ) {
        // Background gradient (giữ nguyên gradient trắng đen)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF313131),
                            Color(0xFF979797)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )

        // Content và hình ảnh nằm ngang
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Món ăn được thích nhiều nhất",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = meal.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Time",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meal.time,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_calories),
                        contentDescription = "Calories",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meal.calories,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Circular image - to hơn và nằm ngang
            Box(
                modifier = Modifier
                    .size(140.dp) // Tăng kích thước hình tròn
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
                    color = if (isSelected) Color(0xFF4CAF50) else Color(0xFFF5F5F5), // Đổi màu nền sang sáng
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                tint = if (isSelected) Color.White else Color.Black, // Đổi màu icon
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFF4CAF50) else Color.Black, // Đổi màu chữ
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun MealCard(meal: Meal, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0), // Đổi màu border sang sáng hơn
                shape = RoundedCornerShape(12.dp)
            )
            .padding(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(12.dp)) // Đổi nền sang trắng
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = meal.imageRes),
                contentDescription = meal.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = meal.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black, // Đổi màu chữ sang đen
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = meal.calories,
                fontSize = 12.sp,
                color = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = "Time",
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = meal.time,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}