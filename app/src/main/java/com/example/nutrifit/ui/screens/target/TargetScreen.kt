package com.example.nutrifit.ui.screens.target

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R

@Composable
fun TargetScreen(
    onBack: () -> Unit,
    onNextClicked: () -> Unit // <-- THAY ĐỔI 1: Thêm tham số onNextClicked
) {
    var isChecked by remember { mutableStateOf(false) }
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(false) }
    var isChecked3 by remember { mutableStateOf(false) }
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
                painter = painterResource(id = R.drawable.vector_2),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(1.dp))

            Image(
                painter = painterResource(id = R.drawable.vector_1),
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
                text = "Mục tiêu của bạn là gì?",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .offset(y = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Chúng tôi sẽ cá nhân hóa lộ trình tập luyện và chế độ ăn uống theo mục tiêu của bạn.",
                fontSize = 14.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .padding(bottom = 110.dp)
                .padding(start = 17.dp, end = 17.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.khungnho2),
                contentDescription = null,
                modifier = Modifier
                    .size(800.dp)
                    .clip(RoundedCornerShape(25.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // o an uong lanh manh
                Box() {
                    if (isChecked) {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2_2_),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    }
                    Row(modifier = Modifier.offset(x = 50.dp, y = 10.dp)) {
                        Column() {
                            Text(
                                text = "Ăn uống lành mạnh",
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier.offset(x = 10.dp)
                            )
                            Text(
                                text = "Đốt mỡ, thon gọn một cách khoa học",
                                fontSize = 9.sp,
                                color = Color.Black
                            )
                        }
                        Box(
                            modifier = Modifier
                                .offset(x = 80.dp, y = 20.dp)
                                .padding(end = 40.dp)
                                .size(27.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (isChecked) Color(0xFF4CAF50) else Color.Transparent
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isChecked) Color(0xFF4CAF50) else Color.Gray,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { isChecked = !isChecked },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isChecked) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White // Dấu tích trắng
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                // o giam can
                Box() {
                    if (isChecked1) {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2_2_),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    }
                    Row(modifier = Modifier.offset(x = 50.dp, y = 10.dp)) {
                        Column() {
                            Text(
                                text = "Giảm cân",
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier.offset(x = 10.dp)
                            )
                            Text(
                                text = "Phát triển cơ bắp, tăng cân lành mạnh",
                                fontSize = 9.sp,
                                color = Color.Black
                            )
                        }

                        Box(
                            modifier = Modifier
                                .offset(x = 75.dp, y = 20.dp)
                                .size(27.dp) // Kích thước ô
                                .clip(RoundedCornerShape(6.dp)) // Bo góc nhẹ
                                .background(
                                    if (isChecked1) Color(0xFF4CAF50) else Color.Transparent // xanh lá khi chọn, trong suốt khi chưa
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isChecked1) Color(0xFF4CAF50) else Color.Gray, // viền xanh khi chọn
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { isChecked1 = !isChecked1 }, // Khi nhấn thì đổi trạng thái
                            contentAlignment = Alignment.Center
                        ) {
                            if (isChecked1) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White // Dấu tích trắng
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                // o tang co, tang can
                Box() {
                    if (isChecked2) {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2_2_),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    }
                    Row(modifier = Modifier.offset(x = 50.dp, y = 10.dp)) {
                        Column() {
                            Text(
                                text = "Tăng cơ / Tăng cân",
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier.offset(x = 10.dp)
                            )
                            Text(
                                text = "Cân bằng tập luyện và dinh dưỡng luôn khỏe mạnh",
                                fontSize = 9.sp,
                                color = Color.Black
                            )
                        }

                        Box(
                            modifier = Modifier
                                .offset(x = 20.dp, y = 20.dp)
                                .size(27.dp) // Kích thước ô
                                .clip(RoundedCornerShape(6.dp)) // Bo góc nhẹ
                                .background(
                                    if (isChecked2) Color(0xFF4CAF50) else Color.Transparent // xanh lá khi chọn, trong suốt khi chưa
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isChecked2) Color(0xFF4CAF50) else Color.Gray, // viền xanh khi chọn
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { isChecked2 = !isChecked2 }, // Khi nhấn thì đổi trạng thái
                            contentAlignment = Alignment.Center
                        ) {
                            if (isChecked2) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White // Dấu tích trắng
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                //o giu dang va duy tri suc khoe
                Box() {
                    if (isChecked3) {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2_2_),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.khungnutbam2),
                            contentDescription = null,
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth()
                        )
                    }
                    Row(modifier = Modifier.offset(x = 50.dp, y = 10.dp)) {
                        Column() {
                            Text(
                                text = "Giữ dáng / Duy trì sức khỏe",
                                fontSize = 15.sp,
                                color = Color.Black,
                                modifier = Modifier.offset(x = 10.dp)
                            )
                            Text(
                                text = "Xây dựng thói quen ăn uống khoa học và bền vững",
                                fontSize = 9.sp,
                                color = Color.Black
                            )
                        }

                        Box(
                            modifier = Modifier
                                .offset(x = 20.dp, y = 20.dp)
                                .size(27.dp) // Kích thước ô
                                .clip(RoundedCornerShape(6.dp)) // Bo góc nhẹ
                                .background(
                                    if (isChecked3) Color(0xFF4CAF50) else Color.Transparent // xanh lá khi chọn, trong suốt khi chưa
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isChecked3) Color(0xFF4CAF50) else Color.Gray, // viền xanh khi chọn
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { isChecked3 = !isChecked3 }, // Khi nhấn thì đổi trạng thái
                            contentAlignment = Alignment.Center
                        ) {
                            if (isChecked3) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White // Dấu tích trắng
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Quay lại",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .clickable {
                            onBack()
                        }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .offset(y = 60.dp)
                    .fillMaxSize()

            ) {
                if (isChecked or isChecked1 or isChecked2 or isChecked3 == false) {
                    Text(
                        text = "Tiếp tục",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                    )
                } else {
                    Button(
                        onClick = {
                            onNextClicked() // <-- THAY ĐỔI 2: Gọi onNextClicked
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // xanh dương
                        shape = RoundedCornerShape(8.dp), // bo nhẹ góc
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(48.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp)) // bóng mờ nhẹ
                    ) {
                        Text(
                            text = "Tiếp tục",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}



