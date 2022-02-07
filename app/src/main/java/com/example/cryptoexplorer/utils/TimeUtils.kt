package com.example.cryptoexplorer.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(timestamp : Long?) : String{
    if (timestamp == null)
        return ""

    val stamp = Timestamp(timestamp)
    val date = Date(stamp.time)

    val patternt = "HH:mm:ss"
    val sdf = SimpleDateFormat(patternt, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()

    return sdf.format(date)
}