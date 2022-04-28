package com.example.weatherapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.Calendar

@Entity(tableName = "weather")
class Weather {
    @JvmField
    @PrimaryKey
    var cityId = 0

    @JvmField
    var actualized: Date = Calendar.getInstance().time

    @JvmField
    var cityName: String? = null

    @JvmField
    var temperature: Number? = null

    @JvmField
    var temperatureMin: Number? = null

    @JvmField
    var temperatureMax: Number? = null

    @JvmField
    var humidity: Number? = null

    @JvmField
    var clouds: Number? = null

    @JvmField
    var sunrise: Number? = null

    @JvmField
    var sunset: Number? = null

    @JvmField
    var uv: Number? = null

    @JvmField
    var windSpeed: Number? = null

    @JvmField
    var windDirection: String? = null



}
