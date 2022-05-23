package com.example.weatherapp.view

//Imported for date

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.fragment.app.FragmentManager.TAG
import androidx.fragment.app.FragmentTransaction
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.city_view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONObject
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    val WeatherAppViewModel: WeatherAppViewModel by viewModels()

    private var firstRun = true

    // declare a global variable of FusedLocationProviderClient
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        WeatherAppViewModel.weatherRepository =
            (application as WeatherApplication).weatherRepository
        WeatherAppViewModel.forecastRepository =
            (application as WeatherApplication).forecastRepository

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment(WeatherAppViewModel)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment) //init fragment
            commit() //first fragment is visible
        }

        btnFragment1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                hide(secondFragment)
                show(firstFragment)
                addToBackStack(TAG)
                commit()
//                replace(R.id.flFragment, firstFragment) //init fragment
//                addToBackStack(null)
//                commit() //first fragment is visible
            }
        }

        var fragment_two = false
        btnFragment2.setOnClickListener {
            if (!fragment_two) {
                supportFragmentManager.beginTransaction().apply {
                    hide(firstFragment)
                    add(R.id.flFragment, secondFragment)
                    addToBackStack(TAG)
                    commit()
                }
                fragment_two = true
            } else {
                supportFragmentManager.beginTransaction().apply {
                    hide(firstFragment)
                    show(secondFragment)
                    addToBackStack(TAG)
                    commit()
//                replace(R.id.flFragment, secondFragment) //init fragment
//                addToBackStack(null)
//                commit() //first fragment is visible
                }
            }
        }

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
        WeatherAppViewModel.getForecastFromApi(lat, lon)
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

                //Date update view
                //val calendar: Calendar = Calendar.getInstance()
                //val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
                //val dateTime = simpleDateFormat.format(calendar.time)

                //Date update view
                date_ID.text = weather.actualized.toString()

                //Location update
                Location_ID.text = location_message

                //Temp update
                temp_ID.text = weather.temperature.toString() + " ° "

                //Humidity update
                humidity_ID.text = weather.humidity.toString() + " % " + "\nHumidity"

                //UV update
                UV_ID.text = weather.uv.toString() + "\nUV-Index"

                //Wind Update
                wind_ID.text = weather.windSpeed.toString() + " km/h " + "\nWind"

                //Description Update
                description_ID.text = weather.weatherDescription.toString()

                //Logo Update
                imageView.setImageResource(getImgByCode(weather.weatherCode))
                //imageView.setImageResource()

                //Update of textview and imageview
                //call function here when its done
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Error: Cannot get the data from the internet. Please try again later.")
                builder.setTitle("Error")
                builder.setPositiveButton("OK", null)
                builder.show()
            }
        }




        //actualize GUI after successful location and API retrieval
        WeatherAppViewModel.forecast.observe(this) { forecast ->
            if (forecast != null) {
                Log.d(null, "BB sees you")
                //var


            }
            //var loc = Locale("", forecast. )

            //Updates forecast days
            day_ID1.text = forecast[0].date_time.toString().drop(5)
            day_ID2.text = forecast[1].date_time.toString().drop(5)
            day_ID3.text = forecast[2].date_time.toString().drop(5)
            day_ID4.text = forecast[3].date_time.toString().drop(5)
            day_ID5.text = forecast[4].date_time.toString().drop(5)
            day_ID6.text = forecast[5].date_time.toString().drop(5)

            //Update forecast temp
            temp_ID1.text = forecast[0].temperature.toString()
            temp_ID2.text = forecast[1].temperature.toString()
            temp_ID3.text = forecast[2].temperature.toString()
            temp_ID4.text = forecast[3].temperature.toString()
            temp_ID5.text = forecast[4].temperature.toString()
            temp_ID6.text = forecast[5].temperature.toString()

            //Update  imageview
            forecast_ID1.setImageResource(getImgByCode(forecast[0].weatherCode))
            forecast_ID2.setImageResource(getImgByCode(forecast[1].weatherCode))
            forecast_ID3.setImageResource(getImgByCode(forecast[2].weatherCode))
            forecast_ID4.setImageResource(getImgByCode(forecast[3].weatherCode))
            forecast_ID5.setImageResource(getImgByCode(forecast[4].weatherCode))
            forecast_ID6.setImageResource(getImgByCode(forecast[5].weatherCode))

            //call function here when its done

        }
    }

    fun getImgByCode(code: Int?): Int {
        when (code) {
            202, 232 -> return R.drawable.thunderstorm_heavy_rain
            201, 231 -> return R.drawable.thunderstorm_rain
            200, 230 -> return R.drawable.thunderstormlightrain
            233 -> return R.drawable.thunderstorm_hail
            300, 301, 302, 500, 501, 502, 511, 520, 521, 522 -> return R.drawable.rain
            600 -> return R.drawable.lightsnow
            601, 602 -> return R.drawable.snow
            610 -> return R.drawable.mixsnowandrain
            611, 612 -> return R.drawable.sleet
            621, 622, 623 -> return R.drawable.snowshower
            700, 711, 721, 731, 741, 751 -> return R.drawable.mist
            800 -> return R.drawable.clearsky
            801, 802 -> return R.drawable.fewclouds
            803 -> return R.drawable.brokenclouds
            804 -> return R.drawable.overcastclouds
            else -> return R.drawable.unknown
        }
    }

}