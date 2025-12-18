package com.example.chirkov_android.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chirkov_android.data.RetrofitInstance
import com.example.myfirstapplication.data.module.SignInRequest
import com.example.myfirstapplication.data.module.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

class SignInViewModel : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                val response = RetrofitInstance.userManagmentService.signIn(
                    SignInRequest(email = email, password = password)
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        saveAuthToken(body.access_token)
                        saveRefreshToken(body.refresh_token)
                        saveUserData(body.user)

                        Log.v("signIn", "User authenticated: ${body.user.email}")
                        _signInState.value = SignInState.Success
                    } else {
                        _signInState.value = SignInState.Error("Пустой ответ сервера")
                    }
                } else {
                    val errorMessage = parseSignInError(response.code(), response.message())
                    _signInState.value = SignInState.Error(errorMessage)
                    Log.e(
                        "signIn",
                        "Error code: ${response.code()}, message: ${response.message()}, body: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is ConnectException -> "No internet connection"
                    is SocketTimeoutException -> "Connection timeout"
                    is SSLHandshakeException -> "Security error"
                    else -> "Ошибка авторизации: ${e.message}"
                }
                _signInState.value = SignInState.Error(errorMessage)
                Log.e("SignInViewModel", "Exception: ${e.message}", e)
            }
        }
    }

    private fun parseSignInError(code: Int, message: String): String =
        when (code) {
            400 -> "Неверный логин или пароль"
            401 -> "Invalid login credentials"
            422 -> "Неверный формат почты"
            429 -> "Слишком много запросов"
            500 -> "Server error. Please try again later."
            else -> "Login failed: $message"
        }

    private fun saveAuthToken(token: String) {
        // TODO: сохранить в DataStore/Secure storage
        Log.d("Auth", "Access token saved: ${token.take(10)}...")
    }

    private fun saveRefreshToken(token: String) {
        // TODO: сохранить в DataStore/Secure storage
        Log.d("Auth", "Refresh token saved: ${token.take(10)}...")
    }

    private fun saveUserData(user: User) {
        // TODO: сохранить user
        Log.d("Auth", "User data saved: ${user.email}")
    }

    fun resetState() {
        _signInState.value = SignInState.Idle
    }
}

sealed class SignInState {
    data object Idle : SignInState()
    data object Loading : SignInState()
    data object Success : SignInState()
    data class Error(val message: String) : SignInState()
}
