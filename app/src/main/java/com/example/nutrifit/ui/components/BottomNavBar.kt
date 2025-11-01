package com.example.nutrifit.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsGymnastics
import com.example.nutrifit.ui.navigation.NavRoutes

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    data object Home: BottomNavItem(NavRoutes.Home, "Home", Icons.Filled.Home)
    data object Meal: BottomNavItem(NavRoutes.Meal, "Meal", Icons.Filled.Restaurant)
    data object Workout: BottomNavItem(NavRoutes.Workout, "Workout", Icons.Filled.SportsGymnastics)
    data object Map: BottomNavItem(NavRoutes.Map, "Map", Icons.Filled.Map)
    data object Profile: BottomNavItem(NavRoutes.Profile, "Profile", Icons.Filled.Person)
}

@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Meal,
        BottomNavItem.Workout,
        BottomNavItem.Map,
        BottomNavItem.Profile
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
