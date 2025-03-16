package com.digitaldose.medtime.utils.helpers

import android.icu.util.Calendar
import android.icu.util.TimeZone

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 17/11/2024
 */
class HorariosHelper {
    companion object {
//        fun gerarHorarios(horarioInicial: String, intervalo: Int): List<String> {
//            val horarios = mutableListOf<String()
//        }

        fun converterHorarioStringParaLong(horarios: List<String>): List<Long> {
            val currentTime = Calendar.getInstance()
            return horarios.map { horario ->
                val partes = horario.split(":") // Divide a String no formato HH:mm
                val hora = partes[0].toInt()
                val minuto = partes[1].toInt()

                Calendar.getInstance().apply {
                    timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
                    set(Calendar.HOUR_OF_DAY, hora)
                    set(Calendar.MINUTE, minuto)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)

                    // Se o horário já passou hoje, agenda para o próximo dia
                    if (timeInMillis <= currentTime.timeInMillis) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }.timeInMillis
            }
        }
    }
}