package com.example.weatherapp.viewmodel

import android.text.Html
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repository.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherAppViewModel : ViewModel() {
    private var apiKey = "6b683d819fb44284a3a3cc2ec0b5b434"
    // A coroutine job
    private lateinit var job: Job
    @Volatile
    private var running = true
    private val weatherService = ServiceBuilder().getWeatherService()
    private val _dayWeather = MutableLiveData<String>()
    val dayWeather: LiveData<String>
        get() = _dayWeather


    init {
        getDayWeather()
    }

    fun getDayWeather() {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO){
            while(running) {
                // Fetch the weather for actual day
                val dayWeather = weatherService.getDayWeather(apiKey)
                val test = Html.fromHtml(dayWeather.body()!!.data!![0].city_name).toString()

                _dayWeather.postValue(test)

                delay(10)
            }
        }
    }
}