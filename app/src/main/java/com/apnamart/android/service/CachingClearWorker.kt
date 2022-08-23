package com.apnamart.android.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.apnamart.android.dataSource.RepositoryDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CachingClearWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val CNTXT = appContext
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            RepositoryDb.clearCache(CNTXT)
            Log.d("CLEAR CACHE", "SUCCESS")
        }.also {
            return Result.success()
        }

        // Indicate whether the work finished successfully with the Result
    }
}
