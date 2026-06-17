package com.lueur.tarot.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─── Lueur (original neon, with blue allowed) ────────────────────────────────
object LueurColors {
    val Background       = Color(0xFF0A0A14)
    val Surface          = Color(0xFF12121F)
    val SurfaceVariant   = Color(0xFF1A1A2E)
    val CardBack         = Color(0xFF1E1B3A)
    val CardFront        = Color(0xFF16142C)
    val NeonMint         = Color(0xFF00FFB3)
    val NeonLavender     = Color(0xFFB57BFF)
    val NeonCoral        = Color(0xFFFF6B9D)
    val NeonGold         = Color(0xFFFFE066)
    val NeonAqua         = Color(0xFF00E5FF)
    val NeonOrange       = Color(0xFFFF9B50)
    val NeonPink         = Color(0xFFFF4FA3)
    val NeonGreen        = Color(0xFF4DFF91)
    val TextPrimary      = Color(0xFFF0EAFF)
    val TextSecondary    = Color(0xFF9A8FCF)
    val Divider          = Color(0xFF2A2A4A)
    val Reversed         = Color(0xFFFF4FA3)
    val Upright          = Color(0xFF00FFB3)
}

// ─── Classic (warm gold + parchment, no blue) ────────────────────────────────
object ClassicColors {
    val Background       = Color(0xFF1A0A08)
    val Surface          = Color(0xFF241208)
    val SurfaceVariant   = Color(0xFF2E1810)
    val CardBack         = Color(0xFF3D1F12)
    val CardFront        = Color(0xFF221008)
    val PrimaryGold      = Color(0xFFD4AF37)
    val AccentRed        = Color(0xFF8B1A1A)
    val AccentCream      = Color(0xFFF5E6C8)
    val TextPrimary      = Color(0xFFF5E6C8)
    val TextSecondary    = Color(0xFFAA8855)
    val Divider          = Color(0xFF3D2510)
    val Reversed         = Color(0xFFCC3333)
    val Upright          = Color(0xFFD4AF37)
}

// ─── Noir (dark theme, no blue in RGB, FF4081 primary, FFFF00 secondary) ─────
object NoirColors {
    // UI shell — warm true-dark, zero blue
    val Background       = Color(0xFF0E0A0A)   // very dark warm black
    val Surface          = Color(0xFF161010)   // slightly lighter warm black
    val SurfaceVariant   = Color(0xFF1E1414)   // surface for sheets, cards
    val Divider          = Color(0xFF2A1818)

    // Card canvas
    val CardBack         = Color(0xFF121212)   // spec: pure near-black
    val CardFront        = Color(0xFF121212)

    // The palette — as spec'd
    val Rose             = Color(0xFFFF4081)   // PRIMARY — dominant, longest wave
    val Yellow           = Color(0xFFFFFF00)   // SECONDARY — light, energy
    val Red              = Color(0xFFFF0000)   // accent 1
    val Lime             = Color(0xFF32CD32)   // accent 2

    // UI text derived from palette (no blue)
    val TextPrimary      = Color(0xFFF5E8E0)   // warm near-white
    val TextSecondary    = Color(0xFF886055)   // muted warm

    // Orientation
    val Reversed         = Color(0xFFFF0000)   // red — inversion, tension
    val Upright          = Color(0xFF32CD32)   // lime — life, flow

    // Per-arcana accent mapping (all from the 5-colour palette)
    val MajorColor       = Color(0xFFFF4081)   // Rose — major arcana = peak energy
    val CupsColor        = Color(0xFFFF4081)   // Rose — emotion, water
    val WandsColor       = Color(0xFFFFFF00)   // Yellow — fire, action
    val SwordsColor      = Color(0xFFFF0000)   // Red — mind, conflict
    val PentaclesColor   = Color(0xFF32CD32)   // Lime — earth, growth
}

// ─── Shared theme data class ──────────────────────────────────────────────────
data class TarotThemeColors(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val cardBack: Color,
    val cardFront: Color,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val highlight: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val divider: Color,
    val reversed: Color,
    val upright: Color,
    val cupsColor: Color,
    val wandsColor: Color,
    val swordsColor: Color,
    val pentaclesColor: Color,
    val majorColor: Color,
    // Render mode flags
    val isLueur: Boolean,
    val isNoir: Boolean,
)

val LueurThemeColors = TarotThemeColors(
    background    = LueurColors.Background,
    surface       = LueurColors.Surface,
    surfaceVariant= LueurColors.SurfaceVariant,
    cardBack      = LueurColors.CardBack,
    cardFront     = LueurColors.CardFront,
    primary       = LueurColors.NeonMint,
    secondary     = LueurColors.NeonLavender,
    accent        = LueurColors.NeonCoral,
    highlight     = LueurColors.NeonGold,
    textPrimary   = LueurColors.TextPrimary,
    textSecondary = LueurColors.TextSecondary,
    divider       = LueurColors.Divider,
    reversed      = LueurColors.Reversed,
    upright       = LueurColors.Upright,
    cupsColor     = LueurColors.NeonAqua,
    wandsColor    = LueurColors.NeonOrange,
    swordsColor   = LueurColors.NeonPink,
    pentaclesColor= LueurColors.NeonGreen,
    majorColor    = LueurColors.NeonLavender,
    isLueur = true, isNoir = false,
)

val ClassicThemeColors = TarotThemeColors(
    background    = ClassicColors.Background,
    surface       = ClassicColors.Surface,
    surfaceVariant= ClassicColors.SurfaceVariant,
    cardBack      = ClassicColors.CardBack,
    cardFront     = ClassicColors.CardFront,
    primary       = ClassicColors.PrimaryGold,
    secondary     = ClassicColors.AccentCream,
    accent        = ClassicColors.AccentRed,
    highlight     = ClassicColors.PrimaryGold,
    textPrimary   = ClassicColors.TextPrimary,
    textSecondary = ClassicColors.TextSecondary,
    divider       = ClassicColors.Divider,
    reversed      = ClassicColors.Reversed,
    upright       = ClassicColors.Upright,
    cupsColor     = ClassicColors.AccentCream,
    wandsColor    = ClassicColors.AccentRed,
    swordsColor   = Color(0xFFAAAAAA),
    pentaclesColor= ClassicColors.PrimaryGold,
    majorColor    = ClassicColors.PrimaryGold,
    isLueur = false, isNoir = false,
)

val NoirThemeColors = TarotThemeColors(
    background    = NoirColors.Background,
    surface       = NoirColors.Surface,
    surfaceVariant= NoirColors.SurfaceVariant,
    cardBack      = NoirColors.CardBack,
    cardFront     = NoirColors.CardFront,
    primary       = NoirColors.Rose,
    secondary     = NoirColors.Yellow,
    accent        = NoirColors.Red,
    highlight     = NoirColors.Yellow,
    textPrimary   = NoirColors.TextPrimary,
    textSecondary = NoirColors.TextSecondary,
    divider       = NoirColors.Divider,
    reversed      = NoirColors.Reversed,
    upright       = NoirColors.Upright,
    cupsColor     = NoirColors.CupsColor,
    wandsColor    = NoirColors.WandsColor,
    swordsColor   = NoirColors.SwordsColor,
    pentaclesColor= NoirColors.PentaclesColor,
    majorColor    = NoirColors.MajorColor,
    isLueur = false, isNoir = true,
)

val LocalTarotTheme = staticCompositionLocalOf { NoirThemeColors }

@Composable
fun TarotAppTheme(
    tarotTheme: TarotThemeColors = NoirThemeColors,
    content: @Composable () -> Unit,
) {
    val m3Colors = darkColorScheme(
        primary          = tarotTheme.primary,
        secondary        = tarotTheme.secondary,
        tertiary         = tarotTheme.accent,
        background       = tarotTheme.background,
        surface          = tarotTheme.surface,
        surfaceVariant   = tarotTheme.surfaceVariant,
        onPrimary        = Color.Black,
        onSecondary      = Color.Black,
        onBackground     = tarotTheme.textPrimary,
        onSurface        = tarotTheme.textPrimary,
        onSurfaceVariant = tarotTheme.textSecondary,
    )
    CompositionLocalProvider(LocalTarotTheme provides tarotTheme) {
        MaterialTheme(
            colorScheme = m3Colors,
            typography  = TarotTypography,
            content     = content,
        )
    }
}

// ─── Typography ───────────────────────────────────────────────────────────────
val TarotTypography = Typography(
    headlineLarge  = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Light,   fontSize = 28.sp, letterSpacing = 0.5.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal,  fontSize = 22.sp, letterSpacing = 0.3.sp),
    titleLarge     = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal,  fontSize = 18.sp, letterSpacing = 0.5.sp),
    titleMedium    = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp, letterSpacing = 0.8.sp),
    bodyMedium     = TextStyle(fontSize = 13.sp, lineHeight = 20.sp),
    labelSmall     = TextStyle(fontSize = 10.sp, letterSpacing = 1.2.sp, fontWeight = FontWeight.Medium),
)

// ─── #8: Unified dark shell theme — used for ALL app UI regardless of card style ──
// Cards render their own style internally; the app shell is always this.
val UnifiedDarkTheme = TarotThemeColors(
    background     = Color(0xFF0A0A0A),   // true near-black, warm
    surface        = Color(0xFF141414),
    surfaceVariant = Color(0xFF1C1C1C),
    cardBack       = Color(0xFF121212),
    cardFront      = Color(0xFF121212),
    primary        = Color(0xFFFF4081),   // Rose — dominant accent for UI chrome
    secondary      = Color(0xFFFFFF00),   // Yellow — secondary
    accent         = Color(0xFF32CD32),   // Lime
    highlight      = Color(0xFFFFFF00),
    textPrimary    = Color(0xFFF0E8E8),   // warm white, no blue
    textSecondary  = Color(0xFF886060),   // muted warm
    divider        = Color(0xFF2A1818),
    reversed       = Color(0xFFFF0000),
    upright        = Color(0xFF32CD32),
    cupsColor      = Color(0xFFFF4081),
    wandsColor     = Color(0xFFFFFF00),
    swordsColor    = Color(0xFFFF0000),
    pentaclesColor = Color(0xFF32CD32),
    majorColor     = Color(0xFFFF4081),
    isLueur = false, isNoir = true,   // use Noir card renderer by default
)
