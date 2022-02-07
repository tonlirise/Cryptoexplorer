package com.example.cryptoexplorer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoexplorer.api.ApiFactory
import com.example.cryptoexplorer.database.AppDatabase
import com.example.cryptoexplorer.pojo.CoinPriceInfo
import com.example.cryptoexplorer.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    private val dbCoinDao = AppDatabase.getInstance(application).getCoinPriceInfoDao()
    var coinInfoList = dbCoinDao.getPriceList()

    fun getPriceInfoAboutCoin(fsym : String) : LiveData<CoinPriceInfo>{
        return dbCoinDao.getOnePrice(fsym)
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fsyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                dbCoinDao.insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: ${it.toString()}")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Fail: ${it?.message ?: "no message"}")
            })
        compositeDisposable.add(disposable)
    }

    fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = arrayListOf<CoinPriceInfo>()
        val coinJsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val setKeys = coinJsonObject.keySet()
        for (key in setKeys) {
            val currJsonObj = coinJsonObject.getAsJsonObject(key)
            val currKeySet = currJsonObj.keySet()
            for (curKey in currKeySet) {
                val priceInfo =
                    Gson().fromJson(currJsonObj.getAsJsonObject(curKey), CoinPriceInfo::class.java)
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}