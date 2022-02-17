package com.example.cryptoexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoexplorer.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoexplorer.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoexplorer.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase

) : ViewModel() {

    var coinInfoList = getCoinInfoListUseCase()

    fun getPriceInfoAboutCoin(fsym: String) = getCoinInfoUseCase(fsym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

}