package com.rsudanta.agenda.presentation.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    val auth: FirebaseAuth, val db: FirebaseFirestore
) : ViewModel() {

}