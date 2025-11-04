package com.example.nutrifit.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nutrifit.R

@Composable
fun ProfileScreen(
    onNextClicked: () -> Unit // <-- THAY ĐỔI 1: Thêm tham số onNextClicked
) {
    // Xóa bỏ: var showNextScreen by remember { mutableStateOf(false) }
    // Xóa bỏ: if (showNextScreen) { ... } else { ... }
    // -> Giữ lại nội dung bên trong "else" làm nội dung chính

    var name by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val bottomPadding = screenHeightDp * 0.2f
    val interactionSource = remember { MutableInteractionSource() }
    var genderExpanded by remember { mutableStateOf(false) }
    var ageExpanded by remember { mutableStateOf(false) }
    var heightExpanded by remember { mutableStateOf(false) }
    var weightExpanded by remember { mutableStateOf(false) }

    var selectedGender by remember { mutableStateOf("...") }
    var selectedAge by remember { mutableStateOf("...") }
    var selectedHeight by remember { mutableStateOf("...") }
    var selectedWeight by remember { mutableStateOf("...") }

    // Logic để chọn ảnh
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F1F3))
            .padding(WindowInsets.statusBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // thanh tien trinh
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .offset(y = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector_1),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(1.dp))
            Image(
                painter = painterResource(id = R.drawable.vector_2),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row() {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .offset(x = (-5).dp)
            )
            Text(
                text = "Chào mừng bạn đến với",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF1AC9AC),
                        fontWeight = FontWeight.Bold
                    )
                ) { append("NUTRI") }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                ) { append(" - ") }
                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                ) { append("FIT") }
            },
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp,
            modifier = Modifier.offset(y = (-43).dp, x = (-26).dp)
        )

        //khung thong tin ca nhan
        Column(modifier = Modifier.offset(y = -30.dp)) {
            Box() {
                //khung
                Image(
                    painter = painterResource(id = R.drawable.rectangle_80),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                //tieu de
                Text(
                    text = "Thông tin cá nhân",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 23.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .offset(y = 20.dp, x = 40.dp)
                )
                //can giua cac thanh phan
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, // căn giữa theo trục ngang
                    modifier = Modifier.fillMaxSize().offset(y = 65.dp)
                ) {
                    //gop 3 lop avt
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.ellipse_2),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .offset(x = -5.dp, y = -5.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ellipse_3),
                            contentDescription = null,
                            modifier = Modifier
                                .size(110.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ellipse_1),
                            contentDescription = null,
                            modifier = Modifier
                                .size(110.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Tải lên ảnh đại diện",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable { launcher.launch("image/*") }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                "Tên của bạn",
                                style = MaterialTheme.typography.headlineSmall,
                                fontSize = 13.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        placeholder = { Text("Nhập họ tên") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(horizontal = 25.dp),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    //khung nho hien thi 4 nut bam dien thong tin ca nhan
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.khungnho),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 40.dp)
                                .size(700.dp)
                                .padding(bottom = bottomPadding)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.padding(bottom = 150.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
                            // gioi tinh do tuoi
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                //khung gioi tinh
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(start = 50.dp, end = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khungnutbam),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .offset(y = -50.dp)
                                    )
                                    Text(
                                        text = "Giới tính",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .padding(top = 15.dp)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .offset(x = 20.dp, y = -30.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Surface(
                                            color = Color.Transparent,
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = ripple(
                                                        bounded = true,
                                                        color = Color.Gray.copy(alpha = 0.3f)
                                                    )
                                                ) {
                                                    genderExpanded = true
                                                }
                                        ) {
                                            Text(
                                                text = selectedGender,
                                                modifier = Modifier.padding(16.dp),
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        }
                                        DropdownMenu(
                                            shape = RoundedCornerShape(25.dp),
                                            expanded = genderExpanded,
                                            onDismissRequest = { genderExpanded = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "Nam",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedGender = "Nam"
                                                    genderExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "Nữ",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedGender = "Nữ"
                                                    genderExpanded = false
                                                }
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(top = 40.dp, start = 15.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.khungtronnho),
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.gioitinh),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }

                                //khung do tuoi
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(end = 50.dp, start = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khungnutbam),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .offset(y = -50.dp)
                                    )
                                    Text(
                                        text = "Độ tuổi",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .padding(top = 15.dp)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .offset(x = 20.dp, y = -30.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Surface(
                                            color = Color.Transparent,
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = ripple(
                                                        bounded = true,
                                                        color = Color.Gray.copy(alpha = 0.3f)
                                                    )
                                                ) {
                                                    ageExpanded = true
                                                }
                                        ) {
                                            Text(
                                                text = selectedAge,
                                                modifier = Modifier.padding(16.dp),
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        }
                                        DropdownMenu(
                                            shape = RoundedCornerShape(25.dp),
                                            expanded = ageExpanded,
                                            onDismissRequest = { ageExpanded = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "18-25",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedAge = "18-25"
                                                    ageExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "26-35",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedAge = "26-35"
                                                    ageExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "36-45",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedAge = "36-45"
                                                    ageExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "46+",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedAge = "46+"
                                                    ageExpanded = false
                                                }
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(top = 40.dp, start = 15.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.khungtronnho),
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.dotuoi),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                            }

                            // chieu cao can nang
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                //khung chieu cao
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(start = 50.dp, end = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khungnutbam),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .offset(y = -50.dp)
                                    )
                                    Text(
                                        text = "Chiều cao",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .padding(top = 15.dp)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .offset(x = 20.dp, y = -30.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Surface(
                                            color = Color.Transparent,
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = ripple(
                                                        bounded = true,
                                                        color = Color.Gray.copy(alpha = 0.3f)
                                                    )
                                                ) {
                                                    heightExpanded = true
                                                }
                                        ) {
                                            Text(
                                                text = selectedHeight,
                                                modifier = Modifier.padding(16.dp),
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        }
                                        DropdownMenu(
                                            shape = RoundedCornerShape(25.dp),
                                            expanded = heightExpanded,
                                            onDismissRequest = { heightExpanded = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "150-160 cm",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedHeight = ">150 cm"
                                                    heightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "161-170 cm",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedHeight = ">161 cm"
                                                    heightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "171-180 cm",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedHeight = ">171 cm"
                                                    heightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "181-190 cm",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedHeight = ">181 cm"
                                                    heightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "191+ cm",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedHeight = "191+ cm"
                                                    heightExpanded = false
                                                }
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(top = 40.dp, start = 15.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.khungtronnho),
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.chieucao),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }

                                //khung can nang
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(end = 50.dp, start = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khungnutbam),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .offset(y = -50.dp)
                                    )
                                    Text(
                                        text = "Cân nặng",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .padding(top = 15.dp)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .offset(x = 10.dp, y = -30.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Surface(
                                            color = Color.Transparent,
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = ripple(
                                                        bounded = true,
                                                        color = Color.Gray.copy(alpha = 0.3f)
                                                    )
                                                ) {
                                                    weightExpanded = true
                                                }
                                        ) {
                                            Text(
                                                text = selectedWeight,
                                                modifier = Modifier.padding(16.dp),
                                                color = Color.Black,
                                                fontSize = 14.sp
                                            )
                                        }
                                        DropdownMenu(
                                            shape = RoundedCornerShape(25.dp),
                                            expanded = weightExpanded,
                                            onDismissRequest = { weightExpanded = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "40-50 kg",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedWeight = ">40 kg"
                                                    weightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "51-60 kg",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedWeight = ">51 kg"
                                                    weightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        ">61-70 kg",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedWeight = ">61 kg"
                                                    weightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "71-80 kg",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedWeight = ">71 kg"
                                                    weightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "81-90 kg",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedWeight = ">81 kg"
                                                    weightExpanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "91+ kg",
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                onClick = {
                                                    selectedWeight = "91+ kg"
                                                    weightExpanded = false
                                                }
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(top = 40.dp, start = 15.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.khungtronnho),
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.cannang),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "Tiếp tục",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                            onNextClicked()
                        }
                    )
                }
            }
        }
    }
}