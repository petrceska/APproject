package com.example.weatherapp.repository

import com.example.weatherapp.model.DayWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current?" + "lat=" + 49 + "&lon=" + 16 )
    suspend fun getDayWeather(@Query("key") apiKey: String): Response<DayWeather>
}