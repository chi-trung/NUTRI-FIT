package com.example.nutrifit.ui.screens.onboarding

import android.R.attr.left
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(count = pages.size, state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val data = pages[page]

            if (page > 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = WindowInsets.statusBars.asPaddingValues()
                            .calculateTopPadding())
                        .offset(y = (-90).dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- Phần hình ảnh ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = WindowInsets.statusBars.asPaddingValues()
                                    .calculateTopPadding()
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painter = painterResource(id = data.imageRes),
                            contentDescription = data.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(260.dp)
                                .width(360.dp)
                                .clip(RoundedCornerShape(25.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- Thanh tiến trình ---
                    LazyRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-125).dp)
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
                                            colorScheme.primary
                                        else
                                            colorScheme.outlineVariant
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(60.dp))

                    // --- Tiêu đề ---
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (data.titleColor == Color.Black)
                            colorScheme.onBackground
                        else
                            data.titleColor,
                        lineHeight = 28.sp,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // --- Mô tả ---
                    Text(
                        text = data.description,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        color = colorScheme.onBackground.copy(alpha = 0.85f),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // --- Trang đầu tiên ---
            if (page == 0) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = data.imageRes),
                        contentDescription = data.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(230.dp)
                            .width(230.dp)
                            .offset(y = -50.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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
                                    color = colorScheme.onBackground,
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
                        fontSize = 46.sp,
                        modifier = Modifier.offset(y = (-60).dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data.description,
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onBackground,
                        modifier = Modifier.offset(y = 0.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(80.dp))

                    Text(
                        text = "Bấm vào để tiếp tục",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.outline,
                        fontSize = 17.sp,
                        modifier = Modifier.clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(0.dp))

        val isLast = pagerState.currentPage == pages.lastIndex

        if (pagerState.currentPage > 0) {
            Button(
                onClick = {
                    if (isLast) onStart()
                    else scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                colors = if (isLast) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3475B7),
                        contentColor = Color.White
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = colorScheme.background,
                        contentColor = colorScheme.onBackground
                    )
                },
                shape = RoundedCornerShape(8.dp),
                border = if (isLast) {
                    BorderStroke(1.dp, colorScheme.outlineVariant)
                } else {
                    BorderStroke(1.dp, colorScheme.onBackground)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 0.dp)
                    .offset(y = (-24).dp)
            ) {
                Text(
                    text = if (isLast) "Bắt đầu" else "Tiếp tục",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tôi đã có tài khoản",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onStart() }
            )
        }
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int,
    val logo: Painter? = null,
    val titleColor: Color = Color.Black
)
