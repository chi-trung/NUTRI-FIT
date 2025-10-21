package com.example.nutrifit.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.model.TargetState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TargetViewModel : ViewModel() {

    private val _targetList = getInitialTargets().toMutableStateList()
    val targetList: List<TargetState> = _targetList

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onTargetClicked(target: TargetState) {
        val index = _targetList.indexOf(target)
        if (index == -1) return

        val updatedTarget = target.copy(isSelected = !target.isSelected)

        _targetList[index] = updatedTarget
    }

    fun onNextClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(Unit)
        }
    }

    private fun getInitialTargets(): List<TargetState> {
        return listOf(
            TargetState(
                title = "Ăn uống lành mạnh",
                description = "Ưu tiên rau, giảm mỡ, uống nhiều nước"
            ),
            TargetState(
                title = "Giảm cân",
                description = "Theo dõi calo, tập cardio"
            ),
            TargetState(
                title = "Tăng cơ / Tăng cân",
                description = "Ăn đủ protein, tập tạ"
            ),
            TargetState(
                title = "Giữ dáng / Duy trì sức khoẻ",
                description = "Tập luyện đều đặn, ăn uống khoa học"
            )
        )
    }
}