package com.example.mi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.mi.ui.theme.NaPasku
import com.example.mi.ui.theme.Pasek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasekSekcji(
    tytul: String,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = tytul,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Wstecz",
                    tint = NaPasku
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Pasek,
            titleContentColor = NaPasku
        )
    )
}
