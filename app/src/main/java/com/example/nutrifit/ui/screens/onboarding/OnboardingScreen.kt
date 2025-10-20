package com.example.nutrifit.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.example.nutrifit.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            title = "Ăn uống lành mạnh",
            description = "Khám phá các thực đơn dinh dưỡng phù hợp với mục tiêu của bạn.",
            imageRes = R.drawable.rectangle_59
        ),
        OnboardingPage(
            title = "Tập luyện hiệu quả",
            description = "Lộ trình tập luyện khoa học giúp tối ưu kết quả.",
            imageRes = R.drawable.rectangle_59
        ),
        OnboardingPage(
            title = "Ăn uống khoa học",
            description = "Theo dõi macro, calo mỗi ngày một cách dễ dàng.",
            imageRes = R.drawable.rectangle_59
        ),
        OnboardingPage(
            title = "Sống cân bằng",
            description = "Giữ vững thói quen lành mạnh và bền vững.",
            imageRes = R.drawable.rectangle_59
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
                Image(
                    painter = painterResource(id = data.imageRes),
                    contentDescription = data.title,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = data.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = data.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPagerIndicator(pagerState = pagerState)
        Spacer(modifier = Modifier.height(16.dp))

        val isLast = pagerState.currentPage == pages.lastIndex
        Button(onClick = {
            if (isLast) onStart() else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
        }) {
            Text(if (isLast) "Bắt đầu" else "Tiếp tục")
        }
    }
}

data class OnboardingPage(val title: String, val description: String, val imageRes: Int)
