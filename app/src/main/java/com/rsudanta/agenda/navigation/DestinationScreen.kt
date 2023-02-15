package com.rsudanta.agenda.navigation

sealed class DestinationScreen(val route: String) {
    object SplashScreen : DestinationScreen("splashscreen")
    object SignUp : DestinationScreen("signup")
    object SignIn : DestinationScreen("signin")
    object Home : DestinationScreen("home")
}