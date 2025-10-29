package com.example.nutrifit.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutrifit.ui.components.BottomNavBar
import com.example.nutrifit.ui.screens.forgotpw.ForgotPasswordScreen
import com.example.nutrifit.ui.screens.forgotpw.ForgotPasswordScreen2
import com.example.nutrifit.ui.screens.home.HomeScreen
import com.example.nutrifit.ui.screens.login.LoginScreen
import com.example.nutrifit.ui.screens.login.LoginScreen2
import com.example.nutrifit.ui.screens.map.MapScreen
import com.example.nutrifit.ui.screens.meal.MealScreen
import com.example.nutrifit.ui.screens.onboarding.OnboardingScreen
import com.example.nutrifit.ui.screens.profile.ProfileScreen
import com.example.nutrifit.ui.screens.register.RegisterScreen
import com.example.nutrifit.ui.screens.target.TargetScreen
import com.example.nutrifit.ui.screens.workout.WorkoutScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = backStackEntry?.destination

    // Các route hiển thị BottomBar (giữ nguyên nếu NavRoutes.* là String)
    val bottomBarRoutes = setOf(
        NavRoutes.Home,
        NavRoutes.Meal,
        NavRoutes.Workout,
        NavRoutes.Map,
        // NavRoutes.Profile
    )
    val showBottomBar = currentDestination?.route in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentDestination?.route,
                    onNavigate = { route ->
                        if (route != currentDestination?.route) {
                            navController.navigate(route) {
                                // popUpTo bằng route của startDestination nếu có, fallback về Home route
                                val startRoute = navController.graph.findStartDestination().route ?: NavRoutes.Home
                                popUpTo(startRoute) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Profile,
            modifier = if (showBottomBar) Modifier.padding(paddingValues) else Modifier
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
                    onForgotPw = { navController.navigate(NavRoutes.ForgotPw) },
                    onEmailLogin = { navController.navigate(NavRoutes.Login2) } // THÊM NAVIGATION ĐẾN LOGIN2
                )
            }

            composable(NavRoutes.Login2) {
                LoginScreen2(
                    onLogin = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.Login2) { inclusive = true }
                        }
                    },
                    onGoRegister = { navController.navigate(NavRoutes.Register) },
                    onForgotPw = { navController.navigate(NavRoutes.ForgotPw) }
                )
            }

            composable(NavRoutes.Register) {
                RegisterScreen(
                        onRegister = {
                        navController.navigate(NavRoutes.Profile) {
                            popUpTo(NavRoutes.Register) { inclusive = true }
                        }
                    },
                    onBackToLogin = {
                        // CHUYỂN VỀ LOGIN2 THAY VÌ LOGIN
                        navController.navigate(NavRoutes.Login2) {
                            popUpTo(NavRoutes.Login2) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.ForgotPw) {
                ForgotPasswordScreen(
                    onBackToLogin = {
                        navController.navigate(NavRoutes.Login) {
                            popUpTo(NavRoutes.ForgotPw) { inclusive = true }
                        }
                    },
                    onGoToResetPassword = {
                        navController.navigate(NavRoutes.ForgotPw2)
                    }
                )
            }

            composable(NavRoutes.ForgotPw2) {
                ForgotPasswordScreen2(
                    onBackToLogin = {
                        navController.navigate(NavRoutes.Login) {
                            popUpTo(NavRoutes.Login) { inclusive = true }
                        }
                    },
                    onSuccessReset = {
                        navController.navigate(NavRoutes.Login) {
                            popUpTo(NavRoutes.Login) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.Profile) {
                ProfileScreen(

                )
            }


            composable(NavRoutes.Target) {
                TargetScreen(
                    onNextClicked = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.Profile) { inclusive = true }
                        }
                    }
                )
            }
            // Bottom tabs - không có animation
            composable(NavRoutes.Home) { HomeScreen() }
            composable(NavRoutes.Meal) { MealScreen() }
            composable(NavRoutes.Workout) { WorkoutScreen() }
            composable(NavRoutes.Map) { MapScreen() }
        }
    }
}
