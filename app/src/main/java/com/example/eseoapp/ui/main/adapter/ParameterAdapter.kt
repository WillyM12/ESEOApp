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

class ParameterAdapter(private val deviceList: Array<SettingsItem>) : RecyclerView.Adapter<ParameterAdapter.ViewHolder>()  {
    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(device: SettingsItem, onClick: ((selectedDevice: SettingsItem) -> Unit)? = null) {
            itemView.findViewById<TextView>(R.id.settings_text_list).text = device.name
            itemView.findViewById<ImageView>(R.id.settings_image_list).setImageResource(device.icon)

            if(device.onClick != null) {
                itemView.setOnClickListener {
                    device.onClick()
                }
            }
        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parametre_list, parent, false)
        return ViewHolder(view)
    }

    // Connecte la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(deviceList[position])
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }
}