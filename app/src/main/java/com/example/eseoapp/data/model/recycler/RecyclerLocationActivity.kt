package com.example.eseoapp.data.model.recycler

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eseoapp.R
import com.example.eseoapp.data.model.item.LocationItem
import com.example.eseoapp.ui.main.adapter.LocationAdapter
import com.example.eseoapp.data.model.preferences.LocalPreferences

class RecyclerLocationActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecyclerLocationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_location)

        // Création de l'action barre situé en haut de l'écran
        supportActionBar?.apply {
            setTitle(R.string.recyclerActivityLocatTitle)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Lecture de la liste des localisation enregistré dans l'historique sous le format de "LocationItem"
        var array = LocalPreferences.getInstance(this).getHistory()?.map {
            LocationItem(it, R.drawable.ic_baseline_location_on_24)
        }?.toTypedArray()?: run{
            emptyArray<LocationItem>()
        }

        // Écriture de la recycler view du "activity_recycler_location" avec le tableau de "LocationItem" rempli juste avant
        val rvDevices = findViewById<RecyclerView>(R.id.recylcer_location)
        rvDevices.layoutManager = LinearLayoutManager(this)
        rvDevices.adapter = LocationAdapter(array)

    }

    // Activation du bouton retour dans l'action barre
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}