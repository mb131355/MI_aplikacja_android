package com.example.mi.ustawienia

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "ustawienia")

object UstawieniaDataStore {
    private val KLUCZ_CIEMNY = booleanPreferencesKey("ciemny_motyw")

    fun ciemnyMotywFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[KLUCZ_CIEMNY] ?: false
        }
    }

    suspend fun ustawCiemnyMotyw(context: Context, wartosc: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KLUCZ_CIEMNY] = wartosc
        }
    }
}
