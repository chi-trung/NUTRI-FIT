package com.example.nutrifit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.ui.navigation.NavRoutes

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    data object Home : BottomNavItem(NavRoutes.Home, "Trang chủ", Icons.Filled.Home)
    data object Meal : BottomNavItem(NavRoutes.Meal, "Thực đơn", Icons.Filled.Restaurant)
    data object Scan : BottomNavItem(NavRoutes.Scan, "Quét mã", Icons.Filled.QrCodeScanner)
    data object Workout : BottomNavItem(NavRoutes.Workout, "Bài tập", Icons.Filled.FitnessCenter)
    data object Profile : BottomNavItem(NavRoutes.Profile, "Hồ sơ", Icons.Filled.Person)
}

@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 40.dp, vertical = 10.dp),
        color = Color(0xFF101010),
        shape = RoundedCornerShape(40.dp),
        shadowElevation = 10.dp
    ) {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Meal,
            BottomNavItem.Scan,
            BottomNavItem.Workout,
            BottomNavItem.Profile
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route

                if (isSelected) {
                    Row(
                        modifier = Modifier
                            .height(46.dp)
                            .background(Color(0xFF1E88E5), RoundedCornerShape(40.dp))
                            .padding(horizontal = 12.dp)
                            .clickable { onNavigate(item.route) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.label,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color.White, CircleShape)
                            .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                            .clickable { onNavigate(item.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = Color(0xFF333333),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
