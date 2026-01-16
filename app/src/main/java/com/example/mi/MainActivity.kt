package com.example.mi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.mi.ui.theme.MiTheme
import com.example.mi.aktualnosci.EkranAktualnosci
import com.example.mi.kategorie.EkranKategorie
import com.example.mi.nawigacja.Trasy
import com.example.mi.sekcje.EkranSekcji
import com.example.mi.ustawienia.EkranUstawienia
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import com.example.mi.spiewnik.EkranSpiewnikLista
import com.example.mi.spiewnik.EkranSpiewnikTekst
import com.example.mi.ui.theme.Pasek
import com.example.mi.ui.theme.NaPasku


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = this
            val ciemny by com.example.mi.ustawienia.UstawieniaDataStore
                .ciemnyMotywFlow(context)
                .collectAsState(initial = false)

            MiTheme(ciemnyMotyw = ciemny) {
                Aplikacja()
            }
        }

    }
}

@Composable
private fun Aplikacja() {
    val navController = rememberNavController()

    val aktualnaTrasa = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val aktualnaTrasa = navController.currentBackStackEntryAsState().value?.destination?.route

            val wSpiewniku = aktualnaTrasa == Trasy.SPIEWNIK_LISTA ||
                    (aktualnaTrasa?.startsWith("spiewnik_tekst/") == true)

            val wSekcji = aktualnaTrasa?.startsWith("sekcja/") == true

            if (!wSpiewniku && !wSekcji) {
                GornyPasek(navController)
            }
        },
        bottomBar = { DolnaNawigacja(navController) }

    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Trasy.KATEGORIE,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(Trasy.AKTUALNOSCI) { EkranAktualnosci() }
            composable(Trasy.KATEGORIE) { EkranKategorie(
                onKlikKafelek = { id ->
                    if (id == "spiewnik") {
                        navController.navigate(Trasy.SPIEWNIK_LISTA)
                    } else {
                        navController.navigate(Trasy.sekcja(id))
                    }
                }
            )
            }
            composable(Trasy.USTAWIENIA) { EkranUstawienia() }

            composable(Trasy.SEKCA) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                EkranSekcji(
                    id = id,
                    navController = navController
                )
            }
            composable(Trasy.SPIEWNIK_LISTA) {
                EkranSpiewnikLista(
                    onWstecz = { navController.popBackStack() },
                    onOtworzPiesn = { id -> navController.navigate(Trasy.spiewnikTekst(id)) }
                )
            }

            composable(Trasy.SPIEWNIK_TEKST) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                EkranSpiewnikTekst(
                    idPiesni = id,
                    onWstecz = { navController.popBackStack() }
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GornyPasek(navController: NavHostController) {
    val aktualnaTrasa = navController.currentBackStackEntryAsState().value?.destination?.route

    val tytul = when {
        aktualnaTrasa == Trasy.AKTUALNOSCI -> "Aktualności"
        aktualnaTrasa == Trasy.KATEGORIE -> "Kategorie"
        aktualnaTrasa == Trasy.USTAWIENIA -> "Ustawienia"

        aktualnaTrasa == Trasy.SPIEWNIK_LISTA -> "Śpiewnik"
        aktualnaTrasa?.startsWith("spiewnik_tekst/") == true -> "Śpiewnik"

        aktualnaTrasa?.startsWith("sekcja/") == true -> "Sekcja"
        else -> "Aplikacja"
    }

    CenterAlignedTopAppBar(
        title = { Text(text = tytul, fontWeight = FontWeight.SemiBold, color = NaPasku) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Pasek,
            titleContentColor = NaPasku
        )
    )

}

private data class ZakladkaDolna(
    val trasa: String,
    val etykieta: String,
    val ikona: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun DolnaNawigacja(navController: NavHostController) {
    val zakladki = listOf(
        ZakladkaDolna(Trasy.AKTUALNOSCI, "Aktualności", Icons.Filled.List),
        ZakladkaDolna(Trasy.KATEGORIE, "Kategorie", Icons.Filled.GridView),
        ZakladkaDolna(Trasy.USTAWIENIA, "Ustawienia", Icons.Filled.Settings),
    )

    val aktualnaTrasa = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Pasek
    ) {
        zakladki.forEach { zakladka ->
            val zaznaczone = aktualnaTrasa == zakladka.trasa

            NavigationBarItem(
                selected = zaznaczone,
                onClick = {
                    navController.navigate(zakladka.trasa) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(zakladka.ikona, contentDescription = zakladka.etykieta) },
                label = { Text(zakladka.etykieta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NaPasku,
                    selectedTextColor = NaPasku,
                    unselectedIconColor = NaPasku,
                    unselectedTextColor = NaPasku,
                    indicatorColor = Pasek // żeby nie robił się jasny „bąbelek”
                )
            )

        }
    }
}
