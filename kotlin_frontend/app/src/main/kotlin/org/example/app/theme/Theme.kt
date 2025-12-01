package org.example.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape

private val PrimaryBlue = Color(0xFF2563EB)
private val SecondaryAmber = Color(0xFFF59E0B)
private val ErrorRed = Color(0xFFEF4444)
private val Background = Color(0xFFF9FAFB)
private val Surface = Color(0xFFFFFFFF)
private val TextPrimary = Color(0xFF111827)

private fun oceanColorScheme(darkTheme: Boolean): ColorScheme {
    // Use light scheme tuned to Ocean Professional
    return androidx.compose.material3.lightColorScheme(
        primary = PrimaryBlue,
        onPrimary = Color.White,
        secondary = SecondaryAmber,
        onSecondary = Color(0xFF1F2937),
        error = ErrorRed,
        onError = Color.White,
        background = Background,
        onBackground = TextPrimary,
        surface = Surface,
        onSurface = TextPrimary
    )
}

private val oceanShapes = Shapes(
    extraSmall = RoundedCornerShape(6),
    small = RoundedCornerShape(10),
    medium = RoundedCornerShape(14),
    large = RoundedCornerShape(20),
    extraLarge = RoundedCornerShape(28)
)

private val oceanTypography = Typography()

/**
 * PUBLIC_INTERFACE
 * Ocean Professional Theme wrapper for the app.
 */
@Composable
fun OceanProfessionalTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = oceanColorScheme(darkTheme = false || useDarkTheme.not()) // prefer light aesthetic
    MaterialTheme(
        colorScheme = colors,
        typography = oceanTypography,
        shapes = oceanShapes,
        content = content
    )
}
