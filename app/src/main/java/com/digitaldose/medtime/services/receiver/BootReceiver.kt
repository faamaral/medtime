package com.digitaldose.medtime.services.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.digitaldose.medtime.services.notification.RunnerNotifier

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 22/03/2025
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val medicamento = intent?.getStringExtra("medicamento") ?: "medicamento"
        val dosagem = intent?.getStringExtra("dosagem") ?: "dosagem"
        val tipoDosagem = intent?.getStringExtra("tipoDosagem") ?: "tipoDosagem"
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                val notificationManager =
                    it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val runnerNotifier = RunnerNotifier(notificationManager, it)
                runnerNotifier.showNotification(medicamento, dosagem, tipoDosagem)
            }
        }
    }

}

fun ativarBootReceiver(context: Context) {
    val receiver = ComponentName(context, BootReceiver::class.java)

    context.packageManager.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}