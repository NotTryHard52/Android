package com.example.chirkov_android.nav

import android.net.Uri

sealed class Screen(val route: String) {
    object Onboard : Screen("onboard")
    object Register : Screen("register")
    object SignIn : Screen("sign_in")
    object ForgotPassword : Screen("forgot_password")

    object OtpVerification : Screen("otp_verification/{email}") {
        const val EMAIL_ARG = "email"
        fun route(email: String) = "otp_verification/$email"
    }

    object CreateNewPassword : Screen("create_new_password")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Catalog : Screen("catalog?title={title}") {
        const val TITLE_ARG = "title"
        fun route(title: String): String = "catalog?title=${android.net.Uri.encode(title)}"
    }
    object Favorite : Screen("favorite")
}