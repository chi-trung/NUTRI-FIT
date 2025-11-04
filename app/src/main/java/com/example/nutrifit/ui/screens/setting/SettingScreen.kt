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
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBackClick: () -> Unit = {},
    onSaveChanges: (String, String, String) -> Unit = { _, _, _ -> },
    navController: NavController? = null
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var twoFactorAuth by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(false) }

    // State cho dropdowns
    var languageExpanded by remember { mutableStateOf(false) }
    var unitsExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Tiếng Việt") }
    var selectedUnits by remember { mutableStateOf("kg / cm") }

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

                            // Avatar Area
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        contentAlignment = Alignment.BottomEnd
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ellipse_1),
                                            contentDescription = "Profile Image",
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(CircleShape)
                                                .background(Color.LightGray),
                                            contentScale = ContentScale.Crop
                                        )

                                        // Camera Button
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF4F66FF))
                                                .clickable { /* Handle image change */ }
                                                .shadow(4.dp, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.CameraAlt,
                                                contentDescription = "Change Photo",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Change Photo Text Button
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .clickable { /* Handle image change */ }
                                            .background(Color(0xFF4F66FF).copy(alpha = 0.1f))
                                    ) {
                                        Text(
                                            text = "Thay đổi ảnh",
                                            color = Color(0xFF4F66FF),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                        )
                                    }
                                }
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
                                        value = fullName,
                                        onValueChange = { fullName = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        placeholder = { Text("Nhập họ tên...") },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color(0xFF4F66FF),
                                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
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
                                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                                        )
                                    )
                                }

                                // Số điện thoại Field
                                Column {
                                    Text(
                                        text = "Số điện thoại",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = phone,
                                        onValueChange = { phone = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        placeholder = { Text("Nhập số điện thoại...") },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color(0xFF4F66FF),
                                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Divider
                            Divider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Save Button
                            Button(
                                onClick = {
                                    onSaveChanges(fullName, email, phone)
                                    onBackClick()
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

                            // Block 2: Xác thực 2 bước
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
                                        text = "Xác thực 2 bước",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Tăng cường bảo mật cho tài khoản của bạn",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                Switch(
                                    checked = twoFactorAuth,
                                    onCheckedChange = { twoFactorAuth = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color(0xFF3B66FF),
                                        checkedTrackColor = Color(0xFF3B66FF).copy(alpha = 0.5f)
                                    )
                                )
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
                                        text = "Đã liên kết",
                                        fontSize = 14.sp,
                                        color = Color(0xFF4CAF50),
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                // Divider between accounts
                                Divider(
                                    color = Color(0xFFE0E0E0).copy(alpha = 0.5f),
                                    thickness = 1.dp
                                )

                                // Facebook Account
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(id = R.drawable.facebook),
                                            contentDescription = "Facebook Icon",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Facebook",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color(0xFF1A1A1A)
                                        )
                                    }

                                    Text(
                                        text = "Chưa liên kết",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
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

                            // Block 1: Ngôn ngữ
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
                                        text = "Ngôn ngữ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Chọn ngôn ngữ hiển thị",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                // Dropdown cho Ngôn ngữ
                                Box {
                                    OutlinedButton(
                                        onClick = { languageExpanded = true },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color(0xFF1A1A1A),
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(40.dp)
                                    ) {
                                        Text(
                                            text = selectedLanguage,
                                            fontSize = 14.sp,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = languageExpanded,
                                        onDismissRequest = { languageExpanded = false },
                                        modifier = Modifier.width(140.dp)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Tiếng Việt") },
                                            onClick = {
                                                selectedLanguage = "Tiếng Việt"
                                                languageExpanded = false
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("English") },
                                            onClick = {
                                                selectedLanguage = "English"
                                                languageExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Divider
                            Divider(
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            // Block 2: Đơn vị đo lường
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
                                        text = "Đơn vị đo lường",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Hệ thống đơn vị đo lường",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                // Dropdown cho Đơn vị đo lường
                                Box {
                                    OutlinedButton(
                                        onClick = { unitsExpanded = true },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color(0xFF1A1A1A),
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(40.dp)
                                    ) {
                                        Text(
                                            text = selectedUnits,
                                            fontSize = 14.sp,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = unitsExpanded,
                                        onDismissRequest = { unitsExpanded = false },
                                        modifier = Modifier.width(140.dp)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("kg / cm") },
                                            onClick = {
                                                selectedUnits = "kg / cm"
                                                unitsExpanded = false
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("lb / ft") },
                                            onClick = {
                                                selectedUnits = "lb / ft"
                                                unitsExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Divider
                            Divider(
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

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

                    // PHẦN 4: LỊCH SỬ & DỮ LIỆU
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
                            // Section Header - Lịch sử & dữ liệu
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
                                    text = "4. Lịch sử & dữ liệu",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3B66FF),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Block 1: Lịch sử hoạt động
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
                                        text = "Lịch sử hoạt động",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Xem lại lịch sử tập luyện và bữa ăn",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                OutlinedButton(
                                    onClick = { /* Xử lý xem lịch sử */ },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFF1A1A1A),
                                        containerColor = Color.White
                                    ),
                                    border = BorderStroke(1.dp, Color(0xFF1A1A1A)),
                                    modifier = Modifier.height(40.dp)
                                ) {
                                    Text(
                                        text = "Xem",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A)
                                    )
                                }
                            }

                            // Divider
                            Divider(
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            // Block 2: Thống kê cá nhân
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
                                        text = "Thống kê cá nhân",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Xem biểu đồ và phân tích hoạt động",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                OutlinedButton(
                                    onClick = { /* Xử lý xem thống kê */ },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFF1A1A1A),
                                        containerColor = Color.White
                                    ),
                                    border = BorderStroke(1.dp, Color(0xFF1A1A1A)),
                                    modifier = Modifier.height(40.dp)
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

                    // PHẦN 5: HỖ TRỢ & KHÁC
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
                                    text = "5. Hỗ trợ & khác",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3B66FF),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Block 1: Trung tâm trợ giúp
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Trung tâm trợ giúp",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Câu hỏi thường gặp và hỗ trợ",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                OutlinedButton(
                                    onClick = { /* Mở trung tâm trợ giúp */ },
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
                                        text = "Truy cập",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A)
                                    )
                                }
                            }

                            Divider(
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            // Block 2: Gửi phản hồi
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Gửi phản hồi",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1A1A1A)
                                    )
                                    Text(
                                        text = "Chia sẻ ý kiến của bạn về ứng dụng",
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                OutlinedButton(
                                    onClick = { /* Gửi phản hồi */ },
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
                                        text = "Gửi",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A)
                                    )
                                }
                            }

                            Divider(
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

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
                                    onClick = { /* Mở điều khoản sử dụng */ },
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

                    // PHẦN 6: VÙNG NGUY HIỂM - XÓA TÀI KHOẢN
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

                    // PHẦN 7: ĐĂNG XUẤT - THÊM MỚI
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
                        val context = LocalContext.current

                        Button(
                            onClick = {
                                // Xóa dữ liệu đăng nhập
                                val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                sharedPref.edit().clear().apply()

                                // Điều hướng về trang đăng nhập
                                navController?.navigate("login") {
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

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen() {
    MaterialTheme {
        SettingScreen()
    }
}