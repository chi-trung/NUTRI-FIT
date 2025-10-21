package com.example.nutrifit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    var hoTen by mutableStateOf("")
        private set

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onHoTenChanged(newName: String) {
        hoTen = newName
    }

    fun onNextClicked() {
        if (hoTen.isNotBlank()) {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.NavigateToNextScreen)
            }
        } else {
            // Xử lý lỗi: họ tên trống
            println(
                "Vui lòng nhập họ tên")
        }
    }

    sealed class NavigationEvent {
        data object NavigateToNextScreen : NavigationEvent()
    }
}