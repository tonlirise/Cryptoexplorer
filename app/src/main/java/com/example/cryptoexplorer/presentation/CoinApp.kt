package com.example.cryptoexplorer.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoexplorer.data.database.AppDatabase
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.network.ApiFactory
import com.example.cryptoexplorer.data.workers.RefreshDataWorkerFactory
import com.example.cryptoexplorer.di.DaggerAppComponent

class CoinApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    CoinMapper(),
                    ApiFactory.apiService,
                    AppDatabase.getInstance(this).getCoinPriceInfoDao()
                )
            ).build()
    }
}