package com.example.weatherapp



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//Imported for date
import android.widget.TextView
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    //Date variable
    lateinit var textView: TextView
    lateinit var date: String
    lateinit var simpleDateFormat : SimpleDateFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.date_ID)

    }


}