package com.example.cryptoexplorer.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity

class CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfoEntity>() {
    override fun areItemsTheSame(oldItem: CoinInfoEntity, newItem: CoinInfoEntity): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfoEntity, newItem: CoinInfoEntity): Boolean {
        return oldItem == newItem
    }
}