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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.runtime.collectAsState
import com.example.nutrifit.ui.screens.meal.MealCard
import com.example.nutrifit.viewmodel.HomeViewModel
import com.example.nutrifit.viewmodel.UserState
import com.example.nutrifit.viewmodel.DailyIntakeState
import com.example.nutrifit.viewmodel.MealsState
import androidx.compose.material3.CircularProgressIndicator


@Composable
fun HomeScreen(navController: NavController) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = viewModel()
    val userState by homeViewModel.userState.collectAsState()
    val dailyIntakeState by homeViewModel.dailyIntakeState.collectAsState()
    val suggestedMealsState by homeViewModel.suggestedMealsState.collectAsState()

    Column(
        modifier = Modifier
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
                // Header Section
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.home1),
                        contentDescription = "Header background",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color(0xFF1AC9AC), fontWeight = FontWeight.Bold)) { append("NUTRI") }
                                withStyle(style = SpanStyle(color = colorScheme.onBackground, fontWeight = FontWeight.Bold)) { append(" - ") }
                                withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) { append("FIT") }
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 37.sp,
                            modifier = Modifier.padding(top = 16.dp, start = 30.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Box(modifier = Modifier.padding(top = 10.dp, end = 30.dp).clickable { navController.navigate(NavRoutes.Profile) }) {
                            Image(
                                painter = painterResource(id = R.drawable.bgavt),
                                contentDescription = "Avatar background",
                                modifier = Modifier.size(63.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.avt),
                                contentDescription = "Avatar",
                                modifier = Modifier.size(63.dp)
                            )
                        }
                    }
                }
            }
            item{
                Spacer(modifier = Modifier.height(25.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    val greeting = when (val state = userState) {
                        is UserState.Success -> "Chào ${state.user.name}!"
                        else -> "Chào bạn!"
                    }
                    Text(
                        text = greeting,
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 25.dp)
                    )
                }
            }

            // Goal and Progress Section
            item{
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.fillMaxSize().padding(start = 17.dp, end = 17.dp)
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
                        Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                             Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.bieutuonglop3),
                                    contentDescription = "Goal icon",
                                    modifier = Modifier.size(60.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    val userGoalText = when (val state = userState) {
                                        is UserState.Success -> state.user.goal ?: "Chưa có mục tiêu"
                                        else -> "Chưa có mục tiêu"
                                    }
                                    Text(
                                        text = "Mục tiêu: $userGoalText",
                                        fontSize = 13.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(7.dp))

                                    val calorieGoalText = when (val state = userState) {
                                        is UserState.Success -> state.user.calorieGoal?.toString() ?: "..."
                                        else -> "..."
                                    }

                                    Text(
                                        text = "Mục tiêu: $calorieGoalText calo/ngày",
                                        fontSize = 11.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Button(
                                    onClick = { navController.navigate(NavRoutes.Schedule) },
                                    modifier = Modifier.height(50.dp).padding(start = 8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF293BB1))
                                ) {
                                    Text("xem lịch", fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }

                            val (caloriesConsumed, calorieGoal) = when {
                                userState is UserState.Success && dailyIntakeState is DailyIntakeState.Success -> {
                                    val user = (userState as UserState.Success).user
                                    val intake = (dailyIntakeState as DailyIntakeState.Success).intake
                                    (intake?.getTotalCalories() ?: 0) to (user.calorieGoal ?: 2000)
                                }
                                else -> 0 to 2000
                            }

                            val progress = if (calorieGoal > 0) (caloriesConsumed.toFloat() / calorieGoal.toFloat()).coerceIn(0f, 1f) else 0f
                            val progressPercentage = (progress * 100).toInt()

                            Spacer(modifier = Modifier.height(20.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(15.dp).background(Color(0xFFD9D9D9), shape = RoundedCornerShape(50))) {
                                Box(modifier = Modifier.fillMaxWidth(progress).fillMaxHeight().background(Color(0xFF4CAF50), shape = RoundedCornerShape(50)))
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Row() {
                                Text("Đã đạt: $caloriesConsumed", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.weight(1f))
                                Text("$progressPercentage%", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier .height(20.dp))
                            Text("Tiến trình hàng tuần", fontSize = 17.sp, color = Color.Black)

                            // Weekly progress chart (static for now)
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(180.dp).background(Color.White, RoundedCornerShape(20.dp)).padding(10.dp)) {
                                // Static chart content
                            }
                        }
                    }
                }
            }

            // Action buttons section
            item {
                Spacer(modifier = Modifier.height(7.dp))
                Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp) .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Box {
                        Image(painter = painterResource(id = R.drawable.khung4nut), contentDescription = "", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            ActionButton(navController = navController, route = NavRoutes.Meal, iconRes = R.drawable.thucdon, label = "Thực đơn", modifier = Modifier.weight(1f))
                            ActionButton(navController = navController, route = NavRoutes.Workout, iconRes = R.drawable.baitap, label = "Bài tập", modifier = Modifier.weight(1f))
                            ActionButton(navController = navController, route = NavRoutes.Scan, iconRes = R.drawable.kiemtra, label = "Kiểm tra", modifier = Modifier.weight(1f))
                            ActionButton(navController = navController, route = NavRoutes.Schedule, iconRes = R.drawable.lichtap, label = "Lịch tập", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // Meal Suggestions Section
            item{
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Đề xuất bữa ăn", color = Color.Black, fontSize = 20.sp, modifier = Modifier.align(Alignment.Start).padding(start = 25.dp))
                }
            }

            item {
                when(val state = suggestedMealsState) {
                    is MealsState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                    is MealsState.Error -> {
                        Text(state.message, modifier = Modifier.padding(16.dp), color = Color.Red)
                    }
                    is MealsState.Success -> {
                        if (state.meals.isEmpty()) {
                            Text("Không có đề xuất nào phù hợp.", modifier = Modifier.padding(16.dp))
                        } else {
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState())
                                                 .padding(horizontal = 16.dp)
                            ) {
                                state.meals.forEach { meal ->
                                    MealCard(meal = meal) {
                                        navController.navigate("${NavRoutes.MealDetail}/${meal.id}")
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                            }
                        }
                    }
                }
            }
             item {
                 Spacer(modifier = Modifier.height(100.dp))
             }
        }
    }
}

@Composable
fun ActionButton(navController: NavController, route: String, iconRes: Int, label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(6.dp).clickable { navController.navigate(route) },
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.khungnho4nut), contentDescription = "", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = iconRes), contentDescription = label, modifier = Modifier.size(45.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(label, fontSize = 11.sp, color = Color.Black)
        }
    }
}