package com.example.weatherapp.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {

    private val url = "https://api.weatherbit.io/v2.0/"
    private var retrofit: Retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var weatherApiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    fun getWeatherService(): WeatherApiService {
        return weatherApiService
    }

}