package com.example.weatherapp.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.util.Log
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
import com.example.weatherapp.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class WeatherAppViewModel : ViewModel() {
    private var apiKey = "6b683d819fb44284a3a3cc2ec0b5b434"

    //    private var apiKey = "50b06584d76f4d10b8e48182c4aa07b9"
    //558d117339f24712bb91f7afb0afe9df
    // for another api
    val API: String = "0cdd38b1b7a634430f4cb4b640ab6a26"

    private lateinit var job: Job

    @Volatile
    private var running = true
    lateinit var weatherRepository: WeatherRepository
    lateinit var forecastRepository: ForecastRepository
    private val weatherApiService = ServiceBuilder().getWeatherService()
    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _forecast = MutableLiveData<Array<Forecast>>()
    val forecast: LiveData<Array<Forecast>>
        get() = _forecast

    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList: LiveData<List<Weather>>
        get() = _weatherList

    fun getActualWeatherFromApi(lat: Number, lon: Number) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
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
                var response: String?
                try {
                    response =
                        URL("https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=metric&appid=$API").readText(
                            Charsets.UTF_8
                        )
                } catch (e: Exception) {
                    response = null
                }
                val jsonObject = JSONObject(response)
                val main = jsonObject.getJSONObject("main")
                val sys = jsonObject.getJSONObject("sys")
                val wind = jsonObject.getJSONObject("wind")
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0)

                val city_name = jsonObject.getString("name")
                val country_code = sys.getString("country")
                val location_display = city_name + ", " + country_code
                val weatherDescription = weather.getString("description")

                val temperature_api = main.getString("temp")
                val wind_speed = wind.getString("speed")
                val hum = main.getString("humidity") + " %"

                val updatedAt: Long = jsonObject.getLong("dt")
                val updatedAtText = "Updated at: " + SimpleDateFormat(
                    "dd/MM/yyyy hh:mm a",
                    Locale.ENGLISH
                ).format(Date(updatedAt * 1000))

                val weather_return = Weather().apply {

                    countryCode = country_code
                    cityName = city_name
                    temperature = temperature_api.toDouble()
                    humidity = hum.toInt()
                    windSpeed = wind_speed.toDouble()

                }
                weatherRepository.insert(weather_return)

            }
        }
    }

    fun getActualWeatherPreferablyDb(cityName: String) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            val dbWeather = weatherRepository.getWeatherByCityName(cityName)
            if (dbWeather != null) {
                // propagate the weather for actual day
                _weather.postValue(dbWeather)
            } else {
                //retrieve from API
                val response = weatherApiService.getDayWeatherByCity(apiKey, cityName)

                val weather = weatherApiToDb(response)
                if (weather != null) {
                    weatherRepository.insert(weather)

                    // propagate the weather for actual day
                    _weather.postValue(weather)
                }
            }
        }
    }

    fun getForecastFromApi(lat: Number, lon: Number) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            //retrieve from API
            val response = weatherApiService.getForecast(apiKey, lat, lon)

            val arr = forecastApiToDb(response)
            if (arr.size >= 6) {
                arr.forEach { forecast ->
                    if (
                        forecast.cityName != null &&
                        forecast.date_time != null &&
                        forecastRepository.getForecastByCityAndDate(
                            forecast.cityName!!,
                            forecast.date_time!!
                        ) == null
                    ) {
                        forecastRepository.insert(forecast)
                    } else {
                        var f = forecastRepository.getForecastByCityAndDate(forecast.cityName!!, forecast.date_time!!)
                        if (f != null) {
                            forecast.id = f.id
                        }
                        forecastRepository.update(forecast)
                    }

                }

                // propagate the forecast for actual day
                _forecast.postValue(arr.take(6).toTypedArray())
            }
        }
    }

    fun getForecastPreferablyDb(cityName: String) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            val arr = forecastRepository.getForecastByCityName(cityName)
            if (arr.size == 6) {
                // propagate the forecast for actual day
                _forecast.postValue(forecastRepository.getForecastByCityName(cityName))

            } else {
                //retrieve from API
                val response = weatherApiService.getForecastByCity(apiKey, cityName)

                val forecastArr = forecastApiToDb(response)
                if (forecastArr.size >= 6) {
                    // propagate the forecast for actual day
                    _forecast.postValue(forecastArr.take(6).toTypedArray())

                    forecastArr.take(6).toTypedArray().forEach { forecast ->
                        if (
                            forecast.cityName != null &&
                            forecast.date_time != null
                        ) {
                            if (
                                forecastRepository.getForecastByCityAndDate(
                                    forecast.cityName!!,
                                    forecast.date_time!!
                                ) == null
                            ) {
                                forecastRepository.insert(forecast)
                            } else {
                                forecastRepository.update(forecast)
                            }
                        }
                    }
                }
            }

        }
    }

    fun setWeatherList(cityNames: Array<String>, reloadAll : Boolean = false) {
        val weatherList = mutableListOf<Weather>()

        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            cityNames.forEach { cityName ->

                val dbWeather = weatherRepository.getWeatherByCityName(cityName)
                if (dbWeather != null && !reloadAll) {
                    weatherList.add(dbWeather)
                } else {
                    //retrieve from API
                    val response = weatherApiService.getDayWeatherByCity(apiKey, cityName)
                    val apiWeather = weatherApiToDb(response)
                    if (apiWeather != null) {
                        weatherList.add(apiWeather)

                        if (weatherRepository.getWeatherByStationId(apiWeather.stationId) == null) {
                            weatherRepository.insert(apiWeather)
                        } else {
                            weatherRepository.update(apiWeather)
                        }

                    }
                }
            }
            // propagate the weather for actual day
            _weatherList.postValue(weatherList)
        }
    }

    private fun weatherApiToDb(apiResponse: Response<ApiWeather>): Weather? {
        if (apiResponse.body()?.data?.get(0) != null) {
            val data = apiResponse.body()?.data!![0]
            val weather = Weather().apply {
                stationId = data.station.toString()
                actualized = data.actualized.toString()
                countryCode = data.country_code
                cityName = data.city_name
                temperature = data.temp?.toDouble()
                humidity = data.rh?.toInt()
                clouds = data.clouds?.toInt()
                sunrise = data.sunrise
                sunset = data.sunset
                uv = data.uv?.toInt()
                windSpeed = data.wind_spd?.toDouble()
                windDirection = data.wind_dir?.toInt()
                val weatherDescription_string = data.weather.toString()
                weatherDescription =
                    weatherDescription_string.substringAfter("description=").substringBefore('}')
                weatherCode = weatherDescription_string.substringAfter("code=").substringBefore(',')
                    .toDouble().toInt()

            }
            return weather
        }
        return null
    }

    private fun forecastApiToDb(apiResponse: Response<ApiForecast>): Array<Forecast> {
        val forecastList = emptyList<Forecast>().toMutableList()

        apiResponse.body()?.data?.take(6)?.forEach { it ->
            val forecast = Forecast().apply {
                actualized = Calendar.getInstance().getTime().toString()
                countryName = apiResponse.body()!!.country_code
                cityName = apiResponse.body()!!.city_name
                date_time = it.datetime
                temperature = it.temp?.toDouble()
                temperatureMin = it.min_temp?.toDouble()
                temperatureMax = it.max_temp?.toDouble()
                humidity = it.rh?.toInt()
                clouds = it.clouds?.toInt()
                sunrise = it.sunrise_ts?.toString()
                sunset = it.sunset_ts?.toString()
                uv = it.uv?.toInt()
                windSpeed = it.wind_spd?.toDouble()
                windDirection = it.wind_dir?.toInt()
                precipitation = it.precip?.toInt()
                precipitationProbability = it.pop?.toInt()
                weatherCode =
                    it.weather.toString().substringAfter("code=").substringBefore(',').toDouble()
                        .toInt()

            }
            forecastList += forecast
        }
        return forecastList.toTypedArray()
    }
}