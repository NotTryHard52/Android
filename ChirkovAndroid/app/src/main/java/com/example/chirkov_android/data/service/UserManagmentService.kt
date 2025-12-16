package com.example.chirkov_android.data.service

import com.example.chirkov_android.data.module.ForgotPasswordRequest
import com.example.chirkov_android.data.module.SignUpRequest
import com.example.chirkov_android.data.module.SignUpResponse
import com.example.chirkov_android.data.module.UpdatePasswordRequest
import com.example.chirkov_android.data.module.VerifyEmailOtpRequest
import com.example.myfirstapplication.data.module.SignInRequest
import com.example.myfirstapplication.data.module.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJxZndwbHBveWZreGVxZGxvcm1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkyNjA4MjcsImV4cCI6MjA3NDgzNjgyN30.dNEp56agaeg_IKeMGjmwg7mU8F5VxNrHQbwRj1eo0nA"

interface UserManagmentService {
    @Headers(
        "apikey: ${API_KEY}",
        "Content-Type: application/json"
    )
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
    @Headers(
        "apikey: ${API_KEY}",
        "Content-Type: application/json"
    )
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @Headers(
        "apikey: ${API_KEY}",
        "Content-Type: application/json"
    )
    @POST("auth/v1/recover")
    suspend fun sendRecoveryCode(@Body request: ForgotPasswordRequest): Response<Void>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/verify")
    suspend fun verifyEmailOtp(
        @Body body: VerifyEmailOtpRequest
    ): Response<Void>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/user")
    suspend fun updatePassword(
        @Body body: UpdatePasswordRequest
    ): Response<Void>
}