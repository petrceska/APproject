package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.city_view.view.*


class cityAdapter (var citys: List <City>) : RecyclerView.Adapter<cityAdapter.CityViewHolder> ()
{
    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_view, parent, false)
        return CityViewHolder(view)
    }

    //Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CityViewHolder, position: Int)
    {
        //Set view here
        holder.itemView.apply {
            temp_view.text = citys[position].temp.toString()
            city_View.text = citys[position].city
            country_View.text = citys[position].country
            wind_View.text = citys[position].wind.toString()
            humidity_View.text = citys[position].humidity.toString()
            //image_view.

        }
    }

    //Return the size of your dataset(invoked by the layout manager)
    override fun getItemCount(): Int {
        return citys.size
    }
}