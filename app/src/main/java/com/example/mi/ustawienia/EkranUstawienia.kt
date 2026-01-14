package com.example.mi.ustawienia

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mi.ui.theme.NaPasku
import com.example.mi.ui.theme.Pasek
import kotlinx.coroutines.launch

@Composable
fun EkranUstawienia() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val ciemny by UstawieniaDataStore.ciemnyMotywFlow(context)
        .collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Ustawienia", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)

        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Tryb ciemny", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text("Włącz ciemny motyw aplikacji")
                }

                Switch(
                    checked = ciemny,
                    onCheckedChange = { nowaWartosc ->
                        scope.launch {
                            UstawieniaDataStore.ustawCiemnyMotyw(context, nowaWartosc)
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Pasek,
                        checkedThumbColor = NaPasku,
                        uncheckedTrackColor = MaterialTheme.colorScheme.outline,
                        uncheckedThumbColor = MaterialTheme.colorScheme.surface
                    )
                )

            }
        }
    }
}
