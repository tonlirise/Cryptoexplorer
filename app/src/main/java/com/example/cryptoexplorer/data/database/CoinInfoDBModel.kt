package com.example.cryptoexplorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "coin_info_price")
data class CoinInfoDBModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String?,
    val price: String?,
    val lastUpdate: Long?,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val imageUrl: String?
)
