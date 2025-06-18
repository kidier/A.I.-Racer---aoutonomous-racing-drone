package com.example.gpslocation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gpslocation.data.Location
import com.example.gpslocation.data.LocationDatabaseHelper

/**
 * Activity allowing manual input of GPS coordinates.
 */
class ManualLocationActivity : AppCompatActivity() {
    private lateinit var dbHelper: LocationDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        dbHelper = LocationDatabaseHelper(this)

        val latField = findViewById<EditText>(R.id.edit_latitude)
        val lonField = findViewById<EditText>(R.id.edit_longitude)
        val regionField = findViewById<EditText>(R.id.edit_region)

        findViewById<Button>(R.id.btn_save_manual).setOnClickListener {
            val lat = latField.text.toString().toDoubleOrNull()
            val lon = lonField.text.toString().toDoubleOrNull()
            if (lat != null && lon != null) {
                val loc = Location(
                    latitude = lat,
                    longitude = lon,
                    timestamp = System.currentTimeMillis(),
                    region = regionField.text.toString()
                )
                dbHelper.insert(loc)
                Toast.makeText(this, R.string.location_saved, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, R.string.invalid_coordinates, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
