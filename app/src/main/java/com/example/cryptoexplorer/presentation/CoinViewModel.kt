package com.example.cryptoexplorer.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoexplorer.data.repository.CoinInfoRepositoryImpl
import com.example.cryptoexplorer.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoexplorer.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoexplorer.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinInfoRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    var coinInfoList = getCoinInfoListUseCase()

    fun getPriceInfoAboutCoin(fsym: String) = getCoinInfoUseCase(fsym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

}