package com.digitaldose.medtime.services.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.ProvidableCompositionLocal
import com.digitaldose.medtime.services.receiver.MedicamentoNotificationReceiver

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 21/11/2024
 */

class MedicamentoNotification(private val context: Context) : MedicamentoNotificationWorker {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(notification: Notification) {

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notification.hashCode(),
                Intent(context, MedicamentoNotificationReceiver::class.java).apply {
                    putExtra("TITLE", notification.title)
                    putExtra("MESSAGE", notification.message)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notification.timeInMillis,
                pendingIntent
            )


    }

    override fun cancel(notification: Notification) {
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    notification.hashCode(),
                    Intent(context, MedicamentoNotificationReceiver::class.java),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )



    }

}

@SuppressLint("ScheduleExactAlarm")
fun setAlarm(context: Context, timeInMillis: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MedicamentoNotification::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
}
