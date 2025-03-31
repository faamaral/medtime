package com.digitaldose.medtime.services.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.digitaldose.medtime.database.models.LembreteEntity
import com.digitaldose.medtime.database.models.NotificationItem
import com.digitaldose.medtime.database.repositories.LembreteRepository
import com.digitaldose.medtime.database.repositories.MedicamentoRepository
import com.digitaldose.medtime.services.notification.NotificationAlarmScheduler
import com.digitaldose.medtime.utils.helpers.HorariosHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 31/03/2025
 */
class LembreteNotificationWorker(
    private val medicamentoRepository: MedicamentoRepository,
    private val lembreteRepository: LembreteRepository,
    private val appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {
    private val medicamentoNotification = NotificationAlarmScheduler(appContext)
    override suspend fun doWork(): Result {
        return try {
            val user = FirebaseAuth.getInstance().currentUser
            val userId = user?.uid ?: return Result.failure()
            withContext(Dispatchers.IO) {
                val medicamentos = medicamentoRepository.getMedicamentoByUserIdWork(userId)

                medicamentos.forEach { medicamento ->
                    val timeInMills =
                        HorariosHelper.converterHorarioStringParaLong(medicamento.horario!!)
                    timeInMills.forEach { horario ->
                        val lembrete = LembreteEntity(
                            titulo = medicamento.nome!!,
                            descricao = "Hora de tomar seu medicamento ${medicamento.nome}",
                            data = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant()
                                .toEpochMilli(),
                            hora = horario,
                            medicamentoId = medicamento.id
                        )
                        val lembreteID = lembreteRepository.createLembrete(lembrete)
                        medicamentoNotification.schedule(
                            NotificationItem(
                                time = horario,
                                id = lembreteID,
                                medicamento = medicamento
                            )
                        )

                    }
                }

                Result.success()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}