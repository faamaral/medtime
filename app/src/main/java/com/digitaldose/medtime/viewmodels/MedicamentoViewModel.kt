package com.digitaldose.medtime.viewmodels

import androidx.lifecycle.ViewModel
import com.digitaldose.medtime.models.Medicamento
import com.digitaldose.medtime.repositories.MedicamentoRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * ViewModel para a entidade Medicamento.
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 11/11/2024
 */

class MedicamentoViewModel : ViewModel() {
    val medicamentoRepository = MedicamentoRepository()

    fun salvarMedicamento(medicamento: Medicamento) {
            medicamentoRepository.createMedicamento(medicamento).addOnSuccessListener {
                // Todo sucesso
            } .addOnFailureListener {
                // Todo erro
            }
    }

    fun atualizarMedicamento(documentId: String, medicamento: Medicamento) {
        medicamentoRepository.updateMedicamento(documentId, medicamento).addOnSuccessListener {
            // Todo sucesso
        } .addOnFailureListener {
            // Todo erro
        }
    }

    fun deletarMedicamento(documentId: String) {
        medicamentoRepository.deleteMedicamento(documentId).addOnSuccessListener {
            // Todo sucesso
        } .addOnFailureListener {
            // Todo erro
        }
    }

    fun obterMedicamentos() {
        medicamentoRepository.getMedicamentos().addOnSuccessListener {
            // Todo sucesso
        } .addOnFailureListener {
            // Todo erro
        }
    }

    fun obterMedicamento(documentId: String) {
        medicamentoRepository.getMedicamento(documentId).addOnSuccessListener {
            // Todo sucesso
        } .addOnFailureListener {
            // Todo erro
        }
    }
}