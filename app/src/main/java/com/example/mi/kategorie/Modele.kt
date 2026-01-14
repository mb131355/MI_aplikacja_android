package com.example.mi.kategorie

import androidx.compose.ui.graphics.vector.ImageVector

data class Kafelek(
    val id: String,
    val tytul: String,
    val ikona: ImageVector,
    val link: String? = null
)
