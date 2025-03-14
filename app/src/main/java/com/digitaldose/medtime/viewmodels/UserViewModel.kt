package com.digitaldose.medtime.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitaldose.medtime.database.models.User
import com.digitaldose.medtime.database.repositories.UserRepository

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 12/03/2025
 */
class UserViewModel: ViewModel() {
    private val userRepository = UserRepository()
    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: MutableLiveData<User> = _userLiveData

    fun getUser(userId: String): LiveData<User> {
        userRepository.getUserById(userId).addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                try {
                    val user = User(
                        id = document.id.toString(),
                        name = document.data?.get("name").toString() ?: "",
                        email = document.data?.get("email").toString() ?: "",
                        password = document.data?.get("password").toString(),
                        photoUrl = document.data?.get("photoUrl").toString(),
                        sexo = document.data?.get("sexo").toString(),
                        dataNascimento = document.data?.get("dataNascimento").toString(),
                        altura = (document.data?.get("altura") as Long?)?.toInt(),
                        peso = document.data?.get("peso") as Double?,
                        createdAt = document.data?.get("createdAt") as Long?,
                        updatedAt = document.data?.get("updatedAt") as Long?,
                        ativo = document.data?.get("ativo") as Boolean ?: true,
                    )
                    _userLiveData.value = user
                }
                catch (e: Exception) {
                    Log.e("UserViewModel", "Erro ao converter usuário", e)
                }

            }
            else {
                Log.e("UserViewModel", "Usuário não encontrado")
            }

        }.addOnFailureListener{
            Log.e("UserViewModel", "Erro ao obter usuário", it)
        }
        return userLiveData
    }
}

sealed class UserState {

}
