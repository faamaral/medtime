package com.digitaldose.medtime.services.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.digitaldose.medtime.database.models.Medicamento

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 25/11/2024
 */
abstract class Notifier(private val notificationManager: NotificationManager) {
    abstract val notificationChannelId: String
    abstract val notificationChannelName: String
    abstract val notificationId: Int

    fun showNotification(medicamento: String, dosagem: String, tipoDosagem: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel()
            notificationManager.createNotificationChannel(channel)
        }
        val notification = buildNotification(medicamento, dosagem, tipoDosagem)
        notificationManager.notify(notificationId, notification)
    }

    abstract fun buildNotification(medicamento: String, dosagem: String, tipoDosagem: String): Notification
    @RequiresApi(Build.VERSION_CODES.O)
    open fun createNotificationChannel(importance: Int = NotificationManager.IMPORTANCE_DEFAULT): NotificationChannel {
        return NotificationChannel(notificationChannelId, notificationChannelName, importance)
    }

    protected abstract fun getNotificationTitle(): String
    protected abstract fun getNotificationMessage(medicamento: String, dosagem: String, tipoDosagem: String): String
}