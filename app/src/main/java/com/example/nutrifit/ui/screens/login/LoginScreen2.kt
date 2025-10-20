package com.example.nutrifit.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.nutrifit.R

@Composable
fun LoginScreen2(
    onLogin: () -> Unit,
    onGoRegister: () -> Unit,
    onForgotPw: () -> Unit
) {
    // Test với background đơn giản
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image đơn giản
        Image(
            painter = painterResource(R.drawable.loginbackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Text test ở giữa
        Text(
            text = "TEST BACKGROUND",
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    }
}