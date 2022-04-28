package com.example.weatherapp.view

//Imported for date

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val WeatherAppViewModel: WeatherAppViewModel by viewModels()
    var lat: Number = 49
    var lon: Number = 16

    // declare a global variable of FusedLocationProviderClient
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.date_ID)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
        val dateTime = simpleDateFormat.format(calendar.time)
        textView.text = dateTime

        //Update weather based on actual location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        this.getLocation()

    }


    private fun getLocation() {
        // initialize FusedLocationProviderClient
        Log.i(null, "1")
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        }


        if (isLocationEnabled() == true) {
            val cts = CancellationTokenSource()


            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    lat = it.latitude
                    lon = it.longitude
                    Log.i(null, lat.toString() + lon.toString())
                    showWeather()

                } else {

                    fusedLocationClient.getCurrentLocation(
                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                        cts.token
                    ).addOnSuccessListener {
                        if (it != null) {
                            lat = it.latitude
                            lon = it.longitude
                            Log.i(null, lat.toString() + lon.toString())
                            showWeather()

                        }
                    }
                }
            }
        }
    }

    private fun showWeather() {
        WeatherAppViewModel.getDayWeather(lat, lon)
        Log.i(null, lat.toString() + lon.toString())

        WeatherAppViewModel.dayWeather.observe(this) { dayWeather ->
            if (dayWeather?.data != null) {
                Log.i(null, dayWeather.data!![0].city_name.toString())
            }
        }
    }


    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}