package com.example.eseoapp.data.model.item

// Définition du type d'item à afficher sur la recycle activity de paramètres
data class SettingsItem(val name: String, val icon: Int, val onClick: (() -> Unit))