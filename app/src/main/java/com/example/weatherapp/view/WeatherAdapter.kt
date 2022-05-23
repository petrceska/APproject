package com.example.weatherapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.database.Weather
import kotlinx.android.synthetic.main.city_view.view.*
import kotlinx.android.synthetic.main.fragment_first.*


class WeatherAdapter (var citys: List <Weather>) : RecyclerView.Adapter<WeatherAdapter.CityViewHolder> ()
{
    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val myData = mutableListOf<Weather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        myData.addAll(citys)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_view, parent, false)
        return CityViewHolder(view)
    }

    //Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CityViewHolder, position: Int)
    {
        //Set view here
        holder.itemView.apply {
            temp_view.text = citys[position].temperature.toString() + "° "
            city_View.text = citys[position].cityName + " " + citys[position].countryCode
            //country_View.text = citys[position].countryCode
            wind_View.text = citys[position].windSpeed.toString() + " km/h"
            humidity_View.text = citys[position].humidity.toString() + " % "
            when(citys[position].weatherCode?.toInt()) {
                202,232 -> image_view.setImageResource(R.drawable.thunderstorm_heavy_rain)
                201,231 -> image_view.setImageResource(R.drawable.thunderstorm_rain)
                200,230 -> image_view.setImageResource(R.drawable.thunderstormlightrain)
                233 -> image_view.setImageResource(R.drawable.thunderstorm_hail)
                300,301,302,500,501,502,511,520,521,522 -> image_view.setImageResource(R.drawable.rain)
                600 -> image_view.setImageResource(R.drawable.lightsnow)
                601,602 -> image_view.setImageResource(R.drawable.snow)
                610 -> image_view.setImageResource(R.drawable.mixsnowandrain)
                611,612 -> image_view.setImageResource(R.drawable.sleet)
                621,622,623 -> image_view.setImageResource(R.drawable.snowshower)
                700,711,721,731,741,751 -> image_view.setImageResource(R.drawable.mist)
                800 -> image_view.setImageResource(R.drawable.clearsky)
                801,802 -> image_view.setImageResource(R.drawable.fewclouds)
                803 -> image_view.setImageResource(R.drawable.brokenclouds)
                804 -> image_view.setImageResource(R.drawable.overcastclouds)
                else -> image_view.setImageResource(R.drawable.unknown)
            }
            //image_view.


        }
    }

    //Return the size of your dataset(invoked by the layout manager)
    override fun getItemCount(): Int {
        return citys.size
    }
}