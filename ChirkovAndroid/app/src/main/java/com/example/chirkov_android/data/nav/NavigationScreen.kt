package com.example.myfirstapplication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chirkov_android.ui.view.ForgotPassword
import com.example.chirkov_android.ui.view.RegisterAccount
import com.example.chirkov_android.ui.view.SignIn


@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "register"
    ) {
        composable("register") {
            RegisterAccount(
                onSignInClick = { navController.navigate("sign_in") },
                onOtpClick = { navController.navigate("otp_verification") }
            )
        }
        composable("sign_in") {
            SignIn(
                onRegisterClick = { navController.navigate("register") },
                onForgotPasswordClick = { navController.navigate("forgot_password") }
            )
        }
        composable("forgot_password") {
            ForgotPassword(
                onBackClick = { navController.popBackStack() } // возврат
            )
        }
        composable("forgot_password") {
            ForgotPassword(
                onBackClick = { navController.popBackStack() },
                onOtpClick = { navController.navigate("otp_verification") } // ✅ переход
            )
        }
    }
}

