package com.example.weatherapp.repository

import com.example.weatherapp.database.Weather
import com.example.weatherapp.database.WeatherDao


class WeatherRepository(private val weatherDao: WeatherDao) {

    fun getWeatherByCityId(cityId : Number){
        weatherDao.getWeatherByCityId(cityId)
    }

    fun getWeatherByCityName(cityName : String) {
        weatherDao.getWeatherByCityName(cityName)
    }

    fun insert(weather: Weather) {
        weatherDao.insert(weather)
    }

    fun count(): Int {
        return weatherDao.countWeathers()
    }

    fun update(weather: Weather) {
        weatherDao.update(weather)
    }

}