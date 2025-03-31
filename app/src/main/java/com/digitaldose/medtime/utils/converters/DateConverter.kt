package com.digitaldose.medtime.utils.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 21/03/2025
 */
class DateConverter {
    companion object {
        fun convertMillisToDate(millis: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
            return formatter.format(Date(millis))
        }

        fun stringParaLong(dataString: String, formato: String = "dd/MM/yyyy"): Long {
            val sdf = SimpleDateFormat(formato, Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
            return sdf.parse(dataString)?.time ?: 0L
        }
    }
}