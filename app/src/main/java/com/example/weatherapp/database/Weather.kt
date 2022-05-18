package com.example.weatherapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "weather")
class Weather{
    //TODO: check the type of the variable return by the API
    @JvmField
    @PrimaryKey
    var stationId: Int = 0

    @JvmField
    @ColumnInfo( defaultValue = "CURRENT_TIMESTAMP")
    var actualized: String? = null

    @JvmField
    var countryCode: String? = null

    @JvmField
    var cityName: String? = null

    @JvmField
    var temperature: Double? = null

    @JvmField
    var humidity: Int? = null

    @JvmField
    var clouds: Int? = null

    @JvmField
    var sunrise: String? = null

    @JvmField
    var sunset: String? = null

    @JvmField
    var uv: Int? = null

    @JvmField
    var windSpeed: Double? = null

    @JvmField
    var windDirection: Int? = null

//TODO rewrite variable types so it actually matches with data you will get from API

}
