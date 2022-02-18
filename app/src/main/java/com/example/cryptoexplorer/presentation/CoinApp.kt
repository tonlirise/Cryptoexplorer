package com.example.cryptoexplorer.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoexplorer.data.workers.CoinWorkerFactory
import com.example.cryptoexplorer.di.DaggerAppComponent
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    @Inject
    lateinit var workFactory: CoinWorkerFactory

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