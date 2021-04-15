package com.example.eseoapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eseoapp.R
import com.example.eseoapp.data.model.item.LocationItem
import com.example.eseoapp.data.model.item.SettingsItem

class LocationAdapter(private val deviceList: Array<LocationItem>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(device: LocationItem) {
            itemView.findViewById<TextView>(R.id.location_text_list).text = device.name
            itemView.findViewById<ImageView>(R.id.location_image_list).setImageResource(device.icon)

        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_list, parent, false)
        return ViewHolder(view)
    }

    // Connecte la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(deviceList[position])
    }

    // Retourne le nombre d'item de la liste
    override fun getItemCount(): Int {
        return deviceList.size
    }
}