package com.example.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.database.Weather
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import kotlinx.android.synthetic.main.city_view.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import java.util.*

// TODO 1.select all, set Adapter,
class SecondFragment : Fragment(R.layout.fragment_second) {

    private var adapter: RecyclerView.Adapter<WeatherAdapter.CityViewHolder>? = null
    //private var : mutableListOf<ObjectsfromDatabase>()

    //Data to populate the recyclerview
    var cityList: List<Weather>? = null

    var viewModel: WeatherAppViewModel? = null

    //https://www.youtube.com/watch?v=l1N9Onp5EKo
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        setObserver()
        viewModel?.setWeatherList(arrayOf("Prague", "Paris", "London", "Copenhagen", "Madrid", "Barcelona", ))
    }


    private fun setObserver() {
        //actualize GUI after successful location and API retrieval
        viewModel?.weatherList?.observe(viewLifecycleOwner) { weatherList ->
            if (weatherList != null) {

                cityList = weatherList

                recyclerview_id.apply {
                    // set a LinearLayoutManager to handle Android
                    // RecyclerView behavior
                    layoutManager = GridLayoutManager(this@SecondFragment.context, 2)
                    // set the custom adapter to the RecyclerView
                    adapter = WeatherAdapter(cityList!!)

                }
            }
        }
    }
}
