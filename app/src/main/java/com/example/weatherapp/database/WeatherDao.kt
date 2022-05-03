package com.example.weatherapp.database

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather where stationId = :stationId")
    fun getWeatherByStationId(stationId : Int): Weather?

    @Query("SELECT * FROM weather where cityName = :cityName")
    fun getWeatherByCityName(cityName : String): Weather?

    @Update
    fun update(weather: Weather): Int

    @Insert
    fun insert(weather: Weather)

    @Query("SELECT COUNT(*) from weather")
    fun countWeathers(): Int
}