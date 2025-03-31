package com.digitaldose.medtime.database.repositories

import com.digitaldose.medtime.database.models.Medicamento
import com.digitaldose.medtime.utils.constants.Collections
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

/**
 * Classe com funções de CRUD para medicamentos no banco de dados Firebase Cloud Firestore.
 *
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 12/11/2024
 */
class MedicamentoRepositoryImpl(private val firestoreDB: FirebaseFirestore) :
    MedicamentoRepository {

    /**
     *  Cria um novo medicamento no banco de dados.
     *  @param Medicamento medicamento Objeto Medicamento a ser criado.
     *  @return Task<Void> Task que indica o resultado da operação.
     */
    override fun createMedicamento(medicamento: Medicamento): Task<Void> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS)
            .document(medicamento.id)
//        val med = medicamento.toMap()
        return documentRef.set(medicamento)
    }

    /**
     *  Atualiza um medicamento existente no banco de dados.
     *  @param String documentId Identificador do medicamento a ser atualizado.
     *  @param Medicamento medicamento Objeto Medicamento com os dados atualizados.
     *  @return Task<Void> Task que indica o resultado da operação.
     */
    override fun updateMedicamento(medicamento: Medicamento): Task<Void> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS).document(medicamento.id)
        return documentRef.update(medicamento.toMap())
    }

    /**
     *  Deleta um medicamento do banco de dados.
     *  @param String documentId Identificador do medicamento a ser deletado.
     *  @return Task<Void> Task que indica o resultado da operação.
     */
    override fun deleteMedicamento(documentId: String): Task<Void> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS).document(documentId)
        return documentRef.delete()
    }

    /**
     *  Obtém todos os medicamentos do banco de dados.
     *  @return Task<QuerySnapshot> Task que contém a lista de medicamentos.
     */
    override fun getMedicamentos(): Task<QuerySnapshot> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS)
        return documentRef.get()
    }

    /**
     *  Obtém todos os medicamentos do usuário.
     *  @return Task<QuerySnapshot> Task que contém a lista de medicamentos.
     */
    override fun getMedicamentosByUserId(userId: String): Task<QuerySnapshot> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS)
            .whereEqualTo("userId", userId)
        return documentRef.get()
    }

    override suspend fun getMedicamentoByUserIdWork(userId: String): List<Medicamento> {
        return try {
            val snapshot = Tasks.await(
                firestoreDB.collection(Collections.MEDICAMENTOS).whereEqualTo("userId", userId)
                    .get()
            )

            snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(Medicamento::class.java)
            }
        }
        catch (e: Exception) {
            emptyList()
        }
    }

    /**
     *  Obtém um medicamento específico do banco de dados.
     *  @param String documentId Identificador do medicamento a ser obtido.
     *  @return Task<DocumentSnapshot> Task que contém o medicamento.
     */
    override fun getMedicamento(documentId: String): Task<DocumentSnapshot> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS).document(documentId)
        return documentRef.get()
    }
}