package com.example.myfirstapplication.nav

sealed class Screens(val route: String) {
    object Register : Screens("register")
    object SignIn : Screens("sign_in")
    object ForgotPassword : Screens("forgot_password")
}