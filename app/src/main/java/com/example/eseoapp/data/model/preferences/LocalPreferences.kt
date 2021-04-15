package com.example.eseoapp.data.model.preferences

import android.content.Context
import android.content.SharedPreferences

class LocalPreferences private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    // Ajoute le texte passé en paramètre dans un historique
    fun addToHistory(newEntry: String){
        val history = this.getHistory()
        history?.add(newEntry)
        sharedPreferences.edit().putStringSet("histories", history).apply()
    }

    // Retourne le tableau de texte présent dans l'historique
    fun getHistory(): MutableSet<String>?{
        return sharedPreferences.getStringSet("histories", mutableSetOf<String>().toMutableSet())
    }


    companion object {
        private var INSTANCE: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            return INSTANCE?.let {
                INSTANCE
            } ?: run {
                INSTANCE = LocalPreferences(context)
                return INSTANCE!!
            }
        }
    }
}