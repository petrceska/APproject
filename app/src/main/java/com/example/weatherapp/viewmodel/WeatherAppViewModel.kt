package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.Forecast
import com.example.weatherapp.database.Weather
import com.example.weatherapp.model.ApiWeather
import com.example.weatherapp.model.ApiForecast
import com.example.weatherapp.repository.ForecastRepository
import com.example.weatherapp.repository.ServiceBuilder
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherAppViewModel : ViewModel() {
    private var apiKey = "6b683d819fb44284a3a3cc2ec0b5b434"

    private lateinit var job: Job

    @Volatile
    private var running = true
    lateinit var weatherRepository: WeatherRepository
    lateinit var forecastRepository: ForecastRepository
    private val weatherApiService = ServiceBuilder().getWeatherService()
    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = _forecast

    fun getActualWeatherFromApi(lat: Number, lon: Number) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            while (running) {

                //retrieve from API
                val response = weatherApiService.getDayWeather(apiKey, lat, lon)

                val weather = weatherApiToDb(response)
                if (weather != null) {
                    if (weatherRepository.getWeatherByStationId(weather.stationId) == null) {
                        weatherRepository.insert(weather)
                    } else {
                        weatherRepository.update(weather)
                    }

                    // propagate the weather for actual day
                    _weather.postValue(weather)
                } else {
                    //TODO wrong api response
                }
            }
        }
    }

    fun getActualWeatherPreferablyDb(cityName: String) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            while (running) {
                if (weatherRepository.getWeatherByCityName(cityName) != null) {
                    // propagate the weather for actual day
                    _weather.postValue(weatherRepository.getWeatherByCityName(cityName))

                } else {
                    //retrieve from API
                    val response = weatherApiService.getDayWeatherByCity(apiKey, cityName)

                    val weather = weatherApiToDb(response)
                    if (weather != null) {
                        weatherRepository.insert(weather)

                        // propagate the weather for actual day
                        _weather.postValue(weather)
                    } else {
                        //TODO wrong api response
                        //Toast.makeText
                    }
                }
            }
        }
    }

    fun getActualForecastFromApi(lat: Number, lon: Number) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            while (running) {

                //retrieve from API
                val response = weatherApiService.getForecast(apiKey, lat, lon)

                val forecast = forecastApiToDb(response)
                if (forecast != null) {
                    if (forecastRepository.getForecastByStationId(forecast.id) == null) {
                        forecastRepository.insert(forecast)
                    } else {
                        forecastRepository.update(forecast)
                    }

                    // propagate the forecast for actual day
                    _forecast.postValue(forecast)
                } else {
                    //TODO wrong api response
                }
            }
        }
    }

    fun getForecastPreferablyDb(cityName: String) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            while (running) {
                if (forecastRepository.getForecastByCityName(cityName) != null) {
                    // propagate the forecast for actual day
                    _forecast.postValue(forecastRepository.getForecastByCityName(cityName))

                } else {
                    //retrieve from API
                    val response = weatherApiService.getForecastByCity(apiKey, cityName)

                    val forecast = forecastApiToDb(response)
                    if (forecast != null) {
                        forecastRepository.insert(forecast)

                        // propagate the forecast for actual day
                        _forecast.postValue(forecast)
                    } else {
                        //TODO wrong api response
                    }
                }
            }
        }
    }


    private fun weatherApiToDb(apiResponse: Response<ApiWeather>): Weather? {
        if (apiResponse.body()?.data?.get(0) != null) {
            val data = apiResponse.body()?.data!![0]
            val weather = Weather().apply {
                //TODO parse data from API object to DB objecct

                stationId = data.id
                cityName = data.city_name
                temperature = data.temp?.toDouble()
            }
            return weather
        }
        return null
    }

    private fun forecastApiToDb(apiResponse: Response<ApiForecast>): Forecast? {
        if (apiResponse.body()?.data?.get(0) != null) {
            val data = apiResponse.body()?.data!![0]
            val forecast = Forecast().apply {
            //TODO parse data from API object to DB objecct
            }
            return forecast
        }
        return null
    }
}