package com.example.cryptoexplorer.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoexplorer.data.database.CoinInfoDao
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.network.ApiService

class RefreshDataWorkerFactory(
    private val mapper: CoinMapper,
    private val apiService: ApiService,
    private val coinInfoDao: CoinInfoDao
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshDataWorker(appContext, workerParameters, mapper, apiService, coinInfoDao)
    }
}