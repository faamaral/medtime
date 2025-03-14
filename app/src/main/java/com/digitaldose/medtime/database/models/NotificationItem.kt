package com.digitaldose.medtime.database.models

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 25/11/2024
 */
data class NotificationItem(
    val time: Long,
    val id: Int,
    val medicamento: Medicamento
)
