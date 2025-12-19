package com.example.chirkov_android.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.chirkov_android.data.AuthStore
import com.example.chirkov_android.ui.view.CatalogScreen
import com.example.chirkov_android.ui.view.CreateNewPassword
import com.example.chirkov_android.ui.view.FavoriteScreen
import com.example.chirkov_android.ui.view.ForgotPassword
import com.example.chirkov_android.ui.view.HomeScreen
import com.example.chirkov_android.ui.view.OnboardScreen
import com.example.chirkov_android.ui.view.ProfileScreen
import com.example.chirkov_android.ui.view.RegisterAccount
import com.example.chirkov_android.ui.view.SignIn
import com.example.chirkov_android.ui.view.Verification
import com.example.chirkov_android.ui.viewModel.CatalogViewModel
import com.example.chirkov_android.ui.viewModel.CatalogViewModelFactory

@Composable
fun NavigationScreen(navController: NavHostController) {
    val context = LocalContext.current
    val authStore = remember { AuthStore(context) }

    val catalogViewModel: CatalogViewModel = viewModel(
        factory = CatalogViewModelFactory(authStore)
    )

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
                onOtpClick = { email ->
                    navController.navigate(Screen.OtpVerification.route(email))
                }
            )
        }

        composable(Screen.SignIn.route) {
            SignIn(
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                onSuccessNavigate = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPassword(
                onBackClick = { navController.popBackStack() },
                onOtpClick = { email ->
                    navController.navigate(Screen.OtpVerification.route(email))
                }
            )
        }

        composable(
            route = Screen.OtpVerification.route,
            arguments = listOf(
                navArgument(Screen.OtpVerification.EMAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val email = entry.arguments
                ?.getString(Screen.OtpVerification.EMAIL_ARG)
                .orEmpty()

            Verification(
                email = email,
                onBackClick = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Screen.CreateNewPassword.route)
                }
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
                    when (tabIndex) {
                        1 -> { // Favorite
                            navController.navigate(Screen.Favorite.route) {
                                launchSingleTop = true
                            }
                        }
                        3 -> { // Profile
                            navController.navigate(Screen.Profile.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                },
                onOpenCatalog = { title ->
                    navController.navigate(Screen.Catalog.route(title)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onTabSelected = { tabIndex ->
                    if (tabIndex == 0) { // Home
                        navController.navigate(Screen.Home.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.Catalog.route,
            arguments = listOf(
                navArgument(Screen.Catalog.TITLE_ARG) {
                    type = NavType.StringType
                    defaultValue = "Все"
                }
            )
        ) { entry ->
            val title = entry.arguments
                ?.getString(Screen.Catalog.TITLE_ARG)
                ?.let { android.net.Uri.decode(it) }
                ?: "Все"

            CatalogScreen(
                viewModel = catalogViewModel,
                initialCategoryTitle = title,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                viewModel = catalogViewModel,
                authStore = authStore,
                onBackClick = { navController.popBackStack() },
                onTabSelected = { tabIndex ->
                    when (tabIndex) {
                        0 -> navController.navigate(Screen.Home.route) { launchSingleTop = true }
                        3 -> navController.navigate(Screen.Profile.route) { launchSingleTop = true }
                    }
                }
            )
        }
    }
}

