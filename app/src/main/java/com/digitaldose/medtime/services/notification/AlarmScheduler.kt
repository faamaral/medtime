package com.digitaldose.medtime.services.notification

import android.app.PendingIntent
import com.digitaldose.medtime.database.models.NotificationItem

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 25/11/2024
 */
interface AlarmScheduler {
    fun createPedingIntent(notificationItem: NotificationItem): PendingIntent
    fun schedule(notificationItem: NotificationItem)
    fun cancel(notificationItem: NotificationItem)
}