package com.example.chirkov_android.data.module

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String? = null
)