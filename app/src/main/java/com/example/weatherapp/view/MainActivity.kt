package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val WeatherAppViewModel: WeatherAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherAppViewModel.dayWeather.observe(
            this
        ) { test ->
            Log.i(null, test)
        }
    }
}