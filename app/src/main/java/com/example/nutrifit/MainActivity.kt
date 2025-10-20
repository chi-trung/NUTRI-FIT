package com.example.nutrifit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nutrifit.ui.navigation.AppNavHost
import com.example.nutrifit.ui.theme.NutriFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NutriFitTheme {
                AppNavHost()
            }
        }
    }
}
