package com.digitaldose.medtime.di.modules


import com.digitaldose.medtime.database.dao.LembreteDao
import com.digitaldose.medtime.database.repositories.LembreteRepository
import com.digitaldose.medtime.database.repositories.LembreteRepositoryImpl
import com.digitaldose.medtime.database.repositories.MedicamentoRepository
import com.digitaldose.medtime.database.repositories.MedicamentoRepositoryImpl
import com.digitaldose.medtime.database.repositories.UserRepository
import com.digitaldose.medtime.database.repositories.UserRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.scope.get
import org.koin.dsl.module

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */

val repositoriesModule = module {
    single<UserRepository> {
        provideUserRepository(get())
    }
    single<MedicamentoRepository> {
        provideMedicamentoRepository(get())
    }
    single<LembreteRepository> {
        provideLembreteRepository(get())
    }
}

fun provideUserRepository(firestoreDB: FirebaseFirestore): UserRepository {
    return UserRepositoryImpl(
        firestoreDB = firestoreDB
    )
}

fun provideMedicamentoRepository(firestoreDB: FirebaseFirestore): MedicamentoRepository {
    return MedicamentoRepositoryImpl(
        firestoreDB = firestoreDB
    )
}

fun provideLembreteRepository(lembreteDao: LembreteDao): LembreteRepository {
    return LembreteRepositoryImpl(
        lembreteDao = lembreteDao
    )
}

