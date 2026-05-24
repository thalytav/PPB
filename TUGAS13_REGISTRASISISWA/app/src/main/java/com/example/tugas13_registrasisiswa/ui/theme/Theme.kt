package com.example.tugas13_registrasisiswa.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = EarthPrimaryDark,
    secondary = EarthSecondaryDark,
    tertiary = EarthTertiaryDark,
    background = EarthBackgroundDark,
    surface = EarthSurfaceDark,
    onPrimary = EarthOnPrimaryDark,
    onBackground = EarthOnBackgroundDark,
    onSurface = EarthOnBackgroundDark
)

private val LightColorScheme = lightColorScheme(
    primary = EarthPrimary,
    secondary = EarthSecondary,
    tertiary = EarthTertiary,
    background = EarthBackground,
    surface = EarthSurface,
    onPrimary = EarthOnPrimary,
    onBackground = EarthOnBackground,
    onSurface = EarthOnBackground
)

@Composable
fun TUGAS13_REGISTRASISISWATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor to false to use our custom Earth Tone palette
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