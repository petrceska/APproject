package com.example.weatherapp.repository

import com.example.weatherapp.database.Weather
import com.example.weatherapp.database.WeatherDao


class WeatherRepository(private val weatherDao: WeatherDao) {

    fun getWeatherByStationId(id : Int) : Weather?{
        return weatherDao.getWeatherByStationId(id)
    }

    fun getWeatherByCityName(cityName : String): Weather? {
        return weatherDao.getWeatherByCityName(cityName)
    }

    fun insert(weather: Weather) {
        weatherDao.insert(weather)
    }

    fun countWeather(): Int {
        return weatherDao.countWeathers()
    }

    fun update(weather: Weather) {
        weatherDao.update(weather)
    }

}