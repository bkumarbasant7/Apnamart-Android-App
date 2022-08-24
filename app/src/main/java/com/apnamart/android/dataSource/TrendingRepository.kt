package com.apnamart.android.dataSource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apnamart.android.models.RepositoryModel
import com.apnamart.android.utils.RemoteServiceProvider
import com.apnamart.android.utils.parseToTrendingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class TrendingRepository(val context: Context) {

    private val local = RepositoryDb(context.applicationContext).repoDao()
    private val allTrendingRepos: MutableLiveData<List<RepositoryModel>> by lazy { MutableLiveData() }

    var getAllTrendingRepos: List<RepositoryModel> = allTrendingRepos.value ?: emptyList()

    interface OnErrorListener {
        fun onError(error: String?)
    }

    suspend fun getTrendingRepos(
        isRefreshed: Boolean,
        listener: OnErrorListener
    ): List<RepositoryModel>{
        try {
            if (isRefreshed) {
                loadDataFromRemote(listener)
            } else {
                val cachedData = local.getAllTrendingRepos()
                if (cachedData.isEmpty()) {
                    loadDataFromRemote(listener)

                }
//                loadDataFromRemote(listener)

            }

        }catch (e:Exception) {
            e.printStackTrace()
            listener.onError(e.message)
        }

        return local.getAllTrendingRepos()
    }

    private fun loadDataFromRemote(listener:OnErrorListener) = runBlocking {
        try {
            withContext(Dispatchers.IO) {
                val response = RemoteServiceProvider(context).webService.getTrendingRepos()
                if (response.isSuccessful) {
//                    val newData = response.body()!!.parseToTrendingModel()
//                    runBlocking {
//                        for (i in newData) {
//                            local.insertRepo(i)
//                        }
//                    }
                    if(response.body()!!.parseToTrendingModel().isNotEmpty()) {
                    getAllTrendingRepos = response.body()!!.parseToTrendingModel()
                    allTrendingRepos.postValue(response.body()!!.parseToTrendingModel())
                        }

                } else {
                    listener.onError(response.message())
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