package com.example.cryptoexplorer.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CoinNameListDto(
    @SerializedName("Data")
    @Expose
    val coinNameContainers: List<CoinNameContainerDto>? = null
)
