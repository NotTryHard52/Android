package com.example.chirkov_android.data.module

data class ProfileDto(
    val id: String,
    val user_id: String,
    val photo: String?,
    val firstname: String?,
    val lastname: String?,
    val address: String?,
    val phone: String?
)

// PATCH body
data class ProfileUpdateDto(
    val photo: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val address: String? = null,
    val phone: String? = null
)

// POST body (минимум обязателен user_id; остальное можно null)
data class ProfileUpsertDto(
    val user_id: String,
    val photo: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val address: String? = null,
    val phone: String? = null
)
