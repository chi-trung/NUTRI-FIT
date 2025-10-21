package com.example.nutrifit.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.nutrifit.R
import com.example.nutrifit.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onNextClicked: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val hoTen = viewModel.hoTen
    val gioiTinh = viewModel.gioiTinh
    val tuoi = viewModel.tuoi
    val chieuCao = viewModel.chieuCao
    val canNang = viewModel.canNang

    var showDialogFor by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is ProfileViewModel.NavigationEvent.NavigateToNextScreen -> onNextClicked()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Phần Header (giữ nguyên) ---
        Header()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Thông tin cá nhân",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(24.dp))
        AvatarUploader()
        Text(
            text = "Tải lên ảnh đại diện",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Tên của bạn",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = hoTen,
            onValueChange = { viewModel.onHoTenChanged(it) },
            placeholder = { Text("Nhập họ tên") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF0F0F8)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // THAY ĐỔI 3: Cập nhật InfoButton
                    InfoButton(
                        title = "Giới tính",
                        icon = Icons.Default.Wc,
                        value = gioiTinh,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Giới tính" }
                    )
                    InfoButton(
                        title = "Tuổi",
                        icon = Icons.Outlined.CalendarToday,
                        value = tuoi,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Tuổi" }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoButton(
                        title = "Chiều cao",
                        icon = Icons.Outlined.Height,
                        value = chieuCao,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Chiều cao" }
                    )
                    InfoButton(
                        title = "Cân nặng",
                        icon = Icons.Outlined.MonitorWeight,
                        value = canNang,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Cân nặng" }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { viewModel.onNextClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Tiếp tục", fontSize = 18.sp)
        }
    }

    when (showDialogFor) {
        "Giới tính" -> {
            GenderSelectionDialog(
                onDismiss = { showDialogFor = null },
                onConfirm = { selectedGender ->
                    viewModel.onGioiTinhChanged(selectedGender)
                    showDialogFor = null
                }
            )
        }
        "Tuổi" -> {
            InfoInputDialog(
                title = "Nhập tuổi của bạn",
                onDismiss = { showDialogFor = null },
                onConfirm = { newAge ->
                    viewModel.onTuoiChanged(newAge)
                    showDialogFor = null
                },
                placeholder = "Ví dụ: 25",
                keyboardType = KeyboardType.Number
            )
        }
        "Chiều cao" -> {
            InfoInputDialog(
                title = "Nhập chiều cao (cm)",
                onDismiss = { showDialogFor = null },
                onConfirm = { newHeight ->
                    viewModel.onChieuCaoChanged(newHeight)
                    showDialogFor = null
                },
                placeholder = "Ví dụ: 170",
                keyboardType = KeyboardType.Number
            )
        }
        "Cân nặng" -> {
            InfoInputDialog(
                title = "Nhập cân nặng (kg)",
                onDismiss = { showDialogFor = null },
                onConfirm = { newWeight ->
                    viewModel.onCanNangChanged(newWeight)
                    showDialogFor = null
                },
                placeholder = "Ví dụ: 65",
                keyboardType = KeyboardType.Number
            )
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo Nutri-Fit",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Chào mừng bạn đến với ",
            fontSize = 16.sp
        )
        Text(
            text = "NUTRI - FIT",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AvatarUploader() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Black)
            .clickable {
                launcher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Ảnh đại diện",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.CloudUpload,
                contentDescription = "Tải lên ảnh đại diện",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun InfoButton(
    title: String,
    icon: ImageVector,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White
        ),
        border = null
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontSize = 14.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (value.isBlank()) "....." else value,
                fontSize = 14.sp,
                color = if (value.isBlank()) Color.Gray else Color.Black,
                fontWeight = if (value.isBlank()) FontWeight.Normal else FontWeight.Bold
            )
        }
    }
}



/**
 * Dialog chung để nhập text (dùng cho Tuổi, Chiều cao, Cân nặng)
 */
@Composable
fun InfoInputDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(placeholder) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(text)
                }
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Hủy")
            }
        }
    )
}

/**
 * Dialog riêng để chọn Giới tính
 */
@Composable
fun GenderSelectionDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val genderOptions = listOf("Nam", "Nữ", "Khác")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Chọn giới tính",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                genderOptions.forEach { gender ->
                    Text(
                        text = gender,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onConfirm(gender)
                            }
                            .padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}