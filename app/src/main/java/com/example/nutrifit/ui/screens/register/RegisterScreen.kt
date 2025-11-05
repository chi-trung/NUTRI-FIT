package com.example.nutrifit.ui.screens.register

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ripple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.widget.Toast

// Định nghĩa các màu sắc
private val NutriColor = Color(0xFF1AC9AC)
private val BackgroundColor = Color(0xFFF5F5F5)
private val CornerRadius = 16.dp
private val GoogleButtonColor = Color(0xFF4285F4) // Màu Google blue
private val FacebookButtonColor = Color(0xFF1877F2) // Màu Facebook blue
private val GitHubButtonColor = Color(0xFF000000) // Màu GitHub (đen)

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.loginbackground),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        // White box content - NẰM Ở GIỮA MÀN HÌNH với opacity 90%
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // ĐỔI VỀ WRAP CONTENT HEIGHT
                .align(Alignment.Center)
                .padding(horizontal = 20.dp) // THÊM KHOẢNG CÁCH TRÁI PHẢI
        ) {
            // White box với opacity 90%
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() // ĐỔI VỀ WRAP CONTENT HEIGHT
                    .clip(RoundedCornerShape(CornerRadius))
                    .background(Color.White.copy(alpha = 0.9f)) // OPACITY 90%
                    .padding(16.dp) // TĂNG PADDING TRONG ĐỂ CÂN ĐỐI HỚN
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp), // GIẢM PADDING VÌ ĐÃ CÓ PADDING Ở BOX NGOÀI
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header với nút back và chữ Đăng nhập
                    HeaderSection(onBackToLogin = onBackToLogin)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logo và tiêu đề
                    LogoSection()

                    Spacer(modifier = Modifier.height(20.dp))

                    // Form đăng ký
                    RegisterForm(
                        phoneNumber = phoneNumber,
                        onPhoneNumberChange = { phoneNumber = it },
                        email = email,
                        onEmailChange = { email = it },
                        password = password,
                        onPasswordChange = { password = it },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        otpCode = otpCode,
                        onOtpCodeChange = { otpCode = it },
                        rememberMe = rememberMe,
                        onRememberMeChange = { rememberMe = it },
                        focusManager = focusManager,
                        onRegister = onRegister,
                        onSendOtp = { /* Xử lý gửi OTP - giữ nguyên, không hoạt động */ }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Đăng nhập với mạng xã hội - DẠNG COLUMN
                    SocialLoginSection()
                }
            }
        }
    }
}

@Composable
fun HeaderSection(onBackToLogin: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nút back với animation
        var isBackPressed by remember { mutableStateOf(false) }
        val backScale by animateFloatAsState(
            targetValue = if (isBackPressed) 0.9f else 1f,
            animationSpec = tween(100),
            label = "back_button_scale"
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .scale(backScale)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(
                        bounded = false,
                        radius = 20.dp,
                        color = Color.Gray
                    )
                ) {
                    isBackPressed = true
                    onBackToLogin()
                    // Reset animation
                    GlobalScope.launch {
                        delay(100)
                        isBackPressed = false
                    }
                },
            tint = Color.Black // Màu đen
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Đăng nhập",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun LogoSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "NUTRI-FIT Logo",
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = NutriColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ) {
                    append("NUTRI")
                }
                append(" - ")
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFFF0004),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ) {
                    append("FIT")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Đăng ký để bắt đầu hành trình tập luyện",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 18.sp
        )

        Text(
            text = "và ăn uống khoa học",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun RegisterForm(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    otpCode: String,
    onOtpCodeChange: (String) -> Unit,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    focusManager: FocusManager,
    onRegister: () -> Unit,
    onSendOtp: () -> Unit
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Email
        Text(
            text = "Email",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Nhập email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mã OTP
        Text(
            text = "Mã OTP",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomTextField(
                value = otpCode,
                onValueChange = onOtpCodeChange,
                placeholder = "Nhập mã OTP",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                focusManager = focusManager,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onSendOtp,
                modifier = Modifier
                    .width(100.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NutriColor
                )
            ) {
                Text(
                    text = "Gửi",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mật khẩu
        Text(
            text = "Mật khẩu",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Nhập mật khẩu",
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Xác nhận mật khẩu
        Text(
            text = "Xác nhận mật khẩu",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "Nhập lại mật khẩu",
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Remember me
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = onRememberMeChange,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Lưu mật khẩu",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Đăng ký button
        Button(
            onClick = {
                // Validation
                if (email.isBlank()) {
                    Toast.makeText(context, "Email không được bỏ trống", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (!email.contains("@")) {
                    Toast.makeText(context, "Email không hợp lệ (phải chứa @)", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (password.length < 8) {
                    Toast.makeText(context, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (password != confirmPassword) {
                    Toast.makeText(context, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // Đăng ký Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Thành công
                            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                            onRegister()
                        } else {
                            // Lỗi (ví dụ: email đã tồn tại)
                            Toast.makeText(context, "Đăng ký thất bại: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NutriColor
            )
        ) {
            Text(
                text = "Đăng ký",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SocialLoginSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ĐÃ XÓA DÒNG "Hoặc đăng nhập với"

        SocialLoginButton(
            icon = R.drawable.google,
            text = "Đăng nhập với Google",
            buttonColor = GoogleButtonColor,
            onClick = { /* Handle Google login */ }
        )

        SocialLoginButton(
            icon = R.drawable.github,
            text = "Đăng nhập với GitHub",
            buttonColor = GitHubButtonColor,
            onClick = { /* Handle GitHub login */ }
        )
    }
}

@Composable
fun SocialLoginButton(
    icon: Int,
    text: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor // MÀU FILL CHO NÚT
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$text Login",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White // CHỮ MÀU TRẮNG
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusManager: FocusManager,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth() // ĐẢM BẢO CHIẾU RỘNG ĐẦY ĐỦ TRONG KHÔNG GIAN CÓ SẴN
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.Transparent) // NO FILL - TRONG SUỐT
            .padding(horizontal = 12.dp),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusManager: FocusManager,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.Transparent)
            .padding(horizontal = 12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }

                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { passwordVisible = !passwordVisible },
                    tint = Color.Gray
                )
            }
        }
    )
}
