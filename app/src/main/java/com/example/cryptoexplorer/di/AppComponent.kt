package com.example.cryptoexplorer.di

import android.app.Application
import com.example.cryptoexplorer.presentation.CoinApp
import com.example.cryptoexplorer.presentation.CoinInfoFragment
import com.example.cryptoexplorer.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun injection(coinPriceListActivity: CoinPriceListActivity)

    fun injection(coinInfoFragment: CoinInfoFragment)

    fun injection(coinApp : CoinApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            application: Application
        ): AppComponent
    }
}