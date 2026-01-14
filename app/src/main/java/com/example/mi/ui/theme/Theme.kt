package com.example.mi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val JasnaPaleta = lightColorScheme(
    primary = Glowny,
    onPrimary = TekstNaGlownym,

    secondary = GlownyCiemny,
    onSecondary = TekstNaGlownym,

    background = Tlo,
    onBackground = Color(0xFF1B1B1F),

    surface = Powierzchnia,
    onSurface = Color(0xFF1B1B1F),

    outline = Color(0xFFDDDAFF)
)

private val CiemnaPaleta = darkColorScheme(
    primary = Color(0xFFB9A9FF),
    onPrimary = Color(0xFF1B1B1F),

    secondary = Color(0xFFD0C7FF),
    onSecondary = Color(0xFF1B1B1F),

    background = Color(0xFF12121A),
    onBackground = Color(0xFFEAEAF2),

    surface = Color(0xFF1A1A24),
    onSurface = Color(0xFFEAEAF2),

    outline = Color(0xFF3A345C)
)

@Composable
fun MiTheme(
    ciemnyMotyw: Boolean = false, // na razie na sztywno; później możemy przełączyć w Ustawieniach
    content: @Composable () -> Unit
) {
    val kolory = if (ciemnyMotyw) CiemnaPaleta else JasnaPaleta

    MaterialTheme(
        colorScheme = kolory,
        typography = Typography,
        content = content
    )
}
