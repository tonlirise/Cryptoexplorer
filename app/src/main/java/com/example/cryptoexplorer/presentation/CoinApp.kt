package com.example.cryptoexplorer.presentation

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.example.cryptoexplorer.data.database.AppDatabase
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.network.ApiFactory
import com.example.cryptoexplorer.data.workers.RefreshDataWorkerFactory
import com.example.cryptoexplorer.di.DaggerAppComponent
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    @Inject
    lateinit var workFactory: RefreshDataWorkerFactory

    override fun onCreate() {
        component.injection(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()
    }
}