package com.example.eseoapp

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.eseoapp.data.model.item.LocationItem
import com.example.eseoapp.data.model.preferences.LocalPreferences
import com.example.eseoapp.data.model.recycler.RecyclerLocationActivity
import com.example.eseoapp.data.model.recycler.RecyclerParameterActivity
import com.example.eseoapp.ui.main.permission.LocationActivity

class MainActivity : AppCompatActivity() {
    companion object{
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Affiche la vue de paramètre après click sur le bouton "settings"
        findViewById<ImageButton>(R.id.settings_main).setOnClickListener {
            startActivity(RecyclerParameterActivity.getStartIntent(this))
        }

        // Affiche la vue de localisation après click sur le bouton "Localisez-vous"
        findViewById<Button>(R.id.localisation_main).setOnClickListener {
            startActivity(LocationActivity.getStartIntent(this))
        }

        // Affiche la vue de la liste des localisation après click sur le bouton "Regardez où vous êtes allez"
        findViewById<Button>(R.id.histoLocal_main).setOnClickListener {
            startActivity(RecyclerLocationActivity.getStartIntent(this))
        }

    }
}