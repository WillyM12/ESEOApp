package com.example.eseoapp.data.model.recycler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eseoapp.R
import com.example.eseoapp.data.model.item.SettingsItem
import com.example.eseoapp.ui.main.adapter.ParameterAdapter

class RecyclerParameterActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecyclerParameterActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_parameter)

        // Création de l'action barre situé en haut de l'écran
        supportActionBar?.apply {
            setTitle(R.string.recyclerActivityParamTitle)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Remplissage d'un tableau sous le format de "SettingsItem" avec la configurarion souhaitée
        val array = arrayOf(
                SettingsItem(getString(R.string.parametres_appli), R.drawable.ic_baseline_settings_64) {
                    val targetIntent = Intent().apply {
                        action = Settings.ACTION_APPLICATION_SETTINGS
                    }

                    startActivity(targetIntent);
                },
                SettingsItem(getString(R.string.parametres_loc), R.drawable.ic_baseline_location_on_24) {
                    val targetIntent = Intent().apply {
                        action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    }
                    startActivity(targetIntent);
                },
                SettingsItem(getString(R.string.ESEO_loc), R.drawable.ic_baseline_map_24) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:47.49321221330138, -0.5512689999647891")));
                },
                SettingsItem(getString(R.string.ESEO_site), R.drawable.eseo) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr/")));
                },
                SettingsItem(getString(R.string.boite_mail), R.drawable.ic_baseline_email_24){
                    startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")));
                })

        // Écriture de la recycler view du "activity_recycler_parameter" avec le tableau de "SettingsItem" rempli juste avant
        val rvDevices = findViewById<RecyclerView>(R.id.recylcer_parameter)
        rvDevices.layoutManager = LinearLayoutManager(this)
        rvDevices.adapter = ParameterAdapter(array)
    }

    // Activation du bouton retour dans l'action barre
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}