package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import androidx.activity.viewModels

//Imported for date
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val WeatherAppViewModel: WeatherAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.date_ID)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
        val dateTime = simpleDateFormat.format(calendar.time)
        textView.text = dateTime

        WeatherAppViewModel.dayWeather.observe(
            this
        ) { test ->
            Log.i(null, test)
        }
    }


}