package com.example.eseoapp.ui.main.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.example.eseoapp.R
import com.example.eseoapp.data.model.preferences.LocalPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import java.math.RoundingMode
import java.util.*

class LocationActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_LOCATION= 9999
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocationActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        findViewById<Button>(R.id.get_local).setOnClickListener {
            this.requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        if (hasPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                    .addOnSuccessListener { geoCode(it) }
                    .addOnFailureListener {
                        // Remplacer par un vrai bon message
                        Toast.makeText(this, getString(R.string.permission_cancel), Toast.LENGTH_SHORT).show()
                    }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        MaterialDialog(this).show {
                            title(R.string.permission_cancel)
                            message(R.string.permission_cancel_message)
                            positiveButton(R.string.ok) {
                                Toast.makeText(this@LocationActivity, getString(R.string.permission_cancel), Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.permission_cancel), Toast.LENGTH_LONG).show()
                    }
                }
                return
            }
        }
    }

    private fun geoCode(location: Location){
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val distance = FloatArray(1)
        if (results.isNotEmpty()) {
            findViewById<TextView>(R.id.location).text = results[0].getAddressLine(0)
            Location.distanceBetween(results[0].latitude, results[0].longitude, 47.49321221330138, -0.5512689999647891, distance)
            findViewById<TextView>(R.id.km_ESEO).text = distance[0].div(1000).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString() + "km"
            LocalPreferences.getInstance(this).addToHistory(results[0].getAddressLine(0))
        }
        else{
            findViewById<TextView>(R.id.location).text = getString(R.string.local_impossible_message)
            findViewById<TextView>(R.id.km_ESEO).text = getString(R.string.zero_km)
        }
    }
}