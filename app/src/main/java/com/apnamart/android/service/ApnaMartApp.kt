package com.apnamart.android.service

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ApnaMartApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val myWork = PeriodicWorkRequestBuilder<CachingClearWorker>(
            5, TimeUnit.SECONDS
        )
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "CachingClearWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}