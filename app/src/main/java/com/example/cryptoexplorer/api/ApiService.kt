package com.example.cryptoexplorer.api

import com.example.cryptoexplorer.pojo.CoinInfoListOfData
import com.example.cryptoexplorer.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinInfo(
        @Query(QUERY_PARAM_API_KEY) api_key : String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit : Int = 10,
        @Query(QUERY_PARAM_TSYM) tsym : String = CURRENCY
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) api_key : String = API_KEY,
        @Query(QUERY_PARAM_FSYMS) fsyms : String,
        @Query(QUERY_PARAM_TSYMS) tsyms : String = CURRENCY
    ) : Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"

        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TSYM = "tsym"

        private const val QUERY_PARAM_FSYMS = "fsyms"
        private const val QUERY_PARAM_TSYMS = "tsyms"

        private const val API_KEY = ""
        private const val CURRENCY = "USD"
    }
}