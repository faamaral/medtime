package com.digitaldose.medtime.database.repositories

import com.digitaldose.medtime.database.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/03/2025
 */
class UserRepositoryImpl(private val firestoreDB: FirebaseFirestore) : UserRepository {


    override fun createUser(user: User): Task<Void>{
        val documentRef = firestoreDB.collection("users").document(user.id!!)
        return documentRef.set(user)
    }

    override fun updateUser(updatedUser: User): Task<Void> {
        val documentRef = firestoreDB.collection("users").document(updatedUser.id!!)
        return documentRef.set(updatedUser)
    }

    override fun getUserById(userId: String): Task<DocumentSnapshot> {
        val documentRef = firestoreDB.collection("users").document(userId)
        return documentRef.get()
    }
}