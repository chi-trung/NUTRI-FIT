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
import com.example.nutrifit.ui.screens.schedule.ScheduleScreen
import com.example.nutrifit.ui.screens.workout.WorkoutScreen
import com.example.nutrifit.ui.screens.meal.MealDetailScreen
import com.example.nutrifit.ui.screens.setting.SettingScreen // dang làm setting


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
        NavRoutes.Setting,
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
            startDestination = NavRoutes.Onboarding, // Onboarding
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
                    onFirstLogin = {
                        navController.navigate(NavRoutes.Profile) {
                            popUpTo(NavRoutes.Login) { inclusive = true }
                        }
                    },
                    onGoRegister = { navController.navigate(NavRoutes.Register) },
                    onForgotPw = { navController.navigate(NavRoutes.ForgotPw) },
                    onEmailLogin = { navController.navigate(NavRoutes.Login2) }
                )
            }

            composable(NavRoutes.Login2) {
                LoginScreen2(
                    onLogin = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.Login2) { inclusive = true }
                        }
                    },
                    onFirstLogin = {
                        navController.navigate(NavRoutes.Profile) {
                            popUpTo(NavRoutes.Login2) { inclusive = true }
                        }
                    },
                    onGoRegister = { navController.navigate(NavRoutes.Register) },
                    onGoBack = { navController.popBackStack() },
                    onForgotPw = { navController.navigate(NavRoutes.ForgotPw) },

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
                    onNextClicked = {
                        navController.navigate(NavRoutes.Target)
                    }
                )
            }

            composable(NavRoutes.Setting) {
                SettingScreen(
                    onBackClick = { navController.popBackStack() },
                    onSaveChanges = { name, email, phone ->
                    },
                    navController = navController
                )
            }

            composable(NavRoutes.Target) {
                TargetScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNextClicked = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.Profile) { inclusive = true }
                        }
                    }
                )
            }
            // Bottom tabs - không có animation
            composable(NavRoutes.Home ) { HomeScreen(navController) }
            composable(NavRoutes.Meal) { MealScreen(navController) }
            composable(NavRoutes.Workout) { WorkoutScreen() }
            composable(NavRoutes.Map) { MapScreen() }
            composable("mealdetail/{mealId}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId")?.toIntOrNull() ?: 0
                MealDetailScreen(mealId = mealId, navController = navController)
            }

            composable(NavRoutes.Schedule) {
                ScheduleScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}