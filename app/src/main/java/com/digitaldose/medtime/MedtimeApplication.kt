package com.digitaldose.medtime

import android.app.Application
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequest
import com.digitaldose.medtime.di.appModules
import com.digitaldose.medtime.di.modules.NOTIFICATION_WORKER
import com.digitaldose.medtime.services.receiver.ativarBootReceiver
import com.digitaldose.medtime.utils.helpers.WorkScheduleHelper
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.qualifier.named

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 20/03/2025
 */
class MedtimeApplication : Application(), Configuration.Provider {
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setMinimumLoggingLevel(android.util.Log.DEBUG).build()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MedtimeApplication)
            modules(appModules)
            workManagerFactory()
        }

        val workRequest = get<PeriodicWorkRequest>(named(NOTIFICATION_WORKER))
        WorkScheduleHelper.scheduleDailyWork(this, workRequest)
        ativarBootReceiver(this)
    }


}