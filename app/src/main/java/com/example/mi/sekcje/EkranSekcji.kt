package com.example.mi.sekcje

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.example.mi.omi.EkranOMI
import com.example.mi.notatnik.EkranNotatnik
import com.example.mi.kalendarz.EkranKalendarz
import com.example.mi.zdrapki.EkranZdrapki

@Composable
fun EkranSekcji(
    id: String,
    navController: NavHostController
) {

    when (id) {
        "omi" -> EkranOMI(navController)
        "notatnik" -> EkranNotatnik(navController)
        "kalendarz" -> EkranKalendarz(navController)
        "zdrapki" -> EkranZdrapki(navController)
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                Text("Nieznana sekcja:", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(text = id, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}
