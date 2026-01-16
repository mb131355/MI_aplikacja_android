package com.example.mi.notatnik

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mi.ui.components.PasekSekcji

@Composable
fun EkranNotatnik(navController: NavHostController) {
    Scaffold(
        topBar = { PasekSekcji(tytul = "Notatnik", navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Notatnik – tu będzie zawartość.")
        }
    }
}
