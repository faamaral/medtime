package com.digitaldose.medtime.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlin.random.Random

/**
 * Classe que representa um medicamento.
 *
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 12/11/2024
 */
data class Medicamento(
    val nome: String? = null,
    val descricao: String? = null,
    val dosagem: String? = null,
    val frequencia: String? = null,
    val horario: List<String>? = emptyList()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return hashMapOf(
            "nome" to nome,
            "descricao" to descricao,
            "dosagem" to dosagem,
            "frequencia" to frequencia,
            "horario" to horario
        )
    }

    @Exclude
    fun generateDocumentId(): String {
        return "${nome}${Random.nextInt()}"
    }
}
