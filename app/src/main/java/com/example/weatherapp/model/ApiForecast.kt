package com.example.weatherapp.model

class ApiForecast {
    var city_name: String? = null
    var lon: String? = null
    var timezone: String? = null
    var lat: String? = null
    var country_code: String? = null
    var state_code: Number? = null
    var data: Array<ApiForecastData>? = null
}