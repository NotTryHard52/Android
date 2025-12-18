package com.example.chirkov_android.data

import com.example.chirkov_android.data.module.CategoryDto
import com.example.chirkov_android.data.module.ProductDto
import com.example.chirkov_android.data.service.SupabaseApi

class CatalogRepository(
    private val api: SupabaseApi = RetrofitInstance.api
) {
    suspend fun getCategories(): List<CategoryDto> = api.getCategories()

    suspend fun getProducts(categoryId: String?): List<ProductDto> {
        return if (categoryId == null) {
            api.getProducts()
        } else {
            api.getProductsByCategoryId(categoryIdEq = "eq.$categoryId")
        }
    }
}