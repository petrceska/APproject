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
    var cityName: String? = null

    @JvmField
    var temperature: Int? = null

    @JvmField
    var temperatureMin: Int? = null

    @JvmField
    var temperatureMax: Int? = null

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
    var windSpeed: Int? = null

    @JvmField
    var windDirection: String? = null

    @JvmField
    var precipitation: Int? = null

    @JvmField
    var precipitationProbability: Int? = null

}
