package com.example.weatherapp.repository

import com.example.weatherapp.model.DayWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.FieldPosition

interface WeatherService {
    @GET("current")
    suspend fun getDayWeather(@Query("key") apiKey: String, @Query("lat") lat: Number, @Query("lon") lon: Number): Response<DayWeather>

    @GET("current")
    suspend fun getDayWeatherByCity(@Query("key") apiKey: String, @Query("city") city: String): Response<DayWeather>
}