package com.example.cryptoexplorer.presentation

import android.app.Application
import com.example.cryptoexplorer.di.DaggerAppComponent

class CoinApp : Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}