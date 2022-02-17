package com.example.cryptoexplorer.domain.usecases

import com.example.cryptoexplorer.domain.repository.CoinRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val coinRep : CoinRepository) {
    operator fun invoke() = coinRep.loadData()
}