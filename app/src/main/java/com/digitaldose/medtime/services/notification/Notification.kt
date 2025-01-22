package com.digitaldose.medtime.services.notification

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 22/11/2024
 */
data class Notification(
    val title: String,
    val message: String,
    val timeInMillis: Long
)
