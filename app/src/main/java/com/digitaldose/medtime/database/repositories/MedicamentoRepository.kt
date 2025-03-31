package com.digitaldose.medtime.database.repositories

import com.digitaldose.medtime.database.models.Medicamento
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
interface MedicamentoRepository {
    fun createMedicamento(medicamento: Medicamento): Task<Void>
    fun updateMedicamento(medicamento: Medicamento): Task<Void>
    fun deleteMedicamento(documentId: String): Task<Void>
    fun getMedicamentos(): Task<QuerySnapshot>
    fun getMedicamento(documentId: String): Task<DocumentSnapshot>
    fun getMedicamentosByUserId(userId: String): Task<QuerySnapshot>
    suspend fun getMedicamentoByUserIdWork(userId: String): List<Medicamento>
}