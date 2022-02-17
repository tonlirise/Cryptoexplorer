package com.example.cryptoexplorer.di

import androidx.lifecycle.ViewModel
import com.example.cryptoexplorer.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindsCoinViewModel(coinViewModel: CoinViewModel) : ViewModel
}