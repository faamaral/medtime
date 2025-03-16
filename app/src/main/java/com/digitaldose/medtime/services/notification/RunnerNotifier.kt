package com.digitaldose.medtime.services.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.digitaldose.medtime.database.models.Medicamento

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 25/11/2024
 */
class RunnerNotifier(
    private val notificationManager: NotificationManager,
    private val context: Context
) : Notifier(notificationManager) {
    override val notificationChannelId: String
        get() = "runner_channel_id"
    override val notificationChannelName: String
        get() = "Runner Channel"
    override val notificationId: Int
        get() = 200

    override fun buildNotification(medicamento: String, dosagem: String, tipoDosagem: String): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage(medicamento, dosagem, tipoDosagem))
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm).build()
    }

    override fun getNotificationTitle(): String {
        return "Hora da medicação!"
    }

    override fun getNotificationMessage(medicamento: String, dosagem: String, tipoDosagem: String): String {
        return "Já tomou o medicamento ${medicamento}?\nTomar ${dosagem} ${tipoDosagem} de ${medicamento} agora!."
    }
}