package com.rsudanta.agenda.presentation.authentication

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rsudanta.agenda.navigation.DestinationScreen
import com.rsudanta.agenda.presentation.component.LoadingProgressSpinner
import kotlin.math.sign

@Composable
fun SignInScreen(vm: AuthenticationViewModel, navController: NavController) {
    val focus = LocalFocusManager.current
    val signInState by vm.signInState.collectAsState()
    val isLoading = signInState.isLoading

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Sign In",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
            )
            OutlinedTextField(
                value = signInState.email,
                onValueChange = { vm.updateSignInEmail(it) },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
            )

            OutlinedTextField(
                value = signInState.password,
                onValueChange = { vm.updateSignInPassword(it) },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
            )
            Button(
                onClick = {
                    focus.clearFocus(force = true)
                    vm.signIn(navController)
                }, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "SIGN IN")
            }
            Text(text = "New here? Go to signup ->",
                color = androidx.compose.ui.graphics.Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(DestinationScreen.SignUp.route) {
                            popUpTo(DestinationScreen.SignUp.route)
                            launchSingleTop = true
                        }
                    })
        }
        if (isLoading) {
            LoadingProgressSpinner()
        }
    }
}