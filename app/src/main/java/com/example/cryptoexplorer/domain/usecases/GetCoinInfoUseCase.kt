package com.example.cryptoexplorer.domain.usecases

import com.example.cryptoexplorer.domain.repository.CoinRepository

class GetCoinInfoUseCase(private val coinRep : CoinRepository) {
    operator fun invoke(fSymbol : String) = coinRep.getCoinInfo(fSymbol)
}