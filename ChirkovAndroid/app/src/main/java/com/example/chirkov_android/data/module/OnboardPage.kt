package com.example.chirkov_android.data.module

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val isFirst: Boolean = false
)
