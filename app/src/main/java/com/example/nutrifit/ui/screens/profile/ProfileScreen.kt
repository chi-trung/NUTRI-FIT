package com.example.nutrifit.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
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
import androidx.compose.ui.text.style.TextOverflow
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
            .background(MaterialTheme.colorScheme.background) // THAY ĐỔI: Dùng background từ theme
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Thông tin cá nhân",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        AvatarUploader()

        Text(
            text = "Tải lên ảnh đại diện",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Tên của bạn",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hoTen,
            onValueChange = { viewModel.onHoTenChanged(it) },
            placeholder = { Text("Nhập họ tên", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // THAY ĐỔI: Surface bao bọc toàn bộ grid
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background // Nền tương tự background chính
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp), // Tối ưu padding nếu cần
                verticalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các hàng
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các cột
                ) {
                    InfoButton(
                        title = "Giới tính",
                        iconRes = R.drawable.gioitinh, // THAY ĐỔI: Dùng resource ID
                        value = gioiTinh,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Giới tính" }
                    )
                    InfoButton(
                        title = "Tuổi",
                        iconRes = R.drawable.dotuoi, // THAY ĐỔI: Dùng resource ID
                        value = tuoi,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Tuổi" }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các cột
                ) {
                    InfoButton(
                        title = "Chiều cao",
                        iconRes = R.drawable.chieucao, // THAY ĐỔI: Dùng resource ID
                        value = chieuCao,
                        modifier = Modifier.weight(1f),
                        onClick = { showDialogFor = "Chiều cao" }
                    )
                    InfoButton(
                        title = "Cân nặng",
                        iconRes = R.drawable.cannang, // THAY ĐỔI: Dùng resource ID
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
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Tiếp tục", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }

    // --- Các Dialog không thay đổi đáng kể, giữ nguyên ---
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo Nutri-Fit",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "Chào mừng bạn đến với",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "NUTRI - FIT",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
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
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = CircleShape
            )
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun InfoButton(
    title: String,
    iconRes: Int, // THAY ĐỔI: Nhận Resource ID cho ảnh
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .height(110.dp), // THAY ĐỔI: Tăng chiều cao để khớp với ảnh
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface, // Nền trắng cho các thẻ
        tonalElevation = 1.dp // THAY ĐỔI: Thêm một chút đổ bóng nhẹ để tạo chiều sâu
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp) // THAY ĐỔI: Padding bên trong
        ) {
            Image( // THAY ĐỔI: Dùng Image thay vì Icon
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(36.dp), // THAY ĐỔI: Kích thước icon lớn hơn một chút
                contentScale = ContentScale.Fit // Đảm bảo ảnh không bị cắt xén
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium, // Kiểu chữ nhỏ hơn cho tiêu đề
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f) // Màu xám nhẹ
            )
            Spacer(modifier = Modifier.height(4.dp))

            val displayValue = if (value.isBlank()) "Chọn" else value
            val valueColor = if (value.isBlank()) {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colorScheme.onSurface
            }
            val valueWeight = if (value.isBlank()) FontWeight.Normal else FontWeight.SemiBold

            Text(
                text = displayValue,
                style = MaterialTheme.typography.titleMedium, // THAY ĐỔI: Kiểu chữ lớn hơn, nổi bật hơn cho giá trị
                color = valueColor,
                fontWeight = valueWeight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


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
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(placeholder) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier.fillMaxWidth()
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

@Composable
fun GenderSelectionDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val genderOptions = listOf("Nam", "Nữ", "Khác")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Chọn giới tính",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                genderOptions.forEach { gender ->
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodyLarge,
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