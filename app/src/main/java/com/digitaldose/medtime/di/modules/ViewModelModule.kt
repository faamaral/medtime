package com.digitaldose.medtime.di.modules

import com.digitaldose.medtime.database.repositories.LembreteRepository
import com.digitaldose.medtime.database.repositories.MedicamentoRepository
import com.digitaldose.medtime.database.repositories.UserRepository
import com.digitaldose.medtime.viewmodels.AuthViewModel
import com.digitaldose.medtime.viewmodels.LembreteViewModel
import com.digitaldose.medtime.viewmodels.MedicamentoViewModel
import com.digitaldose.medtime.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */

val viewModelsModule = module {
    viewModel {
        provideUserViewModel(get())
    }

    viewModel {
        provideAuthViewModel(get(), get())
    }

    viewModel {
        provideMedicamentoViewModel(get(), get())
    }

    viewModel {
        provideLembreteViewModel(get())
    }
}

fun provideUserViewModel(userRepository: UserRepository): UserViewModel {
    return UserViewModel(userRepository = userRepository)
}

fun provideAuthViewModel(auth: FirebaseAuth, userRepository: UserRepository): AuthViewModel {
    return AuthViewModel(auth = auth, userRepository = userRepository)
}

fun provideMedicamentoViewModel(
    medicamentoRepository: MedicamentoRepository,
    lembreteRepository: LembreteRepository
): MedicamentoViewModel {
    return MedicamentoViewModel(
        medicamentoRepository = medicamentoRepository,
        lembreteRepository = lembreteRepository
    )
}

fun provideLembreteViewModel(lembreteRepository: LembreteRepository): LembreteViewModel {
    return LembreteViewModel(lembreteRepository)
}