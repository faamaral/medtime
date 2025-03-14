package com.digitaldose.medtime.services.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.digitaldose.medtime.services.notification.RunnerNotifier

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val medicamento = intent?.getStringExtra("medicamento") ?: "medicamento"
        val dosagem = intent?.getStringExtra("dosagem") ?: "dosagem"
        val tipoDosagem = intent?.getStringExtra("tipoDosagem") ?: "tipoDosagem"

        context?.let {
            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val runnerNotifier = RunnerNotifier(notificationManager, it)
            runnerNotifier.showNotification(medicamento, dosagem, tipoDosagem)
        }
    }

}
