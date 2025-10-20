package com.example.nutrifit.ui.screens.forgotpw

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onBackToLogin: () -> Unit = {},
    onSuccessReset: () -> Unit = {}
) {
    // Steps: 1 = input email, 2 = verify code, 3 = reset password
    var step by remember { mutableStateOf(1) }

    // States
    val email = remember { mutableStateOf("") }
    val code = remember { mutableStateOf("") } // 6 digits
    val newPass = remember { mutableStateOf("") }
    val confirmPass = remember { mutableStateOf("") }

    // Resend countdown
    var seconds by remember { mutableStateOf(0) }
    LaunchedEffect(step, seconds) {
        if (step == 2 && seconds > 0) {
            delay(1000)
            seconds -= 1
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background image with scrim
        Image(
            painter = painterResource(R.drawable.rectangle_59),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LogoHeader()
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = when (step) {
                            1 -> "Quên mật khẩu"
                            2 -> "Nhập mã xác thực"
                            else -> "Đổi mật khẩu"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(16.dp))

                    when (step) {
                        1 -> StepEnterEmail(email = email) { // send code
                            seconds = 60
                            step = 2
                        }
                        2 -> StepVerifyCode(
                            email = email.value,
                            code = code,
                            seconds = seconds,
                            onResend = {
                                if (seconds == 0) seconds = 60
                            },
                            onConfirm = { if (code.value.length == 6) step = 3 }
                        )
                        else -> StepResetPassword(
                            newPass = newPass,
                            confirmPass = confirmPass,
                            onConfirm = {
                                // Basic validation
                                if (newPass.value.isNotBlank() && newPass.value == confirmPass.value) {
                                    onSuccessReset()
                                }
                            }
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    AnimatedVisibility(visible = step != 3) {
                        Text(
                            text = "Không nhận được mã? Gửi lại",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable(enabled = step == 2 && seconds == 0) {
                                if (step == 2 && seconds == 0) seconds = 60
                            }
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Thử cách đăng nhập khác | Về đăng nhập",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable { onBackToLogin() },
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.FitnessCenter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "NUTRI",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = " - ",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "FIT",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE53935))
            )
        }
        Text(
            text = "Lấy lại mật khẩu",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alpha(0.8f)
        )
    }
}

@Composable
private fun StepEnterEmail(
    email: MutableState<String>,
    onSendCode: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Nhập email bạn đã dùng để đăng ký",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alpha(0.8f)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Địa chỉ email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onSendCode,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) { Text("Gửi mã") }
    }
}

@Composable
private fun StepVerifyCode(
    email: String,
    code: MutableState<String>,
    seconds: Int,
    onResend: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Mã xác thực đã được gửi đến: $email",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alpha(0.8f)
        )
        Spacer(Modifier.height(12.dp))
        OtpInput(code = code, length = 6)
        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = if (seconds > 0) "Gửi lại sau ${seconds}s" else "Gửi lại",
                color = if (seconds > 0) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(enabled = seconds == 0) { onResend() }
            )
            Button(
                onClick = onConfirm,
                enabled = code.value.length == 6,
                shape = RoundedCornerShape(10.dp)
            ) { Text("Xác nhận") }
        }
    }
}

@Composable
private fun StepResetPassword(
    newPass: MutableState<String>,
    confirmPass: MutableState<String>,
    onConfirm: () -> Unit
) {
    var show1 by remember { mutableStateOf(false) }
    var show2 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Đổi mật khẩu mới cho tài khoản của bạn",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alpha(0.8f)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = newPass.value,
            onValueChange = { newPass.value = it },
            label = { Text("Nhập mật khẩu mới") },
            singleLine = true,
            visualTransformation = if (show1) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    imageVector = if (show1) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier.clickable { show1 = !show1 }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = confirmPass.value,
            onValueChange = { confirmPass.value = it },
            label = { Text("Nhập lại mật khẩu") },
            singleLine = true,
            visualTransformation = if (show2) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    imageVector = if (show2) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier.clickable { show2 = !show2 }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onConfirm,
            enabled = newPass.value.isNotBlank() && newPass.value == confirmPass.value,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) { Text("Xác nhận") }
    }
}

@Composable
private fun OtpInput(
    code: MutableState<String>,
    length: Int = 6
) {
    // UI hiển thị 6 ô nhập, nhưng state lưu chuỗi
    val cells = (0 until length).map { idx ->
        if (idx < code.value.length) code.value[idx].toString() else ""
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        cells.forEachIndexed { index, ch ->
            OutlinedTextField(
                value = ch,
                onValueChange = { input ->
                    if (input.isNotEmpty()) {
                        val c = input.last()
                        if (c.isDigit()) {
                            val current = code.value
                            val new = StringBuilder(current)
                            if (index < current.length) {
                                new.setCharAt(index, c)
                            } else if (current.length < length) {
                                new.append(c)
                            }
                            code.value = new.toString().take(length)
                        }
                    } else {
                        // xóa ký tự tại ô hiện tại
                        val current = code.value
                        if (index < current.length) {
                            code.value = (current.substring(0, index) + current.substring(index + 1))
                        }
                    }
                },
                textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    }
}
