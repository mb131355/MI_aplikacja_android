package com.example.mi.spiewnik

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SpiewnikRepozytorium {

    fun wczytajPiesni(context: Context): List<Piesn> {
        val json = context.assets.open("spiewnik.json")
            .bufferedReader()
            .use { it.readText() }

        val typ = object : TypeToken<List<Piesn>>() {}.type
        return Gson().fromJson(json, typ)
    }
}
