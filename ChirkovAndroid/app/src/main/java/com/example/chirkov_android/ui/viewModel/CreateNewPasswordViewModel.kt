package com.example.chirkov_android.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chirkov_android.data.RetrofitInstance
import com.example.chirkov_android.data.module.UpdatePasswordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateNewPasswordViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow<String?>(null)
    val isError: StateFlow<String?> = _isError

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = null
            try {
                val response = RetrofitInstance.userManagmentService.updatePassword(
                    UpdatePasswordRequest(password = newPassword)
                )
                if (response.isSuccessful) {
                    _isSuccess.value = true
                } else {
                    _isError.value = "Ошибка сервера"
                }
            } catch (e: Exception) {
                _isError.value = "Нет соединения с интернетом"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _isSuccess.value = false
        _isError.value = null
    }
}