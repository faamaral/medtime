package com.digitaldose.medtime.di.modules

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */

val firebaseModule = module {
    single { provideFirestore() }
    single { provideFirebaseAuth() }
}

fun provideFirestore(): FirebaseFirestore {
    return FirebaseFirestore.getInstance()
}

fun provideFirebaseAuth(): FirebaseAuth {
    val auth: FirebaseAuth = Firebase.auth
    return auth
}