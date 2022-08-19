package com.apnamart.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apnamart.android.dataSource.RepositoryDb
import com.apnamart.android.dataSource.TrendingRepository
import com.apnamart.android.utils.RemoteService

class ParamViewModelFactory(val database:TrendingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrendingViewModel::class.java)) {
            return TrendingViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}