package com.example.myfirstapplication.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.widget.Toast
import com.example.chirkov_android.data.RetrofitInstance
import com.example.chirkov_android.data.module.SignUpRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class SignUpViewModel : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun signUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.userManagmentService.signUp(signUpRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.v("signUp", "User id: ${it.id}")
                        _signUpState.value = SignUpState.Success
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "Неверный логин или пароль"
                        422 -> "Неверный формат email"
                        429 -> "Слишком много запросов"
                        else -> "Registration failed: ${response.message()}"
                    }
                    _signUpState.value = SignUpState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.ConnectException -> "No internet connection"
                    is java.net.SocketTimeoutException -> "Connection timeout"
                    else -> "Network error: ${e.message}"
                }
                _signUpState.value = SignUpState.Error(errorMessage)
                Log.e("SignUpViewModel", e.message.toString())
            }
        }
    }

    fun resetState() {
        _signUpState.value = SignUpState.Idle
    }
}

sealed class SignUpState {
    object Idle : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}