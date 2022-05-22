package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


class ApiWeatherData {
    var id : Int = 0
    var actualized: Date = Calendar.getInstance().time
    var rh : Number? = null
    var pod : String? = null
    var lon : Number? = null
    var pres : Number? = null
    var timezone : String? = null
    var ob_time : String? = null
    var country_code : String? = null
    var clouds : Number? = null
    var ts : Number? = null
    var solar_rad : Number? = null
    var state_code : String? = null
    var city_name : String? = null
    var wind_spd : Number? = null
    var wind_cdir_full : String? = null
    var wind_cdir : String? = null
    var slp : Number? = null
    var vis : Number? = null
    var h_angle : Number? = null
    var sunset : String? = null
    var dni : Number? = null
    var dewpt : Number? = null
    var snow : Number? = null
    var uv : Number? = null
    var precip : Number? = null
    var wind_dir : Number? = null
    var sunrise : String? = null
    var ghi : Number? = null
    var dhi : Number? = null
    var aqi : Number? = null
    var lat : Number? = null
    var datetime : String? = null
    var temp : Number? = null
    var station : String? = null
    var elev_angle : Number? = null
    var app_temp : Number? = null
    var weather = object {
        var icon : String? = null
        var code : String? = null
        var description : String? = null
    }
}