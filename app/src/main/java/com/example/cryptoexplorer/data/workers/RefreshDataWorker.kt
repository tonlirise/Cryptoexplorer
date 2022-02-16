package com.example.cryptoexplorer.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoexplorer.data.database.AppDatabase
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService
    private val coinInfoDao = AppDatabase.getInstance(context).getCoinPriceInfoDao()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fsyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {
        const val WORKER_NAME = "RefreshDataWorker"

        fun getOneTimeRequest() = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
    }
}