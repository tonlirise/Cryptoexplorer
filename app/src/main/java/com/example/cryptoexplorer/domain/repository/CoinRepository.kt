package com.example.cryptoexplorer.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptoexplorer.data.model.CoinInfo

interface CoinRepository {
    fun getCoinInfoList() : LiveData<List<CoinInfo>>

    fun getCoinInfo(fSymbol : String) : LiveData<CoinInfo>
}