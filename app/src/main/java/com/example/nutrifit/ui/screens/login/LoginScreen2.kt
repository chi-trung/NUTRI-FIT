package com.example.nutrifit.ui.screens.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.layout.ContentScale
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Định nghĩa các màu sắc
private val NutriColor = Color(0xFF1AC9AC)
private val CornerRadius = 16.dp
private val GoogleButtonColor = Color(0xFF4285F4) // Màu Google blue
private val FacebookButtonColor = Color(0xFF1877F2) // Màu Facebook blue

@Composable
fun LoginScreen2(
    onLogin: () -> Unit,
    onGoRegister: () -> Unit,
    onForgotPw: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.loginbackground),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // White box content - NẰM Ở GIỮA MÀN HÌNH với opacity 90%
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // ĐỔI VỀ WRAP CONTENT HEIGHT
                .align(Alignment.Center)
                .padding(horizontal = 20.dp) // KHOẢNG CÁCH TRÁI PHẢI
        ) {
            // White box với opacity 90%
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() // ĐỔI VỀ WRAP CONTENT HEIGHT
                    .clip(RoundedCornerShape(CornerRadius))
                    .background(Color.White.copy(alpha = 0.9f)) // OPACITY 90%
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header với nút back và chữ Đăng ký
                    HeaderSection2(onGoRegister = onGoRegister)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logo và tiêu đề
                    LogoSection2()

                    Spacer(modifier = Modifier.height(20.dp))

                    // Form đăng nhập
                    LoginForm2(
                        email = email,
                        onEmailChange = { email = it },
                        password = password,
                        onPasswordChange = { password = it },
                        rememberMe = rememberMe,
                        onRememberMeChange = { rememberMe = it },
                        focusManager = focusManager,
                        onLogin = onLogin,
                        onForgotPw = onForgotPw
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Đăng nhập với mạng xã hội
                    SocialLoginSection2()
                }
            }
        }
    }
}

@Composable
fun HeaderSection2(onGoRegister: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                    onGoRegister()
                    // Reset animation
                    kotlinx.coroutines.GlobalScope.launch {
                        kotlinx.coroutines.delay(100)
                        isBackPressed = false
                    }
                },
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Đăng ký",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun LogoSection2() {
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
            text = "Đăng nhập để truy cập chương trình cá nhân hóa cho bạn.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 18.sp
        )


    }
}

@Composable
fun LoginForm2(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    focusManager: FocusManager,
    onLogin: () -> Unit,
    onForgotPw: () -> Unit
) {
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

        CustomTextField2(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Nhập Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mật khẩu
        Text(
            text = "Mật khẩu",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        PasswordTextField2(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "••••••••••••••••••••",
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Remember me và Quên mật khẩu
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
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

            // Quên mật khẩu với animation
            var isPressed by remember { mutableStateOf(false) }
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 0.95f else 1f,
                animationSpec = tween(100),
                label = "forgot_password_scale"
            )

            Text(
                text = "Quên mật khẩu?",
                fontSize = 14.sp,
                color = NutriColor,
                modifier = Modifier
                    .scale(scale)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = false,
                            radius = 24.dp,
                            color = NutriColor
                        )
                    ) {
                        onForgotPw()
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Đăng nhập button với animation
        var isLoginPressed by remember { mutableStateOf(false) }
        val loginScale by animateFloatAsState(
            targetValue = if (isLoginPressed) 0.98f else 1f,
            animationSpec = tween(100),
            label = "login_button_scale"
        )

        Button(
            onClick = {
                isLoginPressed = true
                onLogin()
                // Reset animation sau một chút
                kotlinx.coroutines.GlobalScope.launch {
                    kotlinx.coroutines.delay(100)
                    isLoginPressed = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .scale(loginScale),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NutriColor
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 2.dp
            )
        ) {
            Text(
                text = "Đăng nhập",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SocialLoginSection2() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SocialLoginButton2(
            icon = R.drawable.google,
            text = "Đăng nhập với Google",
            buttonColor = GoogleButtonColor,
            onClick = { /* Handle Google login */ }
        )

        SocialLoginButton2(
            icon = R.drawable.facebook,
            text = "Đăng nhập với Facebook",
            buttonColor = FacebookButtonColor,
            onClick = { /* Handle Facebook login */ }
        )
    }
}

@Composable
fun SocialLoginButton2(
    icon: Int,
    text: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "social_button_scale"
    )

    Button(
        onClick = {
            isPressed = true
            onClick()
            // Reset animation sau một chút
            kotlinx.coroutines.GlobalScope.launch {
                kotlinx.coroutines.delay(100)
                isPressed = false
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 3.dp,
            pressedElevation = 1.dp
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
                color = Color.White
            )
        }
    }
}

@Composable
fun CustomTextField2(
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
fun PasswordTextField2(
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