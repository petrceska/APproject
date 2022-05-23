package com.example.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.database.Weather
import com.example.weatherapp.viewmodel.WeatherAppViewModel
import com.example.weatherapp.WeatherApplication
import kotlinx.android.synthetic.main.fragment_second.*


class SecondFragment(vm: WeatherAppViewModel) : Fragment(R.layout.fragment_second) {

    private val viewModel = vm

    private var adapter: RecyclerView.Adapter<WeatherAdapter.CityViewHolder>? = null
    //private var : mutableListOf<ObjectsfromDatabase>()

    //Data to populate the recyclerview
    private var cityList: List<Weather> = listOf(Weather().apply { cityName = "Loading" })

    //https://www.youtube.com/watch?v=l1N9Onp5EKo
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        setObserver()

        recyclerview_id.apply {
            Log.d(null, "inApply----")
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = GridLayoutManager(this@SecondFragment.context, 2)
            // set the custom adapter to the RecyclerView
            adapter = WeatherAdapter(cityList)
        }

    }


    private fun setObserver() {
        //actualize GUI after successful location and API retrieval
        viewModel.weatherList.observe(viewLifecycleOwner) { weatherList ->
            if (weatherList != null) {

                cityList = weatherList

                adapter = WeatherAdapter(cityList)

                recyclerview_id.swapAdapter(adapter, false)

            }
        }

        viewModel.setWeatherList(
            arrayOf(
                "Prague",
                "Oslo",
                "Helsinki",
                "Rio",
                "Paris",
                "London",
                "Brno",
                "Odense",
                "Madrid",
                "Barcelona",
                "Odense"
            )
        )
    }
}
