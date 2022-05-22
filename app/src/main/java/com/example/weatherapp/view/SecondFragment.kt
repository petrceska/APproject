package com.example.weatherapp.view

import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_second.*
import androidx.recyclerview.widget.RecyclerView



class SecondFragment : Fragment (R.layout.fragment_second) {

    //Data to populate the recyclerview
    var cityList = mutableListOf(
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4)
    )

    GridLayoutManager(this, //Context 2, //Span count
    RecyclerView.VERTICAL, //orientation
    false //  reverse layout
    ).apply {
        // specify the layout manager for recycler view
        Recycler_ID.layoutManager = this
    }

    val adapter = cityAdapter(cityList)
    Recycler_ID.adapter = adapter

    //Recycler_ID.layoutManager = LinearLayoutManager(this)

    //Setup search function here
    //searchbar_id.
}