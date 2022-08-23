package com.apnamart.android.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnamart.android.dataSource.TrendingRepository
import com.apnamart.android.models.RepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendingViewModel(val repo: TrendingRepository) : ViewModel() {
    val isErrorOccurred: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    private val listOfRepos = mutableListOf<RepositoryModel>()
    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val trendingReposObservable: MutableLiveData<List<RepositoryModel>> by lazy { MutableLiveData() }

    val isExpandedAt: MutableLiveData<Int> by lazy { MutableLiveData(-1) }
    val scrollPos: MutableLiveData<Int> by lazy { MutableLiveData(-1) }

    fun initializeData(data: List<RepositoryModel>?) {
        isLoading.postValue(true)
        listOfRepos.clear()
        if (data == null)
            for (i in 0..10) {
                listOfRepos.add(
                    RepositoryModel(
                        i,
                        "",
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

            if (isExpandedAt.value!! >= 0) {
                for (i in data.indices) {
                    if (i == isExpandedAt.value!!) {
                        val oldData = data[i]
                        oldData.isExpanded = true
                        listOfRepos[i] = oldData
                    } else {
                        val oldData = data[i]
                        oldData.isExpanded = false
                        listOfRepos[i] = oldData
                    }
                }
            }

        }

        trendingReposObservable.postValue(listOfRepos)

    }

    fun setData(isRefreshed:Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isLoading.postValue(true)
                try {
                    val response =
                        repo.getTrendingRepos(isRefreshed,object : TrendingRepository.OnErrorListener {
                            override fun onError(error: String?) {
                                isErrorOccurred.postValue(true)
                                return
                            }
                        })
                    if (response.isNotEmpty()) {
                        initializeData(response)
                    }
                    isLoading.postValue(false)

                } catch (e: Exception) {
                    isErrorOccurred.postValue(true)
                    e.printStackTrace()
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun refresh() {
        isLoading.postValue(true)
        initializeData(null)
        setData(true)
        isLoading.postValue(false)
    }
}