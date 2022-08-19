package com.apnamart.android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.android.models.RepositoryModel
import com.apnamart.android.utils.parseToTrendingModel
import com.apnamart.android.utils.webservice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler

class TrendingViewModel : ViewModel() {
    private val listOfRepos = mutableListOf<RepositoryModel>()
    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val trendingReposObservable: MutableLiveData<List<RepositoryModel>> by lazy { MutableLiveData() }

    fun initializeData(data: List<RepositoryModel>?) {
        isLoading.postValue(true)
        listOfRepos.clear()
        if (data == null)
            for (i in 0..10) {
                listOfRepos.add(
                    RepositoryModel(
                        "",
                        "",
                        "",
                        0,
                        0,
                        "",
                        false
                    )
                )
            }
        if (data != null) {
            listOfRepos.addAll(data)
        }

        trendingReposObservable.postValue(listOfRepos)

    }

    fun setData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isLoading.postValue(true)
                delay(5000)
                try {
                    val response = webservice.getTrendingRepos()
                    if (response.isSuccessful) {
                        initializeData(response.body()!!.parseToTrendingModel())
                    }
                    isLoading.postValue(false)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun refresh() {
        isLoading.postValue(true)
        initializeData(null)
        setData()
        isLoading.postValue(false)
    }
}