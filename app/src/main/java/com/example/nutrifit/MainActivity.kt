package com.example.nutrifit

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.nutrifit.ui.navigation.AppNavHost
import com.example.nutrifit.ui.theme.NutriFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Cho phép content vẽ dưới system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Đặt màu trong suốt cho status bar và navigation bar
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // Sử dụng WindowInsetsControllerCompat để thiết lập system bars
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Đảm bảo status bar icons có màu phù hợp
        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsetsController.isAppearanceLightNavigationBars = false

        setContent {
            NutriFitTheme {
                AppNavHost()
            }
        }
    }
}