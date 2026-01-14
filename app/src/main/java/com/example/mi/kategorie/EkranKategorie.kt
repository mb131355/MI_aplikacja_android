package com.example.mi.kategorie

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EkranKategorie(onKlikKafelek: (String) -> Unit) {
    val context = LocalContext.current

    val kafelki = remember {
        listOf(
            Kafelek("omi", "O.M.I.", Icons.Filled.Info),
            Kafelek("spiewnik", "ŚPIEWNIK", Icons.Filled.MusicNote),
            Kafelek("notatnik", "NOTATNIK", Icons.Filled.Description),

            Kafelek("kalendarz", "Kalendarz", Icons.Filled.DateRange),
            Kafelek("zdrapki", "Zdrapki", Icons.Filled.ConfirmationNumber),
            Kafelek("mi_today", "mi-today.pl", Icons.Filled.AutoFixHigh, link = "https://www.mi-today.pl/"),

            Kafelek("youtube", "YOUTUBE", Icons.Filled.PlayArrow, link = "https://www.youtube.com/@niechMIsi%C4%99stanie"),
            Kafelek("instagram", "INSTAGRAM", Icons.Filled.CameraAlt, link = "https://www.instagram.com/mlodziez.mi/"),
            Kafelek("facebook", "FACEBOOK", Icons.Filled.ThumbUp, link = "https://www.facebook.com/profile.php?id=100064346486213"),
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(kafelki, key = { it.id }) { kafelek ->
            KafelekWidok(
                kafelek = kafelek,
                onClick = {
                    if (kafelek.link != null) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kafelek.link))
                        context.startActivity(intent)
                    } else {
                        onKlikKafelek(kafelek.id)
                    }
                }
            )
        }
    }
}
