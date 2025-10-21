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

    // --- Biến điều hướng ---
    sealed class NavigationEvent {
        data object NavigateToNextScreen : NavigationEvent()
    }
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onNextClicked() {
        // Bạn có thể thêm logic kiểm tra lỗi ở đây, ví dụ:
        if (hoTen.isNotBlank() && gioiTinh.isNotBlank() && tuoi.isNotBlank() && chieuCao.isNotBlank() && canNang.isNotBlank()) {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.NavigateToNextScreen)
            }
        } else {
            // Xử lý lỗi: thông tin còn trống
            println("Vui lòng nhập đầy đủ thông tin")
        }
    }

    // --- Biến cho Họ tên ---
    var hoTen by mutableStateOf("")
        private set
    fun onHoTenChanged(newName: String) {
        hoTen = newName
    }

    // --- CÁC BIẾN MỚI ĐƯỢC THÊM ---

    // Biến cho Giới tính
    var gioiTinh by mutableStateOf("")
        private set
    fun onGioiTinhChanged(newGender: String) {
        gioiTinh = newGender
    }

    // Biến cho Tuổi
    var tuoi by mutableStateOf("")
        private set
    fun onTuoiChanged(newAge: String) {
        tuoi = newAge
    }

    // Biến cho Chiều cao
    var chieuCao by mutableStateOf("")
        private set
    fun onChieuCaoChanged(newHeight: String) {
        chieuCao = newHeight
    }

    // Biến cho Cân nặng
    var canNang by mutableStateOf("")
        private set
    fun onCanNangChanged(newWeight: String) {
        canNang = newWeight
    }
}