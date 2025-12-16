package com.example.myfirstapplication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chirkov_android.ui.view.ForgotPassword
import com.example.chirkov_android.ui.view.RegisterAccount
import com.example.chirkov_android.ui.view.SignIn
import com.example.chirkov_android.ui.view.Verification


@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "register"
    ) {
        composable("register") {
            RegisterAccount(
                onSignInClick = { navController.navigate("sign_in") },
                onOtpClick = { email ->
                    navController.navigate("otp_verification/$email")
                }
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
                onBackClick = { navController.popBackStack() },
                onOtpClick = { email ->
                    navController.navigate("otp_verification/$email")
                }
            )
        }

        composable("otp_verification/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

            Verification(
                email = email,
                onBackClick = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate("create_new_password")
                }
            )
        }
    }
}

