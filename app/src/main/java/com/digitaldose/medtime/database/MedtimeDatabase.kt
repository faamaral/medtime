package com.digitaldose.medtime.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.digitaldose.medtime.database.dao.HorariosDao
import com.digitaldose.medtime.database.dao.MedicamentoDao
import com.digitaldose.medtime.database.models.HorariosEntity
import com.digitaldose.medtime.database.models.MedicamentoEntity

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 08/03/2025
 */

@Database(entities = [MedicamentoEntity::class, HorariosEntity::class], version = 1)
abstract class MedtimeDatabase : RoomDatabase() {
    abstract fun medicamentoDao(): MedicamentoDao
    abstract fun horariosDao(): HorariosDao

//    companion object {
//        private const val DATABASE_NAME: String = "medtime_database"
//
//        @Volatile
//        private var INSTANCE: MedtimeDatabase? = null
//
//        fun getInstance(context: Context): MedtimeDatabase = INSTANCE ?: synchronized(this) {
//            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//        }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(context.applicationContext, MedtimeDatabase::class.java, DATABASE_NAME)
//                .build()
//
//    }
}