package com.example.cryptoexplorer.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoexplorer.data.database.AppDatabase
import com.example.cryptoexplorer.data.mapper.CoinMapper
import com.example.cryptoexplorer.data.network.ApiFactory
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.example.cryptoexplorer.domain.repository.CoinRepository
import kotlinx.coroutines.delay

class CoinInfoRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val mapper = CoinMapper()
    private val coinInfoDao = AppDatabase.getInstance(application).getCoinPriceInfoDao()
    private val apiService = ApiFactory.apiService

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

    override suspend fun loadData() {
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

}