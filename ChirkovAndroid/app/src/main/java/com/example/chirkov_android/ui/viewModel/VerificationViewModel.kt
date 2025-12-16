package com.example.chirkov_android.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chirkov_android.data.RetrofitInstance
import com.example.chirkov_android.data.module.VerifyEmailOtpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VerificationViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    fun verifyCode(email: String, code: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val response = RetrofitInstance.userManagmentService.verifyEmailOtp(
                    VerifyEmailOtpRequest(
                        type = "signup",
                        email = email,
                        token = code
                    )
                )
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    _isError.value = true
                }
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetError() {
        _isError.value = false
    }
}