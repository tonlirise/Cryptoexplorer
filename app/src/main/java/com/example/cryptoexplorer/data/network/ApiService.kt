package com.example.cryptoexplorer.data.network

import com.example.cryptoexplorer.data.model.CoinInfoListOfData
import com.example.cryptoexplorer.data.model.CoinPriceInfoRawData
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

        private const val API_KEY = "fd17592732758118c86b6258712661ea929c361541ab1ddb2641ed3ce7b95c47"
        private const val CURRENCY = "USD"
    }
}