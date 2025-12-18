package com.example.chirkov_android.data.module

import com.example.chirkov_android.R
import com.example.chirkov_android.data.module.ProductDto
import com.example.chirkov_android.ui.components.ProductCardData

data class Product(
    val id: String,
    val title: String,
    val price: String,
    val isBestSeller: Boolean
)
fun ProductDto.toDomain(): Product =
    Product(
        id = id,
        title = title,
        price = "₽$cost.00",
        isBestSeller = isBestSeller
    )

fun Product.toCardData(): ProductCardData =
    ProductCardData(
        imageRes = R.drawable.nike,          // пока заглушка, если нет поля image
        label = if (isBestSeller) "BEST SELLER" else "",
        title = title,
        price = price
    )
