package com.apnamart.android.ui.screens

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.apnamart.android.R
import com.apnamart.android.dataSource.TrendingRepository
import com.apnamart.android.databinding.ActivityMainBinding
import com.apnamart.android.viewmodels.ParamViewModelFactory
import com.apnamart.android.viewmodels.TrendingViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: TrendingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProvider(
            this, ParamViewModelFactory(
                TrendingRepository(this)
            )
        )[TrendingViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        val connectivityManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()


    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d("LOST CONNECTION", "FALSE")
            viewmodel.isErrorOccurred.postValue(false)
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            Log.d("LOST CONNECTION" , "CONNECTION CHANGED")

        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            if (viewmodel.trendingReposObservable.value!!.isEmpty())
                viewmodel.isErrorOccurred.postValue(true)
            else Snackbar.make(
                binding.root,
                "Connection lost",
                Snackbar.LENGTH_SHORT
            ).show()

        }
    }
}