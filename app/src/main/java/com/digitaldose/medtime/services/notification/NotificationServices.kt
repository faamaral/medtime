package com.digitaldose.medtime.services.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import com.digitaldose.medtime.R
import com.digitaldose.medtime.database.models.Medicamento

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 25/11/2024
 */
class NotificationServices {
    fun sendNotification(context: Context, channelID: String, medicamento: Medicamento) {
        var builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Hora de tomar ${medicamento.nome}")
            .setContentText("Hora de tomar ${medicamento.dosagem} de ${medicamento.nome}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}