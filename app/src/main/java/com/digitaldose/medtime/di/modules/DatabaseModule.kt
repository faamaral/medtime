package com.digitaldose.medtime.di.modules

import androidx.room.Room
import com.digitaldose.medtime.database.MedtimeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MedtimeDatabase::class.java,
            "medtime-db.db"
        ).build()
    }
    single {
        get<MedtimeDatabase>().medicamentoDao()
    }

    single {
        get<MedtimeDatabase>().horariosDao()
    }

    single {
        get<MedtimeDatabase>().lembreteDao()
    }

}