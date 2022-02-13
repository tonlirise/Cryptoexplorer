package com.example.cryptoexplorer.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CoinInfoContainerDto (
    @SerializedName("RAW")
    @Expose
    val coinPriceInfoJsonObject : JsonObject? = null
)