package com.pplog.app

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pplog.app.data.local.SeedDatabaseWorker
import com.pplog.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PPLOGApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PPLOGApplication)
            modules(appModule)
        }
        enqueueSeedWorker()
    }

    private fun enqueueSeedWorker() {
        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
        WorkManager.getInstance(this).enqueue(request)
    }
}
