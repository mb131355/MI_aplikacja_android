package com.example.mi.ustawienia

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.intPreferencesKey

private val Context.dataStore by preferencesDataStore(name = "ustawienia")

object UstawieniaDataStore {
    private val KLUCZ_CIEMNY = booleanPreferencesKey("ciemny_motyw")
    private val KLUCZ_POWIADOMIENIA = booleanPreferencesKey("powiadomienia_zdrapka")
    private val KLUCZ_GODZINA = intPreferencesKey("powiadomienia_godzina")
    private val KLUCZ_MINUTA = intPreferencesKey("powiadomienia_minuta")

    fun ciemnyMotywFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[KLUCZ_CIEMNY] ?: false
        }
    }
    fun powiadomieniaWlaczoneFlow(context: Context) =
        context.dataStore.data.map { it[KLUCZ_POWIADOMIENIA] ?: false }

    fun godzinaFlow(context: Context) =
        context.dataStore.data.map { it[KLUCZ_GODZINA] ?: 9 }

    fun minutaFlow(context: Context) =
        context.dataStore.data.map { it[KLUCZ_MINUTA] ?: 0 }

    suspend fun ustawCiemnyMotyw(context: Context, wartosc: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KLUCZ_CIEMNY] = wartosc
        }
    }
    suspend fun ustawPowiadomieniaZdrapki(context: Context, wlaczone: Boolean) {
        context.dataStore.edit { it[KLUCZ_POWIADOMIENIA] = wlaczone }
    }

    suspend fun ustawGodzinePowiadomien(context: Context, godzina: Int, minuta: Int) {
        context.dataStore.edit {
            it[KLUCZ_GODZINA] = godzina
            it[KLUCZ_MINUTA] = minuta
        }
    }

}
