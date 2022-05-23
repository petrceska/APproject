package com.example.weatherapp.repository

import com.example.weatherapp.database.Forecast
import com.example.weatherapp.database.ForecastDao

class ForecastRepository(private val forecastDao: ForecastDao)  {

    fun getForecastByStationId(id: Int): Forecast? {
        return forecastDao.getForecastById(id)
    }


    fun getForecastByCityName(cityName: String): Array<Forecast> {
        return forecastDao.getForecastByCityName(cityName)
    }

    fun insert(forecast: Forecast) {
        forecastDao.insert(forecast)
    }

    fun countForecast(): Int {
        return forecastDao.countForecasts()
    }

    fun update(forecast: Forecast) {
        forecastDao.update(forecast)
    }
}