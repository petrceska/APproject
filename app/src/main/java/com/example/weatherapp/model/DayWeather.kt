package com.example.weatherapp.model
import androidx.room.Entity
import androidx.room.PrimaryKey

class DayWeather {
    var count : Number? = null
    var data : Array<DayWeatherData>? = null
}