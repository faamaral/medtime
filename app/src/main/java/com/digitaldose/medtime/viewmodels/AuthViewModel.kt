package com.digitaldose.medtime.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitaldose.medtime.database.models.User
import com.digitaldose.medtime.database.repositories.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/03/2025
 */
class AuthViewModel : ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth
    private val _authState = MutableLiveData<AuthState>()
    private val userRepository = UserRepository()
    val authState: MutableLiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus()
    {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, senha: String)
    {
        if (email.isBlank() || senha.isBlank()) {
            _authState.value = AuthState.Error("Email e senha não podem estar vazios")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authState.value = AuthState.Authenticated
                auth.currentUser?.uid

            } else {
                _authState.value = AuthState.Error(task.exception?.message ?: "Falha ao autenticar")
            }
        }
    }

    fun signup(user: User){
        if (user.email.isBlank() || user.password.isBlank()) {
            _authState.value = AuthState.Error("Email e senha não podem estar vazios")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authState.value = AuthState.Authenticated
                userRepository.createUser(User(
                    id = auth.currentUser?.uid,
                    name = user.name,
                    email = user.email,
                    password = user.password,
                ))
            } else {
                _authState.value = AuthState.Error(task.exception?.message ?: "Falha ao cadastrar")
            }
        }
    }

    fun logout()
    {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
}
