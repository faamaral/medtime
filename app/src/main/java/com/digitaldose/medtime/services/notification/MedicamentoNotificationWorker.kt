package com.digitaldose.medtime.services.notification

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 22/11/2024
 */
interface MedicamentoNotificationWorker {
    fun schedule(notification: Notification)
    fun cancel(notification: Notification)

}