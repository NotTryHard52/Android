package com.example.chirkov_android.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chirkov_android.data.RetrofitInstance
import com.example.chirkov_android.data.module.ForgotPasswordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _forgotState = MutableStateFlow<ForgotState>(ForgotState.Idle)
    val forgotState: StateFlow<ForgotState> = _forgotState.asStateFlow()

    fun sendRecoveryCode(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _forgotState.value = ForgotState.Idle
            try {
                val response = RetrofitInstance.userManagmentService.sendRecoveryCode(
                    ForgotPasswordRequest(email)
                )
                if (response.isSuccessful) {
                    _forgotState.value = ForgotState.Success
                } else {
                    _forgotState.value = ForgotState.Error("Ошибка сервера")
                }
            } catch (e: Exception) {
                _forgotState.value = ForgotState.Error("Нет интернета")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun showError(message: String) {
        _forgotState.value = ForgotState.Error(message)
    }
    fun resetState() {
        _forgotState.value = ForgotState.Idle
    }
}

sealed class ForgotState {
    object Idle : ForgotState()
    object Success : ForgotState()
    data class Error(val message: String) : ForgotState()
}