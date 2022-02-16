package com.example.cryptoexplorer.domain.usecases

import com.example.cryptoexplorer.domain.repository.CoinRepository

class LoadDataUseCase(private val coinRep : CoinRepository) {
    operator fun invoke() = coinRep.loadData()
}