package com.digitaldose.medtime.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.digitaldose.medtime.database.models.HorariosEntity

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 09/03/2025
 */
@Dao
interface HorariosDao {

    @Insert
    fun insert(horarios: HorariosEntity)


}