package com.example.cryptoexplorer.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoexplorer.data.database.CoinInfoDao
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val mapper: CoinMapper,
    private val apiService: ApiService,
    private val coinInfoDao: CoinInfoDao
) : CoroutineWorker(context, workerParameters) {
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

    class Factory @Inject constructor(
        private val coinInfoDao: CoinInfoDao,
        private val apiService: ApiService,
        private val mapper: CoinMapper
    ) : ChildWorkerFactory {

        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                mapper,
                apiService,
                coinInfoDao
            )
        }
    }
}