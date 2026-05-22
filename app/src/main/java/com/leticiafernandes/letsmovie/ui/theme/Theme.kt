package com.leticiafernandes.letsmovie.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// TODO Dark Mode
//private val DarkColorScheme = darkColorScheme(
//    primary = ColorPrimaryDark,
//    onPrimary = TextPrimaryDark,
//    secondary = ColorSecondary,
//    tertiary = ColorTertiary,
//    background = BackgroundPrimaryDark,
//    surface = BackgroundSecondaryDark,
//    onBackground = TextPrimaryDark,
//    onSurface = TextPrimaryDark,
//    onSurfaceVariant = TextSecondaryDark
//)

val PlayfairDisplay = FontFamily.Serif

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    onPrimary = White,
    primaryContainer = ColorPrimaryContainer,
    onPrimaryContainer = OnColorPrimaryContainer,
    surface = LuminousSurface,
    onSurface = OnLuminousSurface,
    surfaceVariant = LuminousSurfaceContainer,
    onSurfaceVariant = OnLuminousSurfaceVariant,
    outline = LuminousOutline,
    outlineVariant = LuminousOutlineVariant,
    background = LuminousSurface
)

val LuminousTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = PlayfairDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PlayfairDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Composable
fun LetsMovieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //TODO Dark mode val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LuminousTypography,
        content = content
    )
}
