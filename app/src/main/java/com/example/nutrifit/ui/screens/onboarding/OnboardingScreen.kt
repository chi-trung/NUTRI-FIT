package com.example.nutrifit.ui.screens.onboarding

import android.R.attr.data
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.example.nutrifit.R
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            title = "NUTRI - FIT",
            description = "  Ăn uống lành mạnh\n Luyện tập thông minh",
            imageRes = R.drawable.logo,
            titleColor = Color(0xFF1AC9AC)
        ),
        OnboardingPage(
            title = "      Tập luyện hiệu quả\n Video hướng dẫn chi tiết",
            description = "Chọn nhóm cơ bạn muốn tập và theo dõi các video hướng dẫn bài bản, giúp bạn rèn luyện đúng kỹ thuật và đạt được kết quả mong muốn",
            imageRes = R.drawable.rectangle_59
        ),
        OnboardingPage(
            title = "    Ăn uống khoa học\n Gợi ý bữa ăn phù hợp",
            description = "Ứng dụng gợi ý bữa ăn lành mạnh, cân bằng dinh dưỡng và hỗ trợ kiểm soát calo, giúp bạn duy trì năng lượng và cải thiện sức khỏe",
            imageRes = R.drawable.intro2
        ),
        OnboardingPage(
            title = "               Sống cân bằng\n Lối sống khoẻ mạnh mỗi ngày",
            description = "Kết hợp tập luyện và dinh dưỡng thông minh để tạo nên một lối sống lành mạnh, tăng cường sức đề kháng và nâng cao chất lượng cuộc sống",
            imageRes = R.drawable.intro3
        )
    )

    val pagerState = rememberPagerState()
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(count = pages.size, state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val data = pages[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // hinh anh
                when (pagerState.currentPage) {
                    0 -> Image(
                        painter = painterResource(id = data.imageRes),
                        contentDescription = data.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(230.dp)
                            .width(230.dp)
                            .offset(y = (-50).dp)
                    )

                    1 -> Image(
                        painter = painterResource(id = data.imageRes),
                        contentDescription = data.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(317.dp)
                            .width(370.dp)
                            .offset(y = (-130).dp)
                            .clip(RoundedCornerShape(25.dp))
                    )

                    2 -> Image(
                        painter = painterResource(id = data.imageRes),
                        contentDescription = data.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(317.dp)
                            .width(370.dp)
                            .offset(y = (-130).dp)
                            .clip(RoundedCornerShape(25.dp))
                    )

                    3 -> Image(
                        painter = painterResource(id = data.imageRes),
                        contentDescription = data.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(317.dp)
                            .width(370.dp)
                            .offset(y = (-130).dp)
                            .clip(RoundedCornerShape(25.dp))
                    )




                }

                Spacer(modifier = Modifier.height(24.dp))

                //thanh tien trinh

                if (pagerState.currentPage > 0) {
                    LazyRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .offset(y = (-150).dp, x = (-130).dp)
                    ) {
                        items(3) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .height(6.dp)
                                    .width(17.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        if (pagerState.currentPage == index + 1)
                                            Color.Black
                                        else
                                            Color(0xFFD3D9DD)
                                    )
                            )
                        }
                    }
                }



                //tieu de
                if (pagerState.currentPage == 0) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF1AC9AC),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("NUTRI")
                            }
                            withStyle(
                                style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                            ) {
                                append(" - ")
                            }
                            withStyle(
                                style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
                            ) {
                                append("FIT")
                            }
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 46.sp,
                        modifier = Modifier.offset(y = (-20).dp)
                    )


                }
                else  {
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = data.titleColor,
                        modifier = Modifier.offset(y = (-120).dp)

                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                //noi dung

                if (pagerState.currentPage == 0) {
                    Text(
                        text = data.description,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                else {
                    Text(
                        text = data.description,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 17.sp,
                        modifier = Modifier.offset(y = (-80).dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        val isLast = pagerState.currentPage == pages.lastIndex


        // Hiển thị dòng “Bấm vào để tiếp tục” chỉ ở màn đầu tiên
        if (pagerState.currentPage == 0) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Bấm vào để tiếp tục",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 17.sp,
                modifier = Modifier
                    .offset(y = (-120).dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
            )
        }
        // nếu không phải màn hình đầu tiên thì sẽ hiển thị nút
        if (pagerState.currentPage > 0) {
            Button(
                onClick = {
                    if (isLast) {
                        onStart()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x8A8E91),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(6.dp), // bo góc nhẹ
                border = BorderStroke(1.dp, Color(0xFFD3D9DD)), // viền xám nhạt
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 24.dp)
                    .offset(y = (-35).dp)
            ) {
                Text(
                    text = if (isLast) "Bắt đầu" else "Tiếp tục",
                    fontSize = 18.sp,

                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tôi đã có tài khoản",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        onStart()
                    }
            )
        }

    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int,
    val logo: Painter? = null,
    val titleColor: Color = Color.Black // mặc định là đen, có thể đổi khi truyền vào
)
