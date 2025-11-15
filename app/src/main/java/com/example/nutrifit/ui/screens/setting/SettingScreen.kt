package com.example.nutrifit.ui.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.BorderStroke
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nutrifit.ui.navigation.NavRoutes
import com.example.nutrifit.viewmodel.SettingUiState
import com.example.nutrifit.viewmodel.SettingViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth // ✅ THÊM IMPORT NÀY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBackClick: () -> Unit = {},
    navController: NavController? = null,
    viewModel: SettingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (val state = uiState) {
        is SettingUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is SettingUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.message}")
            }
        }
        is SettingUiState.Success -> {
            val user = state.user
            val providers = state.providers
            var name by remember { mutableStateOf(user.name) }
            var email by remember { mutableStateOf(user.email) }
            val height by remember { mutableStateOf(user.height ?: "") }
            val weight by remember { mutableStateOf(user.weight ?: "") }
            val goal by remember { mutableStateOf(user.goal ?: "") }
            var twoFactorAuth by remember { mutableStateOf(false) }
            var notificationsEnabled by remember { mutableStateOf(false) }

            Scaffold(
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF5F5F5))
                            .padding(paddingValues)
                    ) {
                        // Top Banner / Header
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .padding(16.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    clip = true
                                ),
                            color = Color(0xFF4F66FF),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Back Button
                                IconButton(
                                    onClick = onBackClick,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }

                                // Title and Subtitle
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Cài đặt tài khoản",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Quản lí thông tin cá nhân",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }

                                // Empty space for balance
                                Spacer(modifier = Modifier.size(24.dp))
                            }
                        }

                        // Main Content with Vertical Scroll
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(WindowInsets.statusBars.asPaddingValues())
                        ) {
                            // PHẦN 1: THÔNG TIN CÁ NHÂN
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    // Section Header
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                            .background(
                                                Color(0xFF4F66FF).copy(alpha = 0.1f),
                                                RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "1. Thông tin cá nhân",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF4F66FF),
                                            modifier = Modifier
                                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        // Họ và tên Field
                                        Column {
                                            Text(
                                                text = "Họ và tên",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            OutlinedTextField(
                                                value = name,
                                                onValueChange = { name = it },
                                                modifier = Modifier.fillMaxWidth(),
                                                placeholder = { Text("Nhập họ tên...") },
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = Color(0xFF4F66FF),
                                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                                    focusedTextColor = Color.Black,
                                                    unfocusedTextColor = Color.Black
                                                )
                                            )
                                        }

                                        // Email Field
                                        Column {
                                            Text(
                                                text = "Email",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            OutlinedTextField(
                                                value = email,
                                                onValueChange = { email = it },
                                                modifier = Modifier.fillMaxWidth(),
                                                placeholder = { Text("Nhập email...") },
                                                enabled = true,
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = Color(0xFF4F66FF),
                                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                                    focusedTextColor = Color.Black,
                                                    unfocusedTextColor = Color.Black
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))

                                    // Save Button
                                    Button(
                                        onClick = {
                                            viewModel.saveUserData(name, email)
                                            Toast.makeText(context, "Lưu thành công!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF4F66FF)
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "Lưu thay đổi",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))

                                    // Divider
                                    Divider(
                                        color = Color.LightGray.copy(alpha = 0.5f),
                                        thickness = 1.dp
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        // Height Field
                                        Column {
                                            Text(
                                                text = "Chiều cao",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            OutlinedTextField(
                                                value = height,
                                                onValueChange = { },
                                                modifier = Modifier.fillMaxWidth(),
                                                enabled = false,
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    disabledTextColor = Color.Black,
                                                    disabledBorderColor = Color.Gray.copy(alpha = 0.5f)
                                                )
                                            )
                                        }

                                        // Weight Field
                                        Column {
                                            Text(
                                                text = "Cân nặng",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            OutlinedTextField(
                                                value = weight,
                                                onValueChange = { },
                                                modifier = Modifier.fillMaxWidth(),
                                                enabled = false,
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    disabledTextColor = Color.Black,
                                                    disabledBorderColor = Color.Gray.copy(alpha = 0.5f)
                                                )
                                            )
                                        }

                                        // Goal Field
                                        Column {
                                            Text(
                                                text = "Mục tiêu",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = Color.Black,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            OutlinedTextField(
                                                value = goal,
                                                onValueChange = { },
                                                modifier = Modifier.fillMaxWidth(),
                                                enabled = false,
                                                shape = RoundedCornerShape(12.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    disabledTextColor = Color.Black,
                                                    disabledBorderColor = Color.Gray.copy(alpha = 0.5f)
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            // PHẦN 2: BẢO MẬT & ĐĂNG NHẬP
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    // Section Header - Bảo mật & Đăng nhập
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                            .background(
                                                Color(0xFF3B66FF).copy(alpha = 0.1f),
                                                RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "2. Bảo mật & đăng nhập",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF3B66FF),
                                            modifier = Modifier
                                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    // Block 1: Đổi mật khẩu
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Đổi mật khẩu",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFF1A1A1A)
                                            )
                                            Text(
                                                text = "Cập nhật mật khẩu để bảo vệ tài khoản",
                                                fontSize = 14.sp,
                                                color = Color(0xFF9E9E9E),
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }

                                        OutlinedButton(
                                            onClick = { /* Handle change password */ },
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color.Black
                                            ),
                                            modifier = Modifier.height(40.dp)
                                        ) {
                                            Text(
                                                text = "Đổi mật khẩu",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    // Divider
                                    Divider(
                                        color = Color(0xFFE0E0E0),
                                        thickness = 1.dp,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )



                                    // Block 3: Liên kết tài khoản
                                    Column {
                                        Text(
                                            text = "Liên kết tài khoản",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color(0xFF1A1A1A)
                                        )
                                        Text(
                                            text = "Kết nối với mạng xã hội",
                                            fontSize = 14.sp,
                                            color = Color(0xFF9E9E9E),
                                            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                                        )

                                        // Google Account
                                        val isGoogleLinked = providers.contains("google.com")
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.google),
                                                    contentDescription = "Google Icon",
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Text(
                                                    text = "Google",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF1A1A1A)
                                                )
                                            }

                                            Text(
                                                text = if (isGoogleLinked) "Đã liên kết" else "Chưa liên kết",
                                                fontSize = 14.sp,
                                                color = if (isGoogleLinked) Color(0xFF4CAF50) else Color(0xFF9E9E9E),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }

                                        // Divider between accounts
                                        Divider(
                                            color = Color(0xFFE0E0E0).copy(alpha = 0.5f),
                                            thickness = 1.dp
                                        )

                                        // Github Account
                                        val isGithubLinked = providers.contains("github.com")
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.github),
                                                    contentDescription = "Github Icon",
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Text(
                                                    text = "Github",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF1A1A1A)
                                                )
                                            }

                                            Text(
                                                text = if (isGithubLinked) "Đã liên kết" else "Chưa liên kết",
                                                fontSize = 14.sp,
                                                color = if (isGithubLinked) Color(0xFF4CAF50) else Color(0xFF9E9E9E),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }

                                        // Divider between accounts
                                        Divider(
                                            color = Color(0xFFE0E0E0).copy(alpha = 0.5f),
                                            thickness = 1.dp
                                        )

                                        // Email Account
                                        val isEmailLinked = providers.contains("password")
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_email),
                                                    contentDescription = "Email Icon",
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Text(
                                                    text = "Email",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF1A1A1A)
                                                )
                                            }

                                            Text(
                                                text = if (isEmailLinked) "Đã liên kết" else "Chưa liên kết",
                                                fontSize = 14.sp,
                                                color = if (isEmailLinked) Color(0xFF4CAF50) else Color(0xFF9E9E9E),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }

                            // PHẦN 3: CÀI ĐẶT TÀI KHOẢN
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    // Section Header - Cài đặt tài khoản
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                            .background(
                                                Color(0xFF3B66FF).copy(alpha = 0.1f),
                                                RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "3. Cài đặt tài khoản",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF3B66FF),
                                            modifier = Modifier
                                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    // Block 3: Thông báo
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Thông báo",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFF1A1A1A)
                                            )
                                            Text(
                                                text = "Nhận thông báo từ ứng dụng",
                                                fontSize = 14.sp,
                                                color = Color(0xFF9E9E9E),
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }

                                        Switch(
                                            checked = notificationsEnabled,
                                            onCheckedChange = { notificationsEnabled = it },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = Color(0xFF3B66FF),
                                                checkedTrackColor = Color(0xFF3B66FF).copy(alpha = 0.5f),
                                                uncheckedThumbColor = Color(0xFF9E9E9E),
                                                uncheckedTrackColor = Color(0xFFE0E0E0)
                                            )
                                        )
                                    }
                                }
                            }

                            // PHẦN 4: HỖ TRỢ & KHÁC
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    // Section Header - Hỗ trợ & khác
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                            .background(
                                                Color(0xFF3B66FF).copy(alpha = 0.1f),
                                                RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "4. Hỗ trợ & khác",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF3B66FF),
                                            modifier = Modifier
                                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                    // Block 3: Điều khoản sử dụng
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Điều khoản sử dụng",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFF1A1A1A)
                                            )
                                            Text(
                                                text = "Xem điều khoản và chính sách bảo mật",
                                                fontSize = 14.sp,
                                                color = Color(0xFF9E9E9E),
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }

                                        OutlinedButton(
                                            onClick = { navController?.navigate(NavRoutes.Terms) },
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color(0xFF1A1A1A),
                                                containerColor = Color.White
                                            ),
                                            border = BorderStroke(1.dp, Color(0xFF1A1A1A)),
                                            modifier = Modifier
                                                .width(100.dp)
                                                .height(40.dp)
                                        ) {
                                            Text(
                                                text = "Xem",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1A1A1A)
                                            )
                                        }
                                    }
                                }
                            }

                            // PHẦN 5: VÙNG NGUY HIỂM - XÓA TÀI KHOẢN
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Viền đỏ ngoài cùng (hiệu ứng đường đỏ bên trái)
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(
                                            color = Color(0xFFFF4D4F),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                )

                                // Card chính đặt chồng lên, hơi dịch sang phải
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                        .shadow(
                                            elevation = 4.dp,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(
                                            color = Color(0xFFFFF5F5),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    // Viền trái đỏ bên trong
                                    Box(
                                        modifier = Modifier
                                            .width(6.dp)
                                            .fillMaxHeight()
                                            .background(
                                                color = Color(0xFFFF4D4F),
                                                shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                                            )
                                    )

                                    // Nội dung chính
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 20.dp, top = 20.dp, end = 28.dp, bottom = 20.dp)
                                    ) {
                                        // Header cảnh báo
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.Warning,
                                                contentDescription = "Cảnh báo",
                                                tint = Color(0xFFFF4D4F),
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Vùng nguy hiểm",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFFFF4D4F)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))
                                        Divider(color = Color(0xFFFFD6D6), thickness = 1.dp)
                                        Spacer(modifier = Modifier.height(12.dp))

                                        // Nội dung chính
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = "Xóa tài khoản",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                    color = Color.Black
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "Xóa vĩnh viễn tài khoản và tất cả dữ liệu của bạn",
                                                    fontSize = 13.sp,
                                                    color = Color(0xFF555555)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            Button(
                                                onClick = { /* Handle delete account */ },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFFFF4D4F)
                                                ),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.height(40.dp)
                                            ) {
                                                Text(
                                                    text = "Xóa",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // PHẦN 6: ĐĂNG XUẤT
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Button(
                                    onClick = {
                                        // ✅ CHỈ cần signOut Firebase
                                        FirebaseAuth.getInstance().signOut()

                                        // Navigate về Login và xóa toàn bộ back stack
                                        navController?.navigate(NavRoutes.Login) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    border = BorderStroke(1.dp, Color.LightGray),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                ) {
                                    Text(
                                        text = "Đăng xuất",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen() {
    MaterialTheme {
        SettingScreen()
    }
}
