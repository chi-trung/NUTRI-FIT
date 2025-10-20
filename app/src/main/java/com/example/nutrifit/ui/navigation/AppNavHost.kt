package com.example.nutrifit.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutrifit.ui.components.BottomNavBar
import com.example.nutrifit.ui.screens.home.HomeScreen
import com.example.nutrifit.ui.screens.login.LoginScreen
import com.example.nutrifit.ui.screens.map.MapScreen
import com.example.nutrifit.ui.screens.meal.MealScreen
import com.example.nutrifit.ui.screens.onboarding.OnboardingScreen
import com.example.nutrifit.ui.screens.profile.ProfileScreen
import com.example.nutrifit.ui.screens.workout.WorkoutScreen
import com.example.nutrifit.ui.screens.register.RegisterScreen
import com.example.nutrifit.ui.screens.forgotpw.ForgotPasswordScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = backStackEntry?.destination

    val showBottomBar = when (currentDestination?.route) {
        NavRoutes.Home, NavRoutes.Meal, NavRoutes.Workout, NavRoutes.Map, NavRoutes.Profile -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentDestination?.route,
                    onNavigate = { route ->
                        if (route != currentDestination?.route) {
                            navController.navigate(route) {
                                popUpTo(NavRoutes.Home) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Onboarding,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavRoutes.Onboarding) {
                OnboardingScreen(onStart = {
                    navController.navigate(NavRoutes.Login) {
                        popUpTo(NavRoutes.Onboarding) { inclusive = true }
                    }
                })
            }
            composable(NavRoutes.Login) {
                LoginScreen(
                    onLogin = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.Login) { inclusive = true }
                        }
                    },
                    onGoRegister = { navController.navigate(NavRoutes.Register) },
                    onForgotPw = { navController.navigate(NavRoutes.ForgotPw) }
                )
            }
            composable(NavRoutes.Register) {
                RegisterScreen(
                    onRegister = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.Register) { inclusive = true }
                        }
                    },
                    onBackToLogin = { navController.navigate(NavRoutes.Login) }
                )
            }
            composable(NavRoutes.ForgotPw) {
                ForgotPasswordScreen(
                    onBackToLogin = { navController.navigate(NavRoutes.Login) },
                    onSuccessReset = {
                        navController.navigate(NavRoutes.Login) {
                            popUpTo(NavRoutes.Login) { inclusive = true }
                        }
                    }
                )
            }
            composable(NavRoutes.Home) { HomeScreen() }
            composable(NavRoutes.Meal) { MealScreen() }
            composable(NavRoutes.Workout) { WorkoutScreen() }
            composable(NavRoutes.Map) { MapScreen() }
            composable(NavRoutes.Profile) { ProfileScreen() }
        }
    }
}
