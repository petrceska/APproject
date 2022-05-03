package com.example.weatherapp.database

import androidx.room.*

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast where id = :id")
    fun getForecastById(id : Int): Forecast?

    @Query("SELECT * FROM forecast where cityName = :cityName")
    fun getForecastByCityName(cityName : String): Forecast?

    @Update
    fun update(forecast: Forecast): Int

    @Insert
    fun insert(forecast: Forecast)

    @Query("SELECT COUNT(*) from forecast")
    fun countForecasts(): Int
}