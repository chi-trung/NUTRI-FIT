// Trong file: ui/screens/target/TargetScreen.kt
package com.example.nutrifit.ui.screens.target

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // <-- SỬA LỖI 1: THÊM IMPORT NÀY
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
// import androidx.compose.ui.tooling.animation.states.TargetState // <-- SỬA LỖI 2: XÓA DÒNG SAI NÀY
import com.example.nutrifit.data.model.TargetState // <-- SỬA LỖI 3: THÊM DÒNG ĐÚNG NÀY
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrifit.viewmodel.TargetViewModel

@Composable
fun TargetScreen(
    onNextClicked: () -> Unit,
    viewModel: TargetViewModel = viewModel()
) {
    val targetList = viewModel.targetList

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            onNextClicked()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Mục tiêu của bạn là gì?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Chúng tôi sẽ cá nhân hoá lộ trình...",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Giờ đây 'items' và 'target' đã được nhận diện đúng
            items(targetList) { target ->
                CheckboxRow(
                    state = target,
                    onClicked = {
                        viewModel.onTargetClicked(target)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.onNextClicked() },
            modifier = Modifier
                .fillMaxWidth()
                    .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Tiếp tục", fontSize = 18.sp)
        }
    }
}

@Composable
fun CheckboxRow(
    state: TargetState, // Giờ đây 'TargetState' là data class của bạn
    onClicked: () -> Unit
) {
    // Lỗi này tự hết
    val borderColor = if (state.isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClicked() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.title, // Lỗi này tự hết
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = state.description, // Lỗi này tự hết
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Checkbox(
            checked = state.isSelected, // Lỗi này tự hết
            onCheckedChange = { onClicked() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}