package com.rsudanta.agenda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rsudanta.agenda.presentation.authentication.AuthenticationViewModel
import com.rsudanta.agenda.presentation.authentication.SignUpScreen

@Composable
fun SetupNavigation(
    navController: NavHostController, authenticationViewModel: AuthenticationViewModel
) {
    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route) {
        composable(DestinationScreen.SignUp.route) {
            SignUpScreen()
        }

    }
}