package com.example.weatherapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.Calendar

@Entity(tableName = "forecast")
class Forecast {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @JvmField
    @ColumnInfo( defaultValue = "CURRENT_TIMESTAMP")
    var actualized: String? = null

    @JvmField
    var countryName: String? = null

    @JvmField
    var date_time: String? = null

    @JvmField
    var cityName: String? = null

    @JvmField
    var temperature: Double? = null

    @JvmField
    var temperatureMin: Double? = null

    @JvmField
    var temperatureMax: Double? = null

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

    @JvmField
    var precipitation: Int? = null

    @JvmField
    var precipitationProbability: Int? = null

    //TODO rewrite variable types so it actually matches with data you will get from API

}
