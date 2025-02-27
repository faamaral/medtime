package com.digitaldose.medtime.services.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.digitaldose.medtime.R

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 22/11/2024
 */
class MedicamentoNotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

            val builder = NotificationCompat.Builder(context!!, "medicamento_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("já tomou o seu medicamento")
                .setContentText("Hora de tomar seu medicamento")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(context)
            /*if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }*/
            notificationManager.notify(1, builder.build())


    }

}

//class MedicamentoNotification : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        val builder = NotificationCompat.Builder(context!!, "medicamento_channel")
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle("já tomou o seu medicamento")
//            .setContentText("Hora de tomar seu medicamento")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//
//        val notificationManager = NotificationManagerCompat.from(context)
//        notificationManager.notify(1, builder.build())
//    }
//
//}