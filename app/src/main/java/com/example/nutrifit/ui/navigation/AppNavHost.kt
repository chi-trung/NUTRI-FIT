package com.example.nutrifit.ui.navigation

import androidx.compose.foundation.layout.Box
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
import com.example.nutrifit.data.model.Workout
import com.example.nutrifit.ui.components.BottomNavBar
import com.example.nutrifit.ui.screens.dailylog.DailyLogScreen
import com.example.nutrifit.ui.screens.forgotpw.ForgotPasswordScreen
import com.example.nutrifit.ui.screens.forgotpw.ForgotPasswordScreen2
import com.example.nutrifit.ui.screens.home.HomeScreen
import com.example.nutrifit.ui.screens.login.LoginScreen
import com.example.nutrifit.ui.screens.login.LoginScreen2
import com.example.nutrifit.ui.screens.map.MapScreen
import com.example.nutrifit.ui.screens.meal.MealDetailScreen
import com.example.nutrifit.ui.screens.meal.MealScreen
import com.example.nutrifit.ui.screens.onboarding.OnboardingScreen
import com.example.nutrifit.ui.screens.profile.ProfileScreen
import com.example.nutrifit.ui.screens.register.RegisterScreen
import com.example.nutrifit.ui.screens.schedule.ScheduleScreen
import com.example.nutrifit.ui.screens.setting.SettingScreen // dang làm setting
import com.example.nutrifit.ui.screens.target.TargetScreen
import com.example.nutrifit.ui.screens.workout.WorkoutDetailScreen
import com.example.nutrifit.ui.screens.workout.WorkoutScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = backStackEntry?.destination

    // Các route hiển thị BottomBar
    val bottomBarRoutes = setOf(
        NavRoutes.Home,
        NavRoutes.Meal,
        NavRoutes.DailyLog, // Add DailyLog to show bottom bar
        NavRoutes.Workout,
        NavRoutes.Map,
        // NavRoutes.Profile // Setting cũng không nên có bottom bar
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
            startDestination = NavRoutes.Workout, // *** SỬA LỖI: Đặt Workout làm màn hình bắt đầu ***
            modifier = Modifier // Xóa padding ở đây để cho phép màn hình con kiểm soát
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

            // Các màn hình có BottomNavBar sẽ được bọc trong Box với padding
            composable(NavRoutes.Home) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    HomeScreen(navController)
                }
            }
            composable(NavRoutes.Meal) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    MealScreen(navController)
                }
            }
            composable(NavRoutes.DailyLog) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    DailyLogScreen(navController)
                }
            }
            composable(NavRoutes.Workout) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    WorkoutScreen(navController)
                }
            }
            composable(NavRoutes.Map) {
                Box(modifier = Modifier.padding(paddingValues)) {
                    MapScreen()
                }
            }

            composable("${NavRoutes.MealDetail}/{mealId}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId")?.toIntOrNull() ?: 0
                MealDetailScreen(mealId = mealId, navController = navController)
            }

            composable(NavRoutes.WORKOUT_DETAIL) {
                val workout = navController.previousBackStackEntry?.savedStateHandle?.get<Workout>("workout")
                WorkoutDetailScreen(workout = workout, navController = navController)
            }

            composable(NavRoutes.Schedule) {
                ScheduleScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}