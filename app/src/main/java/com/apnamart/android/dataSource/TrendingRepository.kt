package com.apnamart.android.dataSource

import com.apnamart.android.models.RepositoryModel
import com.apnamart.android.utils.RemoteService
import com.apnamart.android.utils.parseToTrendingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class TrendingRepository(val database: RepositoryDb, val remoteSource: RemoteService) {

    interface OnErrorListener{
        fun onError(error:String?)
    }

    suspend fun getTrendingRepos(listener:OnErrorListener): List<RepositoryModel> {
        try {
            val cachedData = database.repoDao().getAllTrendingRepos()
            if (cachedData.isEmpty()) {
                loadDataFromRemote(listener)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            listener.onError(e.message)
        }

        return database.repoDao().getAllTrendingRepos()
    }

    private fun loadDataFromRemote(listener:OnErrorListener) = runBlocking {
        try {
            withContext(Dispatchers.IO) {
                val response = remoteSource.getTrendingRepos()
                if (response.isSuccessful) {
                    val newData = response.body()!!.parseToTrendingModel()
                    runBlocking {
                        for (i in newData) {
                            database.repoDao().insertRepo(i)
                        }
                    }

                }
            }
        } catch (e: Exception) {
            if(e is UnknownHostException){
                listener.onError(e.message)
            }
            e.printStackTrace()
        }
    }
}