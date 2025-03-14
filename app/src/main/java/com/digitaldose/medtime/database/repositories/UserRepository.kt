package com.digitaldose.medtime.database.repositories

import com.digitaldose.medtime.database.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/03/2025
 */
class UserRepository {
    private val firestoreDB = FirebaseFirestore.getInstance()

    fun createUser(user: User): Task<Void>{
        val documentRef = firestoreDB.collection("users").document(user.id!!)
        return documentRef.set(user)
    }

    fun updateUser(userId: String, updatedUser: User): Task<Void> {
        val documentRef = firestoreDB.collection("users").document(userId)
        return documentRef.set(updatedUser)
    }

    fun getUserById(userId: String): Task<DocumentSnapshot> {
        val documentRef = firestoreDB.collection("users").document(userId)
        return documentRef.get()
    }
}