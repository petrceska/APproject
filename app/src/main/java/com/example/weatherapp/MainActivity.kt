package com.example.weatherapp



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//Imported for date
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.date_ID)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM ")
        val dateTime = simpleDateFormat.format(calendar.time)
        textView.text = dateTime

    }


}