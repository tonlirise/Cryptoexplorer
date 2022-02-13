package com.example.cryptoexplorer.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoexplorer.databinding.ItemCoinInfoBinding
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.squareup.picasso.Picasso

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {
    var coinInfoList = listOf<CoinInfoEntity>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var coinClickListener : CoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val bindinng =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(bindinng)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
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

    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface CoinClickListener{
        fun onCoinClickListener (coinPriceInfo : CoinInfoEntity)
    }
}