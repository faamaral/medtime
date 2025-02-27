package com.digitaldose.medtime.services.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

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

    override fun buildNotification(): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(android.R.drawable.btn_star).build()
    }

    override fun getNotificationTitle(): String {
        return "Runner Started"
    }

    override fun getNotificationMessage(): String {
        return "Runner has started"
    }
}