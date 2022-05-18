package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

    //Data to populate the recyclerview
    var cityList = mutableListOf(
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4),
        City(29,"Odense","Denmark",4,4.4)
    )
    //TODO Implement GRIDVIEW
    // Set upt the Recyclerview
    GridLayoutManager(
        this, //Context
        2, //Span count
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
}