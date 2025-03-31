package com.digitaldose.medtime.utils.helpers

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.digitaldose.medtime.services.workers.LembreteNotificationWorker
import java.util.concurrent.TimeUnit

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 31/03/2025
 */

class WorkScheduleHelper {
    companion object {
        fun scheduleDailyWork(context: Context, workRequest: PeriodicWorkRequest) {
            try {
//                val workRequest =
//                    PeriodicWorkRequestBuilder<LembreteNotificationWorker>(1, TimeUnit.DAYS)
//                        .setInitialDelay(getTimeUntilMidnight(), TimeUnit.MILLISECONDS)
//                        .build()

                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    "daily_work",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    workRequest
                )
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun getTimeUntilMidnight() : Long {
            val now = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"))
            val midnight = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                add(Calendar.DAY_OF_MONTH, 1)
            }
            return midnight.timeInMillis - now.timeInMillis
        }
    }
}