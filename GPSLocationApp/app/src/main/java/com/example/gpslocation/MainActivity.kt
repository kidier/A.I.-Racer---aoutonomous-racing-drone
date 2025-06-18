package com.example.gpslocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gpslocation.data.LocationDatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: LocationDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = LocationDatabaseHelper(this)

        findViewById<Button>(R.id.btn_save_current).setOnClickListener {
            saveCurrentLocation()
        }
        findViewById<Button>(R.id.btn_add_manual).setOnClickListener {
            startActivity(Intent(this, ManualLocationActivity::class.java))
        }
        findViewById<Button>(R.id.btn_view_list).setOnClickListener {
            startActivity(Intent(this, LocationListActivity::class.java))
        }
    }

    private fun saveCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            return
        }
        val provider = android.location.LocationManager.GPS_PROVIDER
        val manager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
        val loc: Location? = manager.getLastKnownLocation(provider)
        if (loc != null) {
            val entry = com.example.gpslocation.data.Location(
                latitude = loc.latitude,
                longitude = loc.longitude,
                timestamp = System.currentTimeMillis(),
                region = null
            )
            dbHelper.insert(entry)
            Toast.makeText(this, R.string.location_saved, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.location_not_available, Toast.LENGTH_SHORT).show()
        }
    }
}
