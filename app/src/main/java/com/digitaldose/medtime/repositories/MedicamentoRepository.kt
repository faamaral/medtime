package com.digitaldose.medtime.repositories

import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.utils.constants.Collections
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

/**
 * Classe com funções de CRUD para medicamentos no banco de dados Firebase Cloud Firestore.
 *
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 12/11/2024
 */
class MedicamentoRepository {
    // Instância do banco de dados Firebase Firestore
    private val firestoreDB = FirebaseFirestore.getInstance()

    /**
     *  Cria um novo medicamento no banco de dados.
     *  @param Medicamento medicamento Objeto Medicamento a ser criado.
     *  @return Task<Void> Task que indica o resultado da operação.
     */
    fun createMedicamento(medicamento: Medicamento): Task<Void> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS)
            .document(medicamento.id)
        return documentRef.set(medicamento.toMap())
    }

    /**
     *  Atualiza um medicamento existente no banco de dados.
     *  @param String documentId Identificador do medicamento a ser atualizado.
     *  @param Medicamento medicamento Objeto Medicamento com os dados atualizados.
     *  @return Task<Void> Task que indica o resultado da operação.
     */
    fun updateMedicamento(medicamento: Medicamento): Task<Void> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS).document(medicamento.id)
        return documentRef.update(medicamento.toMap())
    }

    /**
     *  Deleta um medicamento do banco de dados.
     *  @param String documentId Identificador do medicamento a ser deletado.
     *  @return Task<Void> Task que indica o resultado da operação.
     */
    fun deleteMedicamento(documentId: String): Task<Void> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS).document(documentId)
        return documentRef.delete()
    }

    /**
     *  Obtém todos os medicamentos do banco de dados.
     *  @return Task<QuerySnapshot> Task que contém a lista de medicamentos.
     */
    fun getMedicamentos(): Task<QuerySnapshot> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS)
        return documentRef.get()
    }

    /**
     *  Obtém um medicamento específico do banco de dados.
     *  @param String documentId Identificador do medicamento a ser obtido.
     *  @return Task<DocumentSnapshot> Task que contém o medicamento.
     */
    fun getMedicamento(documentId: String): Task<DocumentSnapshot> {
        val documentRef = firestoreDB.collection(Collections.MEDICAMENTOS).document(documentId)
        return documentRef.get()
    }
}