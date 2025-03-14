package com.digitaldose.medtime.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 08/03/2025
 */


@Entity(tableName = "medicamentos")
data class MedicamentoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "descricao") val descricao: String,
    @ColumnInfo(name = "dosagem") val dosagem: String,
    @ColumnInfo(name = "tipoDosagem") val tipoDosagem: String,
    @ColumnInfo(name = "frequencia") val frequencia: String,
    @ColumnInfo(name = "intervalo") val intervalo: Int,
//    @ColumnInfo(name = "horario") val horario: List<String>,
//    @TypeConverters(MedicamentoLocalConverter::class)
//    val horario: List<String>,
    @ColumnInfo(name = "dataCriacao") val dataCriacao: Long,
    @ColumnInfo(name = "dataAtualizacao") val dataAtualizacao: Long
)

@Entity(
    tableName = "horarios", foreignKeys = [
        ForeignKey(
            entity = MedicamentoEntity::class,
            parentColumns = ["id"],
            childColumns = ["medicamentoId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class HorariosEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "horario") val horario: String,
    @ColumnInfo(name = "medicamentoId") val medicamentoId: String,
    @ColumnInfo(name = "dataCriacao") val dataCriacao: Long,
    @ColumnInfo(name = "dataAtualizacao") val dataAtualizacao: Long
)