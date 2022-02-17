package com.example.cryptoexplorer.domain.usecases

import com.example.cryptoexplorer.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(private val coinRep : CoinRepository) {
    operator fun invoke(fSymbol : String) = coinRep.getCoinInfo(fSymbol)
}