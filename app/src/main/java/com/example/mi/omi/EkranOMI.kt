package com.example.mi.omi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mi.ui.components.PasekSekcji
import androidx.compose.material3.Scaffold



@Composable
fun EkranOMI(navController: NavHostController) {
    Scaffold(
        topBar = {
            PasekSekcji(
                tytul = "O M.I.",
                navController = navController
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Tu będzie opis aplikacji.")
        }
    }
}
