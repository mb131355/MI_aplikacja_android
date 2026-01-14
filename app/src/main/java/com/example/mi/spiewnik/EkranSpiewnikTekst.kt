package com.example.mi.spiewnik

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mi.ui.theme.Pasek
import com.example.mi.ui.theme.NaPasku
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkranSpiewnikTekst(
    idPiesni: Int,
    onWstecz: () -> Unit
) {
    val context = LocalContext.current
    val piesn = remember(idPiesni) {
        SpiewnikRepozytorium.wczytajPiesni(context).firstOrNull { it.id == idPiesni }
    }

    var rozmiarTekstu by rememberSaveable { mutableStateOf(16f) } // pt jak na screenie

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(piesn?.tytul ?: "Pieśń", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onWstecz) {
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
        },
        bottomBar = {
            // Pasek na dole: zmniejsz / rozmiar / powiększ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { rozmiarTekstu = (rozmiarTekstu - 1f).coerceAtLeast(12f) }
                ) {
                    Icon(Icons.Filled.Remove, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Zmniejsz")
                }

                Text("Rozmiar: ${rozmiarTekstu.toInt()}pt")

                TextButton(
                    onClick = { rozmiarTekstu = (rozmiarTekstu + 1f).coerceAtMost(28f) }
                ) {
                    Text("Powiększ")
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        }
    ) { padding ->
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scroll)
                .fillMaxSize()
        ) {
            Text(
                text = piesn?.tekst ?: "Nie znaleziono pieśni o ID=$idPiesni",
                fontSize = rozmiarTekstu.sp,
                lineHeight = (rozmiarTekstu + 6).sp
            )
        }
    }
}
