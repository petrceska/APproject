package com.example.weatherapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "weather")
class Weather {
    @JvmField
    @PrimaryKey
    var stationId: Int = 0

    @JvmField
    @ColumnInfo( defaultValue = "CURRENT_TIMESTAMP")
    var actualized: String? = null

    @JvmField
    var cityName: String? = null

    @JvmField
    var temperature: Int? = null

    @JvmField
    var humidity: Int? = null

    @JvmField
    var clouds: Int? = null

    @JvmField
    var sunrise: Int? = null

    @JvmField
    var sunset: Int? = null

    @JvmField
    var uv: Int? = null

    @JvmField
    var windSpeed: Float? = null

    @JvmField
    var windDirection: String? = null



//TODO rewrite variable types so it actually matches with data you will get from API

}
