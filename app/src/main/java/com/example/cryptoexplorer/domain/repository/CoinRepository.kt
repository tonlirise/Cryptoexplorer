package com.example.cryptoexplorer.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity

interface CoinRepository {
    fun getCoinInfoList() : LiveData<List<CoinInfoEntity>>

    fun getCoinInfo(fSymbol : String) : LiveData<CoinInfoEntity>

    suspend fun loadData()
}