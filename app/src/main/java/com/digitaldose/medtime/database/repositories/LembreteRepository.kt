package com.digitaldose.medtime.database.repositories

import androidx.lifecycle.LiveData
import com.digitaldose.medtime.database.models.Lembrete
import com.digitaldose.medtime.database.models.LembreteEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
interface LembreteRepository {
    fun getAllLembretes(): LiveData<MutableList<LembreteEntity>>
    fun getAllLembretesToday(): LiveData<MutableList<LembreteEntity>>
    fun getLembreteById(id: Int): LembreteEntity
    fun getLembretesByMedicamentoId(medicamentoId: String): List<LembreteEntity>
    suspend fun createLembrete(lembrete: LembreteEntity): Int
    suspend fun updateLembrete(lembrete: LembreteEntity)
    fun deleteLembrete(lembrete: LembreteEntity)
}