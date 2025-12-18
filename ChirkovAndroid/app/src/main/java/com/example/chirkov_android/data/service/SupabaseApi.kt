package com.example.chirkov_android.data.service

import com.example.chirkov_android.data.module.CategoryDto
import com.example.chirkov_android.data.module.ProductDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query



interface SupabaseApi {

    @Headers(
        "apikey: ${API_KEY}",
        "Authorization: Bearer ${API_KEY}"
    )
    @GET("rest/v1/categories")
    suspend fun getCategories(
        @Query("select") select: String = "id,title"
    ): List<CategoryDto>

    @Headers(
        "apikey: ${API_KEY}",
        "Authorization: Bearer ${API_KEY}"
    )
    @GET("rest/v1/products")
    suspend fun getProducts(
        @Query("select") select: String = "*"
    ): List<ProductDto>

    @Headers(
        "apikey: ${API_KEY}",
        "Authorization: Bearer ${API_KEY}"
    )
    @GET("rest/v1/products")
    suspend fun getProductsByCategoryId(
        @Query("select") select: String = "*",
        @Query("category_id") categoryIdEq: String
    ): List<ProductDto>
}