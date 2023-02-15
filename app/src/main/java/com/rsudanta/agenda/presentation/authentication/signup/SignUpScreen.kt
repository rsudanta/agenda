package com.rsudanta.agenda.presentation.authentication

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rsudanta.agenda.navigation.DestinationScreen
import com.rsudanta.agenda.presentation.component.LoadingProgressSpinner

@Composable
fun SignUpScreen(vm: AuthenticationViewModel, navController: NavController) {
    val signUpState by vm.signUpState.collectAsState()
    val focus = LocalFocusManager.current
    val isLoading = signUpState.isLoading

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
                text = "Signup",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
            )
            OutlinedTextField(
                value = signUpState.name,
                onValueChange = { vm.updateSignUpName(it) },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Name") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
            )
            OutlinedTextField(
                value = signUpState.email,
                onValueChange = { vm.updateSignUpEmail(it) },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) })
            )
            OutlinedTextField(
                value = signUpState.password,
                onValueChange = { vm.updateSignUpPassword(it) },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
            )
            Button(
                onClick = {
                    focus.clearFocus(force = true)
                    vm.signUp(navController)
                }, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "SIGN UP")
            }
            Text(text = "Already a user? Go to login ->",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(DestinationScreen.SignIn.route) {
                            popUpTo(DestinationScreen.SignIn.route)
                            launchSingleTop = true
                        }
                    })
        }
        if (isLoading) {
            LoadingProgressSpinner()
        }
    }
}