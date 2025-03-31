package com.digitaldose.medtime.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
@Entity(tableName = "lembretes")
data class LembreteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name = "descricao")
    val descricao: String,
    @ColumnInfo(name = "data")
    val data: Long,
    @ColumnInfo(name = "hora")
    val hora: Long,
    @ColumnInfo(name = "acao")
    val acao: Boolean = false,
    @ColumnInfo(name = "medicamentoId")
    val medicamentoId: String
)

enum class Acao {
    Tomou,
    NaoTomou,
    None
}
