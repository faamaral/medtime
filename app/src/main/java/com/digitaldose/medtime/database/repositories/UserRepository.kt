package com.digitaldose.medtime.database.repositories

import com.digitaldose.medtime.database.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
interface UserRepository {

    fun createUser(user: User): Task<Void>
    fun updateUser(updatedUser: User): Task<Void>
    fun getUserById(userId: String): Task<DocumentSnapshot>

}