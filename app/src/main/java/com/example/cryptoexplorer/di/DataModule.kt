package com.example.cryptoexplorer.di

import android.app.Application
import com.example.cryptoexplorer.data.database.AppDatabase
import com.example.cryptoexplorer.data.database.CoinInfoDao
import com.example.cryptoexplorer.data.network.ApiFactory
import com.example.cryptoexplorer.data.network.ApiService
import com.example.cryptoexplorer.data.repository.CoinInfoRepositoryImpl
import com.example.cryptoexplorer.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    @AppScope
    fun bindCoinRepository(coinInfoRepositoryImpl: CoinInfoRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @AppScope
        fun providesCoinInfoDao(
            application: Application
        ) : CoinInfoDao{
            return AppDatabase.getInstance(application).getCoinPriceInfoDao()
        }

        @Provides
        @AppScope
        fun providesApiService() : ApiService{
            return ApiFactory.apiService
        }
    }
}