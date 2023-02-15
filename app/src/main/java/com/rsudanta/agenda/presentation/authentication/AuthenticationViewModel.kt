package com.rsudanta.agenda.presentation.authentication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rsudanta.agenda.model.User
import com.rsudanta.agenda.navigation.DestinationScreen
import com.rsudanta.agenda.presentation.authentication.signin.SignInState
import com.rsudanta.agenda.presentation.authentication.signup.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    val auth: FirebaseAuth, val db: FirebaseFirestore, application: Application
) : AndroidViewModel(application) {
    companion object {
        const val USERS = "users"
    }

    private val context
        get() = getApplication<Application>()

    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState>
        get() = _signUpState.asStateFlow()

    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState>
        get() = _signInState.asStateFlow()

    private val _isSignedIn = MutableStateFlow<Boolean>(false)
    val isSignedIn = _isSignedIn.asStateFlow()

    init {
        auth.signOut()
        val currentUser = auth.currentUser
        val isSignedIn = currentUser != null
        updateIsSignedIn(isSignedIn)
    }

    fun updateSignUpName(name: String) {
        _signUpState.update {
            it.copy(name = name)
        }
    }

    fun updateSignUpEmail(email: String) {
        _signUpState.update {
            it.copy(email = email)
        }
    }

    fun updateSignUpPassword(password: String) {
        _signUpState.update {
            it.copy(password = password)
        }
    }

    private fun updateSignUpIsLoading(isLoading: Boolean) {
        _signUpState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun updateSignInEmail(email: String) {
        _signInState.update {
            it.copy(email = email)
        }
    }

    fun updateSignInPassword(password: String) {
        _signInState.update {
            it.copy(password = password)
        }
    }

    private fun updateSignInIsLoading(isLoading: Boolean) {
        _signInState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun updateIsSignedIn(isSignedIn: Boolean) {
        _isSignedIn.update {
            isSignedIn
        }
    }

    fun signUp(navController: NavController) {
        updateSignUpIsLoading(isLoading = true)
        if (validateSignUpState()) {
            auth.createUserWithEmailAndPassword(signUpState.value.email, signUpState.value.password)
                .addOnCompleteListener { task ->
                    updateSignUpIsLoading(isLoading = false)
                    if (task.isSuccessful) {
                        updateIsSignedIn(isSignedIn = true)
                        storeUser()
                        navController.navigate(DestinationScreen.Home.route) {
                            popUpTo(DestinationScreen.SignUp.route) { inclusive = true }
                        }
                    } else {
                        handleException(task.exception, "Authentication failed")
                    }
                }
        } else {
            updateSignUpIsLoading(isLoading = false)
            handleException(message = "Please fill out the form")
        }
    }

    fun signIn(navController: NavController) {
        updateSignInIsLoading(isLoading = true)
        if (validateSignInState()) {
            auth.signInWithEmailAndPassword(signInState.value.email, signInState.value.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updateSignInIsLoading(isLoading = false)
                        updateIsSignedIn(isSignedIn = true)
                        navController.navigate(DestinationScreen.Home.route) {
                            popUpTo(DestinationScreen.SignUp.route) { inclusive = true }
                        }
                    } else {
                        updateSignInIsLoading(isLoading = false)
                        handleException(task.exception)
                    }
                }
        } else {
            updateSignInIsLoading(isLoading = false)
            handleException(message = "Please fill out the form")
        }
    }

    private fun validateSignUpState(): Boolean {
        val state = signUpState.value
        if (state.email.isEmpty() || state.name.isEmpty() || state.password.isEmpty()) {
            return false
        }
        return true
    }

    private fun validateSignInState(): Boolean {
        val state = signInState.value
        if (state.email.isEmpty() || state.password.isEmpty()) {
            return false
        }
        return true
    }

    private fun storeUser() {
        val uid = auth.currentUser?.uid
        val user = User(
            uid = uid!!, name = signUpState.value.name
        )
        db.collection(USERS).document(uid).set(user)
    }

    private fun handleException(exception: Exception? = null, message: String = "") {
        exception?.printStackTrace()
        val errorMessage: String? = exception?.localizedMessage
        val message = if (errorMessage.isNullOrEmpty()) message else errorMessage
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}