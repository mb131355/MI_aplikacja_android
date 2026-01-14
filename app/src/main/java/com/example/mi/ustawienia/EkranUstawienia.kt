package com.example.mi.ustawienia

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mi.powiadomienia.HarmonogramPowiadomien
import com.example.mi.powiadomienia.Notyfikacje
import com.example.mi.ui.theme.NaPasku
import com.example.mi.ui.theme.Pasek
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun EkranUstawienia() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    /* ===================== POWIADOMIENIA – ZGODA (ANDROID 13+) ===================== */

    val prosbaOPozwolenie = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }

    val maPozwolenieNaNotyfikacje = remember {
        mutableStateOf(
            Build.VERSION.SDK_INT < 33 ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    /* ===================== STANY Z DATASTORE ===================== */

    val ciemny by UstawieniaDataStore
        .ciemnyMotywFlow(context)
        .collectAsState(initial = false)

    val powiadomieniaWlaczone by UstawieniaDataStore
        .powiadomieniaWlaczoneFlow(context)
        .collectAsState(initial = false)

    val godzina by UstawieniaDataStore
        .godzinaFlow(context)
        .collectAsState(initial = 9)

    val minuta by UstawieniaDataStore
        .minutaFlow(context)
        .collectAsState(initial = 0)

    /* ===================== UI ===================== */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            "Ustawienia",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        /* ===================== TRYB CIEMNY ===================== */

        Card(
            onClick = {
                scope.launch {
                    UstawieniaDataStore.ustawCiemnyMotyw(context, !ciemny)
                }
            }
        ) {
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
                    onCheckedChange = { nowa ->
                        scope.launch {
                            UstawieniaDataStore.ustawCiemnyMotyw(context, nowa)
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

        /* ===================== BRAK ZGODY NA POWIADOMIENIA ===================== */

        if (Build.VERSION.SDK_INT >= 33 && !maPozwolenieNaNotyfikacje.value) {
            Card {
                Column(Modifier.padding(14.dp)) {
                    Text("Powiadomienia są wyłączone", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text("Aby działały przypomnienia, musisz zezwolić aplikacji na wysyłanie powiadomień.")
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            prosbaOPozwolenie.launch(Manifest.permission.POST_NOTIFICATIONS)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Zezwól na powiadomienia")
                    }
                }
            }
        }

        /* ===================== POWIADOMIENIA O ZDRAPCE ===================== */

        Card(
            onClick = {
                scope.launch {
                    val nowa = !powiadomieniaWlaczone
                    UstawieniaDataStore.ustawPowiadomieniaZdrapki(context, nowa)

                    if (nowa) {
                        HarmonogramPowiadomien.zaplanujCodziennie(context, godzina, minuta)
                    } else {
                        HarmonogramPowiadomien.anuluj(context)
                    }
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Powiadomienia o zdrapce", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text("Codziennie przypomnij, żeby zdrapać nową zdrapkę.")
                    Spacer(Modifier.height(6.dp))
                    Text("Godzina: %02d:%02d".format(godzina, minuta))
                }

                Switch(
                    checked = powiadomieniaWlaczone,
                    onCheckedChange = { nowa ->
                        scope.launch {
                            UstawieniaDataStore.ustawPowiadomieniaZdrapki(context, nowa)
                            if (nowa) {
                                HarmonogramPowiadomien.zaplanujCodziennie(context, godzina, minuta)
                            } else {
                                HarmonogramPowiadomien.anuluj(context)
                            }
                        }
                    }
                )
            }
        }

        /* ===================== USTAW GODZINĘ ===================== */

        Button(
            onClick = {
                TimePickerDialog(
                    context,
                    { _, h, m ->
                        scope.launch {
                            UstawieniaDataStore.ustawGodzinePowiadomien(context, h, m)
                            if (powiadomieniaWlaczone) {
                                HarmonogramPowiadomien.zaplanujCodziennie(context, h, m)
                            }
                        }
                    },
                    godzina,
                    minuta,
                    true
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ustaw godzinę powiadomień")
        }

        /* ===================== TEST ===================== */

        OutlinedButton(
            onClick = { Notyfikacje.pokazPowiadomienieZdrapki(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Wyślij testowe powiadomienie")
        }
    }
}
