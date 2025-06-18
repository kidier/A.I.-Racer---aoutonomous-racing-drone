package com.example.gpslocation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.gpslocation.data.LocationDatabaseHelper

/**
 * Displays saved locations in a simple list.
 */
class LocationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        val listView = findViewById<ListView>(R.id.location_list)
        val db = LocationDatabaseHelper(this)
        val data = db.getAll().map {
            "${it.id}: ${it.latitude}, ${it.longitude} - ${it.region ?: ""}"
        }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
    }
}
