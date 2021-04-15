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

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        //Demande la permission de me localiser après appui sur le bouton "Récupérer ma position"
        findViewById<Button>(R.id.get_local).setOnClickListener {
            this.requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Demande la permission si aucune réponse n'a déja été enregistré, sinon demande la localisation
    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }

    // Appel la fonction de localisation si la permission à été attribuer, sinon affiche un message indiquant que la permission n'est pas attibuée
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

    // Lors demande de permission (première demande de localisation
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // Si permission attibuée, on demande appel la fonction de localisation
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocation()
                } else {
                    // Permission refusé définitivement, on affiche un message qui indique l'impossibilité de de localiser tant qu'on n'a pas autorisé la localisation dans les paramètres
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        MaterialDialog(this).show {
                            title(R.string.permission_cancel)
                            message(R.string.permission_cancel_message)
                            positiveButton(R.string.ok) {
                                Toast.makeText(this@LocationActivity, getString(R.string.permission_cancel), Toast.LENGTH_LONG).show()
                            }
                        }
                    // Permission refusé, affiche un petit message qui indique l'impossibilité de de localiser
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

        //Récupère la localisation
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        // Ajoute la localisation dans l'historique des localisations
        LocalPreferences.getInstance(this).addToHistory(results[0].getAddressLine(0))
        val distance = FloatArray(1)

        // S'il y a un résultat de localisation
        if (results.isNotEmpty()) {
            findViewById<TextView>(R.id.location).text = results[0].getAddressLine(0)

            // Affiche la distance en kilomètre avec arrondi à 2 décimal entre ma postion et l'ESEO
            Location.distanceBetween(results[0].latitude, results[0].longitude, 47.49321221330138, -0.5512689999647891, distance)
            val distanceEseo = distance[0].div(1000)
            findViewById<TextView>(R.id.km_ESEO).text = distanceEseo.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString() + "km"

            // Affiche un message en fonction de la distance avec l'ESEO
            if(distanceEseo < 2){
                findViewById<TextView>(R.id.message_distance).text= getString(R.string.distance_message_u2)
            }
            else if(distanceEseo < 5){
                findViewById<TextView>(R.id.message_distance).text= getString(R.string.distance_message_u5)
            }
            else{
                findViewById<TextView>(R.id.message_distance).text= getString(R.string.dsitance_message_up_to5)
            }

        }

        // S'il n'y a pas de résultat de localisation
        else{
            findViewById<TextView>(R.id.location).text = getString(R.string.local_impossible_message)
            findViewById<TextView>(R.id.km_ESEO).text = getString(R.string.zero_km)
        }
    }
}

