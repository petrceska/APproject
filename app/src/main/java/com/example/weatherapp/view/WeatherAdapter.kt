package com.example.weatherapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.database.Weather
import kotlinx.android.synthetic.main.city_view.view.*


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
            temp_view.text = citys[position].temperature.toString()
            city_View.text = citys[position].cityName
            country_View.text = citys[position].countryCode
            wind_View.text = citys[position].windSpeed.toString()
            humidity_View.text = citys[position].humidity.toString()
            //image_view.

        }
    }

    //Return the size of your dataset(invoked by the layout manager)
    override fun getItemCount(): Int {
        return citys.size
    }
}