package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.city_view.*
import kotlinx.android.synthetic.main.fragment_second.*


class SecondFragment : Fragment (R.layout.fragment_second) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<cityAdapter.CityViewHolder>? = null

    //Data to populate the recyclerview
    var cityList = mutableListOf(
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerview_id.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = cityAdapter(cityList)

        }
    }





}








    //Recycler_ID.layoutManager = LinearLayoutManager(this)

    //Setup search function here
    //searchbar_id.
