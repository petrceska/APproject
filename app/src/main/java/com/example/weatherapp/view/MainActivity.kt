package com.example.weatherapp.view

//Imported for date

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NavUtils
import androidx.core.view.MotionEventCompat
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONObject
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    private val WeatherAppViewModel: WeatherAppViewModel by viewModels()

    private var firstRun = true

    // declare a global variable of FusedLocationProviderClient
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment) //init fragment
            commit() //first fragment is visible
        }

        btnFragment1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, firstFragment) //init fragment
                addToBackStack(null)
                commit() //first fragment is visible
            }
        }


        btnFragment2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, secondFragment) //init fragment
                addToBackStack(null)
                commit() //first fragment is visible
            }
        }

        WeatherAppViewModel.weatherRepository =
            (application as WeatherApplication).weatherRepository
        WeatherAppViewModel.forecastRepository =
            (application as WeatherApplication).forecastRepository

        // initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //TODO call it whenever you need to update weather
        if (firstRun) {
            setObserver()
            //Update weather based on actual location
            this.actualizeWeatherBasedOnLocation()
            firstRun = false
        }
    }


    private fun actualizeWeatherBasedOnLocation(actualizeLocation: Boolean = true) {
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
            if (actualizeLocation) {
                fusedLocationClient.getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY,
                    cts.token
                ).addOnSuccessListener { actLoc ->
                    if (actLoc != null) {
                        val lat = actLoc.latitude
                        val lon = actLoc.longitude

                        // successfully obtained location -> actualize weather
                        updateWeather(lat, lon)

                    }
                }
            } else {

                // get last known location if possible
                fusedLocationClient.lastLocation.addOnSuccessListener { lastLoc ->
                    if (lastLoc != null) {
                        val lat = lastLoc.latitude
                        val lon = lastLoc.longitude

                        // successfully obtained location -> actualize weather
                        updateWeather(lat, lon)

                    } else {
                        // retrieve actual location
                        fusedLocationClient.getCurrentLocation(
                            LocationRequest.PRIORITY_HIGH_ACCURACY,
                            cts.token
                        ).addOnSuccessListener { actLoc ->
                            if (actLoc != null) {
                                val lat = actLoc.latitude
                                val lon = actLoc.longitude

                                // successfully obtained location -> actualize weather
                                updateWeather(lat, lon)

                            }
                        }
                    }
                }
            }
        }
    }


    private fun updateWeather(lat: Number, lon: Number) {
        WeatherAppViewModel.getActualWeatherFromApi(lat, lon)
        Log.d(null, lat.toString() + lon.toString())
    }


    //check that location in phone is on
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun setObserver() {
        //actualize GUI after successful location and API retrieval
        WeatherAppViewModel.weather.observe(this) { weather ->
            if (weather != null) {

                //actualize GUI
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

                val calendar: Calendar = Calendar.getInstance()
                val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
                val dateTime = simpleDateFormat.format(calendar.time)
                date_ID.text = dateTime


                Location_ID.text = location_message
                //update the temperature
                //val temperature_Display = findViewById<TextView>(R.id.temp_ID)
                //temperature_Display.text = weather.temperature.toString()
                Log.i(null, weather.cityName.toString())

                //TODO make a set function for the main activity
                //val calendar: Calendar = Calendar.getInstance()
                //val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
                //val dateTime = simpleDateFormat.format(calendar.time)
                //date_ID.text = dateTime

                //Location_ID.text = weather.cityName.toString()
                val temperature = weather.temperature?.toInt()
//                temp_ID.text = temperature.toString() + "°"
                //Location_ID.text = weather.cityName.toString()
                //temp_ID.text = weather.temperature.toString()
                //Log.i(null, weather.cityName.toString())
                Log.i(null, weather.temperature.toString() + "°")
            }
        }
    }
}