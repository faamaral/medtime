package com.digitaldose.medtime.database.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
data class Lembrete(
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val data: Long,
    val hora: Long,
    val acao: Boolean = false,
    val medicamentoId: String
)
