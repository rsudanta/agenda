package com.rsudanta.agenda.navigation

sealed class DestinationScreen(val route: String) {
    object SignUp : DestinationScreen("signup")
}