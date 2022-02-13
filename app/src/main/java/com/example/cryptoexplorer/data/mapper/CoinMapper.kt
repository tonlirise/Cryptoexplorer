package com.example.cryptoexplorer.data.mapper

import com.example.cryptoexplorer.data.database.CoinInfoDBModel
import com.example.cryptoexplorer.data.network.ApiFactory
import com.example.cryptoexplorer.data.network.model.CoinInfoContainerDto
import com.example.cryptoexplorer.data.network.model.CoinInfoDto
import com.example.cryptoexplorer.data.network.model.CoinNameListDto
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.google.gson.Gson

class CoinMapper {
    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDBModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = ApiFactory.BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNameListDto): String {
        return namesListDto.coinNameContainers?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDBModel) = CoinInfoEntity(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = dbModel.lastUpdate,
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )
}