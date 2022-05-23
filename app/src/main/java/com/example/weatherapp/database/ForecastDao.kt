package com.example.weatherapp.database

import androidx.room.*

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast where id = :id")
    fun getForecastById(id : Int): Forecast?

    @Query("SELECT * FROM forecast where cityName = :cityName order by date_time limit 6")
    fun getForecastByCityName(cityName : String): Array<Forecast>

    @Query("SELECT * FROM forecast where cityName = :cityName and date_time = :dateTime")
    fun getForecastByCityNameAndDate(cityName : String, dateTime : String): Forecast?

    //
    @Query("SELECT * FROM forecast where date_time = :dateTime")
    fun getForecastByDate(dateTime : String): Forecast?

    @Update
    fun update(forecast: Forecast): Int

    @Insert
    fun insert(forecast: Forecast)

    @Query("SELECT COUNT(*) from forecast")
    fun countForecasts(): Int
}