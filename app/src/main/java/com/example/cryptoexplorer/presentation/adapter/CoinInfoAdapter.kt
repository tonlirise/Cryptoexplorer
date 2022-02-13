package com.example.cryptoexplorer.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptoexplorer.databinding.ItemCoinInfoBinding
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.squareup.picasso.Picasso

class CoinInfoAdapter : ListAdapter<CoinInfoEntity, CoinInfoViewHolder>(CoinInfoDiffCallback()) {
    var coinClickListener : CoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val bindinng =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(bindinng)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder.binding){
            tvSymbols.text = "${coin.fromSymbol} / ${coin.toSymbol}"
            tvPrice.text = coin.price
            tvLastUpdate.text = coin.lastUpdate
            Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
        }
        holder.itemView.setOnClickListener{
            coinClickListener?.onCoinClickListener(coin)
        }
    }

    interface CoinClickListener{
        fun onCoinClickListener (coinPriceInfo : CoinInfoEntity)
    }
}