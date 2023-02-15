package com.rsudanta.agenda.presentation.splashscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rsudanta.agenda.navigation.DestinationScreen
import com.rsudanta.agenda.presentation.authentication.AuthenticationViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(vm: AuthenticationViewModel, navController: NavController) {
    val isSignedIn = vm.isSignedIn.collectAsState()
    LaunchedEffect(key1 = true) {
        delay(2000)
        if (isSignedIn.value) {
            navController.navigate(DestinationScreen.Home.route) {
                popUpTo(DestinationScreen.SplashScreen.route) { inclusive = true }
            }
        } else {
            navController.navigate(DestinationScreen.SignUp.route) {
                popUpTo(DestinationScreen.SplashScreen.route) { inclusive = true }
            }
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "AGENDA", fontSize = 30.sp)
        }
    }
}