package com.example.maping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    // Estado para saber si el usuario está logueado o cargando
    private val _userState = MutableStateFlow<UserState>(UserState.LoggedOut)
    val userState: StateFlow<UserState> = _userState

    // Función que recibe el resultado del pop-up de Google
    fun signInWithGoogle(task: Task<GoogleSignInAccount>) {
        viewModelScope.launch {
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                _userState.value = UserState.Error("Error en Google Sign-In: ${e.message}")
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        _userState.value = UserState.Loading
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
            _userState.value = UserState.Success // ¡Éxito!
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Error desconocido")
        }
    }
}

// Estados posibles de la autenticación
sealed class UserState {
    object LoggedOut : UserState()
    object Loading : UserState()
    object Success : UserState()
    data class Error(val message: String) : UserState()
}