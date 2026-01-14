package com.example.mi.nawigacja

object Trasy {
    const val AKTUALNOSCI = "aktualnosci"
    const val KATEGORIE = "kategorie"
    const val USTAWIENIA = "ustawienia"

    const val SEKCA = "sekcja/{id}"
    fun sekcja(id: String) = "sekcja/$id"
    const val SPIEWNIK_LISTA = "spiewnik_lista"
    const val SPIEWNIK_TEKST = "spiewnik_tekst/{id}"
    fun spiewnikTekst(id: Int) = "spiewnik_tekst/$id"

}
