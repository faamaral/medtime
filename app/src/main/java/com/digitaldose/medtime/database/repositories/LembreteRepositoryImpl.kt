package com.digitaldose.medtime.database.repositories

import androidx.lifecycle.LiveData
import com.digitaldose.medtime.database.dao.LembreteDao
import com.digitaldose.medtime.database.models.Lembrete
import com.digitaldose.medtime.database.models.LembreteEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
class LembreteRepositoryImpl(private val lembreteDao: LembreteDao) : LembreteRepository {
    override fun getAllLembretes(): LiveData<MutableList<LembreteEntity>> {
        return lembreteDao.getAll()
    }

    override fun getAllLembretesToday(): LiveData<MutableList<LembreteEntity>> {
        return lembreteDao.getAllToday()
    }

    override fun getLembreteById(id: Int): LembreteEntity {
        return lembreteDao.getById(id)
    }

    override fun getLembretesByMedicamentoId(medicamentoId: String): List<LembreteEntity> {
        return lembreteDao.getByMedicamentoId(medicamentoId)
    }

    override suspend fun createLembrete(lembrete: LembreteEntity): Int = withContext(IO) {
        lembreteDao.insert(lembrete).toInt()
    }

    override suspend fun updateLembrete(lembrete: LembreteEntity) {
        lembreteDao.update(lembrete)
    }

    override fun deleteLembrete(lembrete: LembreteEntity) {
        lembreteDao.delete(lembrete)
    }
}