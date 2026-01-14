package com.example.mi.powiadomienia

import android.content.Context
import androidx.work.*
import java.util.Calendar
import java.util.concurrent.TimeUnit

object HarmonogramPowiadomien {

    private const val UNIKALNA_NAZWA = "codzienna_zdrapka"

    fun zaplanujCodziennie(context: Context, godzina: Int, minuta: Int) {
        // Najbliższa data o (godzina:minuta)
        val teraz = Calendar.getInstance()
        val cel = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, godzina)
            set(Calendar.MINUTE, minuta)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(teraz)) add(Calendar.DAY_OF_YEAR, 1)
        }

        val opoznienieMs = cel.timeInMillis - teraz.timeInMillis

        val request = PeriodicWorkRequestBuilder<PracownikZdrapki>(24, TimeUnit.HOURS)
            .setInitialDelay(opoznienieMs, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIKALNA_NAZWA,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun anuluj(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(UNIKALNA_NAZWA)
    }
}
