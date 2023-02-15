package com.rsudanta.agenda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rsudanta.agenda.presentation.authentication.AuthenticationViewModel
import com.rsudanta.agenda.presentation.authentication.SignInScreen
import com.rsudanta.agenda.presentation.authentication.SignUpScreen
import com.rsudanta.agenda.presentation.home.HomeScreen
import com.rsudanta.agenda.presentation.splashscreen.SplashScreen

@Composable
fun SetupNavigation(
    navController: NavHostController, authenticationViewModel: AuthenticationViewModel
) {
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreen.route
    ) {
        composable(DestinationScreen.SplashScreen.route) {
            SplashScreen(vm = authenticationViewModel, navController = navController)
        }
        composable(DestinationScreen.SignUp.route) {
            SignUpScreen(vm = authenticationViewModel, navController = navController)
        }
        composable(DestinationScreen.SignIn.route) {
            SignInScreen(vm = authenticationViewModel, navController = navController)
        }
        composable(DestinationScreen.Home.route) {
            HomeScreen()
        }


    }
}