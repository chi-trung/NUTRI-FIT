package com.example.nutrifit.ui.screens.forgotpw

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onBackToLogin: () -> Unit = {},
    onGoToResetPassword: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var isCodeSent by remember { mutableStateOf(false) }
    var isCodeValid by remember { mutableStateOf<Boolean?>(null) } // null = chưa check, true = đúng, false = sai
    var seconds by remember { mutableStateOf(0) }

    // Countdown timer
    LaunchedEffect(seconds) {
        if (seconds > 0) {
            delay(1000)
            seconds -= 1
        }
    }

    // Auto verify OTP when 5 digits entered
    LaunchedEffect(otpCode) {
        if (otpCode.length == 5) {
            delay(500) // Delay nhỏ để user thấy
            // Simulate verification (thay bằng API call thật)
            if (otpCode == "12345") { // Mock correct code
                isCodeValid = true
                delay(1000) // Show green state
                onGoToResetPassword()
            } else {
                isCodeValid = false
            }
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
            // Main card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(800.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo section
                    LogoSection()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Title
                    Text(
                        text = "Quên mật khẩu",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description
                    Text(
                        text = "Nhập email bạn đã dùng để đăng ký tài khoản",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email input với nút gửi mã bên trong
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Nhập địa chỉ email ") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        trailingIcon = {
                            Button(
                                onClick = {
                                    if (email.isNotBlank() && !isCodeSent) {
                                        isCodeSent = true
                                        seconds = 60
                                    }
                                },
                                enabled = email.isNotBlank() && !isCodeSent,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCodeSent) Color(0xFF7C7F84) else Color(0xFF56B141),
                                    disabledContainerColor = Color(0xFF56B141)
                                ),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .height(36.dp)
                            ) {
                                Text(
                                    text = if (isCodeSent) "Đã gửi" else "Gửi mã",
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // OTP Section - luôn hiển thị
                    Text(
                        text = "Nhập mã xác thực ( gồm 5 chữ số )",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // OTP Input Boxes
                    OTPInputBoxes(
                        otpCode = otpCode,
                        onOtpChange = {
                            otpCode = it
                            if (it.length < 5) {
                                isCodeValid = null // Reset validation state
                            }
                        },
                        isValid = isCodeValid
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Error message và resend button
                    if (isCodeValid == false) {
                        Text(
                            text = "Mã xác thực sai hoặc không hợp lệ",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color(0xFFE53935)
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Gửi lại mã",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color(0xFF2196F3),
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.clickable {
                                otpCode = ""
                                isCodeValid = null
                                seconds = 60
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Back to login
                    Text(
                        text = "Thử cách đăng nhập khác | Về đăng nhập",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier.clickable { onBackToLogin() }
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Logo từ drawable
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "NutriFit Logo",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // App name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "NUTRI",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1AC9AC)
                )
            )
            Text(
                text = " - ",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Text(
                text = "FIT",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF0004)
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Subtitle
        Text(
            text = "Lấy lại mật khẩu",
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray
            )
        )
    }
}

@Composable
private fun OTPInputBoxes(
    otpCode: String,
    onOtpChange: (String) -> Unit,
    isValid: Boolean?
) {
    val borderColor = when (isValid) {
        true -> Color(0xFF4CAF50)  // Xanh lá khi đúng
        false -> Color(0xFFE53935) // Đỏ khi sai
        null -> Color.Gray         // Xám khi chưa validate
    }

    val focusRequesters = remember { List(5) { FocusRequester() } }

    // OTP Boxes với auto focus
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(5) { index ->
            OutlinedTextField(
                value = if (index < otpCode.length) otpCode[index].toString() else "",
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                        val newOtpCode = otpCode.toMutableList()

                        // Đảm bảo list có đủ 5 phần tử
                        while (newOtpCode.size < 5) {
                            newOtpCode.add(' ')
                        }

                        if (newValue.isEmpty()) {
                            // Xóa ký tự tại vị trí hiện tại
                            if (index < newOtpCode.size) {
                                newOtpCode[index] = ' '
                            }
                            // Focus về ô trước đó nếu có
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        } else {
                            // Thêm ký tự mới
                            newOtpCode[index] = newValue[0]
                            // Focus sang ô tiếp theo nếu có
                            if (index < 4) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }

                        // Tạo string mới và loại bỏ khoảng trắng
                        val finalCode = newOtpCode.joinToString("").replace(" ", "")
                        onOtpChange(finalCode)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequesters[index]),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = if (index < otpCode.length) borderColor else Color.Gray,
                    unfocusedIndicatorColor = if (index < otpCode.length) borderColor else Color.Gray
                )
            )
        }
    }

    // Auto focus vào ô đầu tiên khi component được tạo
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}


