package com.digitaldose.medtime.di

import com.digitaldose.medtime.di.modules.databaseModule
import com.digitaldose.medtime.di.modules.firebaseModule
import com.digitaldose.medtime.di.modules.repositoriesModule
import com.digitaldose.medtime.di.modules.viewModelsModule
import com.digitaldose.medtime.di.modules.workerModule

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */

val appModules = listOf(
    firebaseModule,
    repositoriesModule,
    viewModelsModule,
    databaseModule,
    workerModule
)