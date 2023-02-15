package com.rsudanta.agenda.presentation.authentication.signup

data class SignUpState(
    var name: String = "",
    var email: String = "",
    val password: String = "",
    val isLoading:Boolean = false
)
