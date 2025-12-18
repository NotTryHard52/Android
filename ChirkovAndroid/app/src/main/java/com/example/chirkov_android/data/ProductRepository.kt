package com.example.chirkov_android.data

import com.example.chirkov_android.data.module.Product
import com.example.chirkov_android.data.module.ProductDto
import com.example.chirkov_android.data.module.toDomain
import com.example.chirkov_android.data.service.SupabaseApi


class ProductRepository {

    private val api = RetrofitInstance.api

    suspend fun getProducts(): List<Product> =
        api.getProducts().map(ProductDto::toDomain)
}