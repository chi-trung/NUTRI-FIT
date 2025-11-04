package com.example.nutrifit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    // State for settings
    private val _language = MutableStateFlow("Tiếng Việt")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _units = MutableStateFlow("kg / cm")
    val units: StateFlow<String> = _units.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    private val _twoFactorAuth = MutableStateFlow(false)
    val twoFactorAuth: StateFlow<Boolean> = _twoFactorAuth.asStateFlow()

    // Loading and error states
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadSettings()
    }

    fun loadSettings() {
        _isLoading.value = true
        val settings = repository.getSettings()
        _language.value = settings["language"] as String
        _units.value = settings["units"] as String
        _notificationsEnabled.value = settings["notifications"] as Boolean
        _twoFactorAuth.value = settings["twoFactor"] as Boolean
        _isLoading.value = false
    }

    fun updateLanguage(newLanguage: String) {
        _language.value = newLanguage
        saveSettings()
    }

    fun updateUnits(newUnits: String) {
        _units.value = newUnits
        saveSettings()
    }

    fun updateNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        saveSettings()
    }

    fun updateTwoFactor(enabled: Boolean) {
        _twoFactorAuth.value = enabled
        saveSettings()
    }

    private fun saveSettings() {
        viewModelScope.launch {
            try {
                repository.saveSettings(
                    _language.value,
                    _units.value,
                    _notificationsEnabled.value,
                    _twoFactorAuth.value
                )
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi lưu cài đặt: ${e.message}"
            }
        }
    }

    fun saveUserInfo(fullName: String, email: String, phone: String) {
        // This could be extended to save additional user info if needed
        // For now, it's a placeholder for the onSaveChanges callback in SettingScreen
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Assuming we extend repository to save email/phone
                // repository.saveAdditionalUserInfo(fullName, email, phone)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi lưu thông tin: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        // Placeholder for password change logic
        if (newPassword.length < 6) {
            _errorMessage.value = "Mật khẩu phải có ít nhất 6 ký tự"
            return
        }
        // Implement actual password change
        _errorMessage.value = "Tính năng đổi mật khẩu chưa được implement"
    }

    fun deleteAccount() {
        // Placeholder for account deletion
        _errorMessage.value = "Tính năng xóa tài khoản chưa được implement"
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
