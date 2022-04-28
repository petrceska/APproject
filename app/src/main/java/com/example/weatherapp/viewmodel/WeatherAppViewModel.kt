package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.Weather
import com.example.weatherapp.model.DayWeather
import com.example.weatherapp.repository.ServiceBuilder
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherAppViewModel : ViewModel() {
    private var apiKey = "6b683d819fb44284a3a3cc2ec0b5b434"

    private lateinit var job: Job
    @Volatile
    private var running = true
    lateinit var repository: WeatherRepository
    private val weatherService = ServiceBuilder().getWeatherService()
    private val _dayWeather = MutableLiveData<DayWeather>()
    val dayWeather: LiveData<DayWeather>
        get() = _dayWeather

    fun getDayWeather(lat: Number, lon: Number) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            while (running) {


                //retrieve from API
                val dayWeather = weatherService.getDayWeather(apiKey, lat, lon)
                val weather = dayWeather.body()

                // Fetch the weather for actual day
                _dayWeather.postValue(weather)
            }
        }
    }


fun searchForDayWeather(city: String) {
     // Launch coroutine in viewModelScope
    viewModelScope.launch(Dispatchers.IO) {
        while (running) {

            // Fetch the weather for actual day
//                val newWeather = getWeatherByCityName(city)

//                _dayWeather.postValue(newWeather)
             // transform Weather to DayWeather
        }
    }
}

fun getWeatherByCityName(cityName: String) {
    repository.getWeatherByCityName(cityName)
}

fun getWeatherByCityId(cityId: Number) {
    repository.getWeatherByCityId(cityId)
}

private fun insert(weather: Weather) {
    repository.insert(weather)
}

fun update(weather: Weather) {
    repository.update(weather)
}

private fun find(): Int {
    return repository.count()
}
}