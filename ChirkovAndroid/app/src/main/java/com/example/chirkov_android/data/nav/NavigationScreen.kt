package com.example.myfirstapplication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chirkov_android.ui.view.CreateNewPassword
import com.example.chirkov_android.ui.view.ForgotPassword
import com.example.chirkov_android.ui.view.OnboardScreen
import com.example.chirkov_android.ui.view.RegisterAccount
import com.example.chirkov_android.ui.view.SignIn
import com.example.chirkov_android.ui.view.Verification


@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "onboard"
    ) {
        // Регистрация
        composable(Screen.Register.route) {
            RegisterAccount(
                onSignInClick = { navController.navigate(Screen.SignIn.route) },
                onOtpClick = { email ->
                    navController.navigate(Screen.OtpVerification.route(email))
                }
            )
        }

        // Вход
        composable(Screen.SignIn.route) {
            SignIn(
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }

        // Забыл пароль
        composable(Screen.ForgotPassword.route) {
            ForgotPassword(
                onBackClick = { navController.popBackStack() },
                onOtpClick = { email ->
                    navController.navigate(Screen.OtpVerification.route(email))
                }
            )
        }

        // Ввод кода (OTP)
        composable(Screen.OtpVerification.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

            Verification(
                email = email,
                onBackClick = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Screen.CreateNewPassword.route)
                }
            )
        }

        // Новый пароль
        composable(Screen.CreateNewPassword.route) {
            CreateNewPassword(
                onBackClick = { navController.popBackStack() },
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Register.route) { inclusive = false }
                    }
                }
            )
        }
        composable("onboard") {
            OnboardScreen(
                onStartClick = {
                    // здесь потом перейдёшь, например, на sign_in
                    // navController.navigate("sign_in")
                }
            )
        }
    }
}

