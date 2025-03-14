package com.digitaldose.medtime.database.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.util.nextAlphanumericString
import kotlin.random.Random
import kotlin.random.nextUInt

/**
 * Classe que representa um medicamento.
 *
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 12/11/2024
 */
data class Medicamento(
    var id: String = Companion.generateDocumentId(),
    var nome: String? = null,
    var descricao: String? = null,
    var dosagem: String? = null,
    var tipoDosagem: String? = null,
    var frequencia: String? = null,
    var intervalo: Int? = null,
    var horario: List<String>? = emptyList(),
    var userId: String? = null
) {
   companion object {
        fun generateDocumentId(): String {
            return "${Random.nextUInt()}${Random.nextAlphanumericString(5)}${Random.nextInt()}"
        }

    }
    @Exclude
    fun toMap(): Map<String, Any?> {
        return hashMapOf(
            "id" to id,
            "nome" to nome,
            "descricao" to descricao,
            "dosagem" to dosagem,
            "tipoDosagem" to tipoDosagem,
            "frequencia" to frequencia,
            "intervalo" to intervalo,
            "horario" to horario,
            "userId" to userId
        )
    }

    @Exclude
    fun generateDocumentId(): String {
        return "${nome}${Random.nextInt()}"
    }
}
