package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.database.AppDatabase
import com.example.weatherapp.repository.ForecastRepository
import com.example.weatherapp.repository.WeatherRepository

class WeatherApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getAppDatabase(this) }
    val weatherRepository by lazy { WeatherRepository(database!!.weatherDao()) }
    val forecastRepository by lazy { ForecastRepository(database!!.forecastDao()) }

}