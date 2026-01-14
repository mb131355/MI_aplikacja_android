package com.example.mi.spiewnik

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mi.ui.theme.NaPasku
import com.example.mi.ui.theme.Pasek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkranSpiewnikLista(
    onWstecz: () -> Unit,
    onOtworzPiesn: (Int) -> Unit
) {
    val context = LocalContext.current
    val wszystkie = remember { SpiewnikRepozytorium.wczytajPiesni(context) }

    var query by remember { mutableStateOf("") }

    val przefiltrowane = remember(query, wszystkie) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            wszystkie
        } else {
            wszystkie.filter { p ->
                p.id.toString().contains(q) || p.tytul.lowercase().contains(q)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Śpiewnik", fontWeight = FontWeight.SemiBold) },
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
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Wyszukiwarka JUŻ bez strzałki
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Szukaj po nr lub tytule...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(24.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(przefiltrowane, key = { it.id }) { piesn ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                                .clickable { onOtworzPiesn(piesn.id) },
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(Icons.Filled.MusicNote, contentDescription = null)
                            Text(
                                text = "${piesn.id}. ${piesn.tytul}",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
