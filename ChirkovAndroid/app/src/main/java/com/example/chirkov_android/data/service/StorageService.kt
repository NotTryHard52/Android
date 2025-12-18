package com.example.chirkov_android.data.service

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface StorageService {

    @POST("storage/v1/object/{bucket}/{path}")
    suspend fun upload(
        @Header("apikey") apiKey: String,
        @Header("Authorization") bearer: String,
        @Header("Content-Type") contentType: String,
        @Path("bucket") bucket: String,
        @Path(value = "path", encoded = true) path: String,
        @Body body: RequestBody
    ): Response<Unit>
}