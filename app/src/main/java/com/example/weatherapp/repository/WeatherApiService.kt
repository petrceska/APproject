package com.example.weatherapp.repository

import com.example.weatherapp.model.ApiWeather
import com.example.weatherapp.model.ApiForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current")
    suspend fun getDayWeather(@Query("key") apiKey: String, @Query("lat") lat: Number, @Query("lon") lon: Number): Response<ApiWeather>

    @GET("forecast/daily")
    suspend fun getForecast(@Query("key") apiKey: String, @Query("lat") lat: Number, @Query("lon") lon: Number): Response<ApiForecast>

    @GET("forecast/daily")
    suspend fun getForecastByCity(@Query("key") apiKey: String, @Query("city") city: String): Response<ApiForecast>

    @GET("current")
    suspend fun getDayWeatherByCity(@Query("key") apiKey: String, @Query("city") city: String): Response<ApiWeather>
}