package com.example.mi.kalendarz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mi.ui.components.PasekSekcji

@Composable
fun EkranKalendarz(navController: NavHostController) {
    Scaffold(
        topBar = { PasekSekcji(tytul = "Kalendarz", navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Kalendarz – tu będzie zawartość.")
        }
    }
}
