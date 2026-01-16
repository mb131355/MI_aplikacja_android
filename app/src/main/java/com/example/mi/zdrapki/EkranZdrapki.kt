package com.example.mi.zdrapki

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mi.ui.components.PasekSekcji

@Composable
fun EkranZdrapki(navController: NavHostController) {
    Scaffold(
        topBar = { PasekSekcji(tytul = "Zdrapki", navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Zdrapki – tu będzie zawartość.")
        }
    }
}
