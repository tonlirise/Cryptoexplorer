package com.example.cryptoexplorer.data.mapper

import com.example.cryptoexplorer.data.database.CoinInfoDBModel
import com.example.cryptoexplorer.data.network.model.CoinInfoContainerDto
import com.example.cryptoexplorer.data.network.model.CoinInfoDto
import com.example.cryptoexplorer.data.network.model.CoinNameListDto
import com.example.cryptoexplorer.domain.entities.CoinInfoEntity
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor(){
    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDBModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
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
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object{
        private const val BASE_IMAGE_URL = "https://cryptocompare.com/"
    }
}