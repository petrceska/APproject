package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather where cityId = '%'")
    fun getWeatherByCityId(cityId : Number): Weather?

    @Query("SELECT * FROM weather where cityName = '%'")
    fun getWeatherByCityName(cityName : String): Weather?

    @Update
    fun update(weather: Weather): Int

    @Insert
    fun insert(weather: Weather)

    @Query("SELECT COUNT(*) from weather")
    fun countWeathers(): Int
}