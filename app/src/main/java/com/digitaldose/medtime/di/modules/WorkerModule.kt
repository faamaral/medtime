package com.digitaldose.medtime.di.modules

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import com.digitaldose.medtime.services.workers.LembreteNotificationWorker
import com.digitaldose.medtime.utils.helpers.WorkScheduleHelper
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 31/03/2025
 */

const val NOTIFICATION_WORKER = "worker_notification"
val workerModule = module {
    factory<PeriodicWorkRequest>(named(NOTIFICATION_WORKER)) {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        return@factory PeriodicWorkRequest.Builder(
            LembreteNotificationWorker::class.java,
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints).setInitialDelay(WorkScheduleHelper.getTimeUntilMidnight(), TimeUnit.MILLISECONDS)
            .setId(UUID.randomUUID()).build()
    }
}