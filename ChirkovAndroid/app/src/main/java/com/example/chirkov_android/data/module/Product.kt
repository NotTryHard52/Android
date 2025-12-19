package com.example.chirkov_android.data.module

import com.example.chirkov_android.R
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

fun Product.toCardData(
    isFavorite: Boolean = false,
    isInCart: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
): ProductCardData =
    ProductCardData(
        id = id,
        imageRes = R.drawable.nike,
        label = if (isBestSeller) "BEST SELLER" else "",
        title = title,
        price = price,
        isFavorite = isFavorite,
        isInCart = isInCart,
        onFavoriteClick = onFavoriteClick,
        onAddClick = onAddClick
    )
