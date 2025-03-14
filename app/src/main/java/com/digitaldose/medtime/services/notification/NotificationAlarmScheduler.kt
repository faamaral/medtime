package com.digitaldose.medtime.services.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.digitaldose.medtime.database.models.Medicamento
import com.digitaldose.medtime.database.models.NotificationItem
import com.digitaldose.medtime.services.receiver.AlarmReceiver

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 25/11/2024
 */
class NotificationAlarmScheduler(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun createPedingIntent(notificationItem: NotificationItem): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("medicamento", notificationItem.medicamento.nome)
            putExtra("dosagem", notificationItem.medicamento.dosagem)
            putExtra("tipoDosagem", notificationItem.medicamento.tipoDosagem)
        }
        return PendingIntent.getBroadcast(
            context,
            notificationItem.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule(notificationItem: NotificationItem) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            notificationItem.time,
            AlarmManager.INTERVAL_DAY,
            createPedingIntent(notificationItem)
        )
    }

    override fun cancel(notificationItem: NotificationItem) {
        alarmManager.cancel(createPedingIntent(notificationItem))
    }

}