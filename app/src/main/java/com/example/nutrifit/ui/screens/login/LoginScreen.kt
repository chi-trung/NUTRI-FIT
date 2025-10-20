package com.example.nutrifit.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Đăng nhập")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = pass.value, onValueChange = { pass.value = it }, label = { Text("Mật khẩu") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogin) { Text("Login") }
    }
}
