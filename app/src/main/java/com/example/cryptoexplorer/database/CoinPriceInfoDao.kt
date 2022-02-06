package com.example.cryptoexplorer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoexplorer.pojo.CoinPriceInfo


@Dao
interface CoinPriceInfoDao {

    @Query("SELECT * FROM coin_info_price ORDER BY lastUpdate")
    fun getPriceList(): LiveData<List<CoinPriceInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(coins : List<CoinPriceInfo>)

    @Query("SELECT * FROM coin_info_price WHERE fromSymbol == :fSymbol")
    fun getOnePrice(fSymbol : String) : LiveData<CoinPriceInfo>
}