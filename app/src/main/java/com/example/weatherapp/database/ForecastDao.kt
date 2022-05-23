package com.example.weatherapp.database

import androidx.room.*

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast where id = :id")
    fun getForecastById(id : Int): Forecast?

    @Query("SELECT * FROM forecast where cityName = :cityName order by date_time limit 3")
    fun getForecastByCityName(cityName : String): Array<Forecast>

    @Query("SELECT * FROM forecast where cityName = :cityName and date_time = :dateTime")
    fun getForecastByCityNameAndDate(cityName : String, dateTime : String): Array<Forecast>

    //
    @Query("SELECT * FROM forecast where date_time = :date_time")
    fun getForecastByDate(date_time : String): Forecast?

    @Update
    fun update(forecast: Forecast): Int

    @Insert
    fun insert(forecast: Forecast)

    @Query("SELECT COUNT(*) from forecast")
    fun countForecasts(): Int
}