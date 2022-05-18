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
import androidx.core.app.NavUtils
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val WeatherAppViewModel: WeatherAppViewModel by viewModels()

    // declare a global variable of FusedLocationProviderClient
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WeatherAppViewModel.weatherRepository = (application as WeatherApplication).weatherRepository
        WeatherAppViewModel.forecastRepository = (application as WeatherApplication).forecastRepository

        //TODO API instead
        val textView: TextView = findViewById(R.id.date_ID)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
        val dateTime = simpleDateFormat.format(calendar.time)
        textView.text = dateTime

        //Update weather based on actual location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //TODO call it whenever you need to update weather
        this.actualizeWeatherBasedOnLocation()

    }


    private fun actualizeWeatherBasedOnLocation() {
        // initialize FusedLocationProviderClient
        Log.i(null, "1")

        // check permissions of for retrieving location
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //ask for permissions of for retrieving location
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        }

        //check that location in phone is on
        if (isLocationEnabled() == true) {
            val cts = CancellationTokenSource()

            // get last known location if possible
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val lat = it.latitude
                    val lon = it.longitude
                    Log.i(null, lat.toString() + lon.toString())

                    // successfully obtained location -> actualize weather
                    showWeather(lat, lon)

                } else {
                    // there is not last known location -> retrieve actual location
                    fusedLocationClient.getCurrentLocation(
                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                        cts.token
                    ).addOnSuccessListener {
                        if (it != null) {
                            val lat = it.latitude
                            val lon = it.longitude
                            Log.i(null, lat.toString() + lon.toString())

                            // successfully obtained location -> actualize weather
                            showWeather(lat, lon)

                        }
                    }
                }
            }
        }
    }


    //actualize GUI after successful location retrieval
    private fun showWeather(lat: Number, lon: Number) {
        WeatherAppViewModel.getActualWeatherFromApi(lat, lon)
        Log.i(null, lat.toString() + lon.toString())

        WeatherAppViewModel.weather.observe(this) { weather ->
            if (weather != null) {

                //actualize GUI after successful api or database call
                //TODO here you can actualize GUI of the app
                //show the location
                var loc = Locale("", weather.countryCode.toString())
                //var loc = Locale("", "DK")
                var countryName = loc.displayCountry
                Log.i(null, countryName.toString())
                var location_message = weather.cityName.toString() + ", " + countryName
                if (countryName == "NULL") {
                    location_message = weather.cityName.toString()
                }
                val location_Display = findViewById<TextView>(R.id.Location_ID)
                location_Display.text = location_message
                //update the temperature
                //val temperature_Display = findViewById<TextView>(R.id.temp_ID)
                //temperature_Display.text = weather.temperature.toString()
                Log.i(null, weather.cityName.toString())

                //Log.i(null, weather.cityName.toString())
                //Log.i(null, weather.temperature.toString())
            }
        }
    }



    //check that location in phone is on
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}