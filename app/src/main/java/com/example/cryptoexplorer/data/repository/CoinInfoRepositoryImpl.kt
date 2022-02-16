package com.example.cryptoexplorer.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoexplorer.data.database.AppDatabase
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.workers.RefreshDataWorker
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.example.cryptoexplorer.domain.repository.CoinRepository

class CoinInfoRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val mapper = CoinMapper()
    private val coinInfoDao = AppDatabase.getInstance(application).getCoinPriceInfoDao()

    override fun getCoinInfoList(): LiveData<List<CoinInfoEntity>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoEntity> {
        return Transformations.map(coinInfoDao.getOnePrice(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)

        workManager.enqueueUniqueWork(
            RefreshDataWorker.WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.getOneTimeRequest()
        )
    }
}