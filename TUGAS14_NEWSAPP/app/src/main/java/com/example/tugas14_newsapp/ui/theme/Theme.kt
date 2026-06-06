package com.example.tugas14_newsapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightEarthGreen,
    secondary = LightEarthBrown,
    tertiary = LightEarthSand,
    background = Color(0xFF2B2B2B),
    surface = Color(0xFF383838),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = EarthGreen,
    secondary = EarthBrown,
    tertiary = EarthSand,
    background = Color(0xFFFDFBF7), // Creamy earth white
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF3E2723),
    onSurface = Color(0xFF3E2723)
)

@Composable
fun TUGAS14_NEWSAPPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false to force our Earth Tone colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
