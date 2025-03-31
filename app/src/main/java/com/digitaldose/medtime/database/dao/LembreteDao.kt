package com.digitaldose.medtime.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digitaldose.medtime.database.models.LembreteEntity

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
@Dao
interface LembreteDao {

    @Query("SELECT * FROM lembretes ORDER BY data DESC")
    fun getAll(): LiveData<MutableList<LembreteEntity>>

    @Query("SELECT * FROM lembretes WHERE DATE(data / 1000, 'unixepoch') = DATE('now') ORDER BY hora DESC")
    fun getAllToday(): LiveData<MutableList<LembreteEntity>>

    @Query("SELECT * FROM lembretes WHERE id = :id")
    fun getById(id: Int): LembreteEntity

    @Query("SELECT * FROM lembretes WHERE medicamentoId = :medicamentoId")
    fun getByMedicamentoId(medicamentoId: String): List<LembreteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lembrete: LembreteEntity): Long

    @Update
    suspend fun update(lembrete: LembreteEntity)

    @Delete
    fun delete(lembrete: LembreteEntity)


}