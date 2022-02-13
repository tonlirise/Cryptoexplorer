package com.example.cryptoexplorer.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CoinInfoDao {

    @Query("SELECT * FROM coin_info_price ORDER BY lastUpdate")
    fun getPriceList(): LiveData<List<CoinInfoDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceList(coins : List<CoinInfoDBModel>)

    @Query("SELECT * FROM coin_info_price WHERE fromSymbol == :fSymbol")
    fun getOnePrice(fSymbol : String) : LiveData<CoinInfoDBModel>
}