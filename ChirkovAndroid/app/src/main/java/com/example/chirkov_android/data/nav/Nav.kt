package com.example.myfirstapplication.nav

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Register : Screen("register")
    object SignIn : Screen("sign_in")
    object ForgotPassword : Screen("forgot_password")
    object OtpVerification : Screen("otp_verification/{email}") {
        fun route(email: String) = "otp_verification/$email"
    }
    object CreateNewPassword : Screen("create_new_password")
}