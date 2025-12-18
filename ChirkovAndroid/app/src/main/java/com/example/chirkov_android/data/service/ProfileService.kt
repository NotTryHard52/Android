package com.example.chirkov_android.data.service

import com.example.chirkov_android.data.module.ProfileDto
import com.example.chirkov_android.data.module.ProfileUpdateDto
import com.example.chirkov_android.data.module.ProfileUpsertDto
import retrofit2.Response
import retrofit2.http.*

interface ProfileService {

    // GET /rest/v1/profiles?user_id=eq.<uuid>&select=*
    @GET("rest/v1/profiles")
    suspend fun getProfileByUserId(
        @Header("apikey") apiKey: String,
        @Header("Authorization") bearer: String,
        @Query("user_id") userIdEq: String,
        @Query("select") select: String = "*"
    ): Response<List<ProfileDto>>

    // PATCH /rest/v1/profiles?user_id=eq.<uuid>
    @PATCH("rest/v1/profiles")
    @Headers(
        "Content-Type: application/json",
        "Prefer: return=representation"
    )
    suspend fun updateProfileByUserId(
        @Header("apikey") apiKey: String,
        @Header("Authorization") bearer: String,
        @Query("user_id") userIdEq: String,
        @Body body: ProfileUpdateDto
    ): Response<List<ProfileDto>>

    // POST /rest/v1/profiles  (создать строку, если её нет)
    @POST("rest/v1/profiles")
    @Headers(
        "Content-Type: application/json",
        "Prefer: return=representation"
    )
    suspend fun createProfile(
        @Header("apikey") apiKey: String,
        @Header("Authorization") bearer: String,
        @Body body: ProfileUpsertDto
    ): Response<List<ProfileDto>>
}