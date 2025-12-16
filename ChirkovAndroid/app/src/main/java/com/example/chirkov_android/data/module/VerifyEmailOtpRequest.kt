package com.example.chirkov_android.data.module

data class VerifyEmailOtpRequest(
    val type: String = "signup",   // для подтверждения регистрации
    val email: String,
    val token: String              // 6‑значный код из письма
)