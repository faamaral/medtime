package com.digitaldose.medtime.utils.converters

import android.icu.util.Calendar
import android.icu.util.TimeZone

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 21/03/2025
 */
class HoraConverter {
    companion object {
        fun converterMillisToString(millis: Long): String {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"))
            calendar.timeInMillis = millis
            val hora = calendar.get(Calendar.HOUR_OF_DAY)
            val minuto = calendar.get(Calendar.MINUTE)
            return String.format("%02d:%02d", hora, minuto)
        }
    }
}