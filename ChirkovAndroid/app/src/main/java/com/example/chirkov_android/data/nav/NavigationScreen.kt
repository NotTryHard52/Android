package com.example.chirkov_android.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.chirkov_android.ui.view.CreateNewPassword
import com.example.chirkov_android.ui.view.ForgotPassword
import com.example.chirkov_android.ui.view.HomeScreen
import com.example.chirkov_android.ui.view.OnboardScreen
import com.example.chirkov_android.ui.view.ProfileScreen
import com.example.chirkov_android.ui.view.RegisterAccount
import com.example.chirkov_android.ui.view.SignIn
import com.example.chirkov_android.ui.view.Verification


@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboard.route
    ) {
        composable(Screen.Onboard.route) {
            OnboardScreen(
                onStartClick = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Onboard.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterAccount(
                onSignInClick = { navController.navigate(Screen.SignIn.route) },
                onOtpClick = { email -> navController.navigate(Screen.OtpVerification.route(email)) }
            )
        }

        composable(Screen.SignIn.route) {
            SignIn(
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                onSuccessNavigate = {
                    navController.navigate(Screen.Home.route) {
                        // очищаем весь auth-флоу до старта графа [web:101]
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPassword(
                onBackClick = { navController.popBackStack() },
                onOtpClick = { email -> navController.navigate(Screen.OtpVerification.route(email)) }
            )
        }

        composable(
            route = Screen.OtpVerification.route,
            arguments = listOf(
                navArgument(Screen.OtpVerification.EMAIL_ARG) { type = NavType.StringType }
            )
        ) { entry ->
            val email = entry.arguments?.getString(Screen.OtpVerification.EMAIL_ARG).orEmpty()

            Verification(
                email = email,
                onBackClick = { navController.popBackStack() },
                onSuccess = { navController.navigate(Screen.CreateNewPassword.route) }
            )
        }

        composable(Screen.CreateNewPassword.route) {
            CreateNewPassword(
                onBackClick = { navController.popBackStack() },
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onTabSelected = { tabIndex ->
                    if (tabIndex == 3) { // 3 = Profile
                        navController.navigate(Screen.Profile.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onTabSelected = { tabIndex ->
                    if (tabIndex == 0) { // 0 = Home
                        navController.navigate(Screen.Home.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

