package com.rsudanta.agenda.presentation.authentication.signin

data class SignInState(
    var email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
