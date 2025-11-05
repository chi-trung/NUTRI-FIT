package com.example.nutrifit.ui.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrifit.R
import com.example.nutrifit.viewmodel.AuthViewModel  // Thay bằng package thực tế của AuthViewModel

@Composable
fun LoginScreen(
    onLogin: () -> Unit,  // Navigate khi đăng nhập thành công
    onGoRegister: () -> Unit,
    onForgotPw: () -> Unit,
    onEmailLogin: () -> Unit = {}  // Chuyển sang LoginScreen2
) {
    val context = LocalContext.current
    val activity = context as Activity
    val viewModel: AuthViewModel = viewModel()

    // Khởi tạo Google Sign-In khi Composable load
    LaunchedEffect(Unit) {
        viewModel.initGoogleSignIn(context)
    }

    // Launcher để xử lý kết quả Google Sign-In
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.handleGoogleSignInResult(result.data)
        }
    }

    // Theo dõi trạng thái auth từ ViewModel
    val authState by viewModel.authState.collectAsState()

    // Xử lý trạng thái đăng nhập (không thay đổi UI, chỉ navigate hoặc hiển thị Toast)
    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                // Đăng nhập thành công: Navigate đến màn hình chính
                onLogin()
            }
            is AuthViewModel.AuthState.Error -> {
                // Hiển thị lỗi qua Toast (không thay đổi UI)
                Toast.makeText(context, (authState as AuthViewModel.AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}  // Loading hoặc Idle: Không làm gì
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image - giữ nguyên
        Image(
            painter = painterResource(R.drawable.loginbackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay - giữ nguyên
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.55f),
                            Color.Black.copy(alpha = 0.25f),
                            Color.Black.copy(alpha = 0.55f)
                        )
                    )
                )
        )

        // Nội dung chính - giữ nguyên
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo + Tiêu đề - giữ nguyên
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(96.dp)
            )

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "NUTRI",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1AC9AC)
                    )
                )
                Text(
                    text = " - ",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
                Text(
                    text = "FIT",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFF0004)
                    )
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Ăn uống lành mạnh, tập luyện thông minh",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // Nút GitHub - Chỉ thay đổi text và icon, giữ nguyên màu xanh Facebook
            Button(
                onClick = { viewModel.signInWithGitHub(activity) },  // Gọi GitHub login
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF262626),
                    contentColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.github),  // Thay icon thành GitHub
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Đăng nhập với GitHub",  // Thay text thành GitHub
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Nút Google - THAY ĐỔI LOGIC: Đăng nhập bằng Google
            Button(
                onClick = { googleLauncher.launch(viewModel.getGoogleSignInIntent()) },  // Gọi hàm public để lấy Intent
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Đăng nhập bằng Google",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Nút Email - Giữ nguyên: Chuyển sang LoginScreen2
            OutlinedButton(
                onClick = onEmailLogin,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    text = "Đăng nhập bằng Email",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onForgotPw) {
                Text("Quên mật khẩu?", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Chưa có tài khoản? ", color = Color.White)
                Text(
                    text = "Đăng ký ngay",
                    color = Color(0xFF66E0A3),
                    modifier = Modifier.clickable { onGoRegister() }
                )
            }
        }
    }
}