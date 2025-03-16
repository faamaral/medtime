package com.digitaldose.medtime.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digitaldose.medtime.database.models.MedicamentoEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 08/03/2025
 */
@Dao
interface MedicamentoDao {
    @Query("SELECT * FROM medicamentos ORDER BY id DESC")
    fun getAll(): Flow<List<MedicamentoEntity>>

    @Query("SELECT * FROM medicamentos WHERE id = :id")
    fun getById(id: String): MedicamentoEntity

    @Query("SELECT * FROM medicamentos WHERE nome LIKE :nome")
    fun getByNome(nome: String): List<MedicamentoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicamento: MedicamentoEntity)

    @Update
    fun update(medicamento: MedicamentoEntity)

    @Delete
    fun delete(medicamento: MedicamentoEntity)

}