package com.lueur.tarot.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.lueur.tarot.data.*
import com.lueur.tarot.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: AppSettings,
    theme: TarotThemeColors,
    onLanguage: (AppLanguage) -> Unit,
    onCardTheme: (CardTheme) -> Unit,
    onSaveHistory: (Boolean) -> Unit,
    onShowEnergy: (Boolean) -> Unit,
    onReverseEnabled: (Boolean) -> Unit,
    onHapticEnabled: (Boolean) -> Unit,
) {
    Scaffold(
        containerColor = theme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Настройки",
                        style = MaterialTheme.typography.headlineMedium.copy(color = theme.primary),
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = theme.background,
                ),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            // ── Card style ────────────────────────────────────────────────
            SettingsSection(title = "Стиль карт", theme = theme) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeOption(
                        selected  = settings.cardTheme == CardTheme.NOIR,
                        name      = "Noir",
                        subtitle  = "Авторская тёмная · Розовый + жёлтый · Без синего",
                        badgeColor = Color(0xFFFF4081),
                        theme     = theme,
                        onClick   = { onCardTheme(CardTheme.NOIR) },
                    )
                    ThemeOption(
                        selected  = settings.cardTheme == CardTheme.LUEUR,
                        name      = "Lueur",
                        subtitle  = "Неоновые абстракции · Сияние",
                        badgeColor = Color(0xFF00FFB3),
                        theme     = theme,
                        onClick   = { onCardTheme(CardTheme.LUEUR) },
                    )
                    ThemeOption(
                        selected  = settings.cardTheme == CardTheme.CLASSIC,
                        name      = "Классический",
                        subtitle  = "Золото и пергамент · Декоративный",
                        badgeColor = Color(0xFFD4AF37),
                        theme     = theme,
                        onClick   = { onCardTheme(CardTheme.CLASSIC) },
                    )
                    // #8: Waite theme
                    ThemeOption(
                        selected  = settings.cardTheme == CardTheme.WAITE,
                        name      = "Уэйт (Памела А)",
                        subtitle  = "Классические иллюстрации · Учебный режим",
                        badgeColor = Color(0xFFC8A030),
                        theme     = theme,
                        onClick   = { onCardTheme(CardTheme.WAITE) },
                    )
                }
            }

            // ── Language (#10: no System, only 3 languages) ───────────────
            SettingsSection(title = "Язык", theme = theme) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // #10: only RU, EN, FR — no System
                    listOf(AppLanguage.RU, AppLanguage.EN, AppLanguage.FR).forEach { lang ->
                        FilterChip(
                            selected = settings.language == lang,
                            onClick  = { onLanguage(lang) },
                            label    = { Text(lang.label, style = MaterialTheme.typography.labelSmall) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = theme.primary.copy(alpha = 0.2f),
                                selectedLabelColor     = theme.primary,
                                containerColor         = theme.surface,
                                labelColor             = theme.textSecondary,
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled         = true,
                                selected        = settings.language == lang,
                                borderColor     = theme.divider,
                                selectedBorderColor = theme.primary.copy(alpha = 0.4f),
                            ),
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "Смена языка перезапустит приложение",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = theme.textSecondary.copy(alpha = 0.4f),
                        fontSize = 9.sp,
                    )
                )
            }

            // ── Spread ────────────────────────────────────────────────────
            SettingsSection(title = "Расклад", theme = theme) {
                Column {
                    SettingsToggle(
                        label    = "Перевёрнутые карты",
                        subtitle = "Случайный % от 10 до 90 для каждой карты",
                        checked  = settings.reverseEnabled,
                        theme    = theme,
                        onCheckedChange = onReverseEnabled,
                    )
                    Divider(color = theme.divider, thickness = 0.5.dp)
                    SettingsToggle(
                        label    = "Подсказки энергий",
                        subtitle = "Ключевые слова под картами. Отключи для продвинутого режима",
                        checked  = settings.showEnergyHints,
                        theme    = theme,
                        onCheckedChange = onShowEnergy,
                    )
                }
            }

            // ── History ───────────────────────────────────────────────────
            SettingsSection(title = "История", theme = theme) {
                SettingsToggle(
                    label    = "Сохранять историю раскладов",
                    subtitle = "Каждый расклад сохраняется на устройстве",
                    checked  = settings.saveHistory,
                    theme    = theme,
                    onCheckedChange = onSaveHistory,
                )
            }

            // ── Feel ──────────────────────────────────────────────────────
            SettingsSection(title = "Ощущения", theme = theme) {
                SettingsToggle(
                    label    = "Тактильный отклик",
                    subtitle = "Вибрация при вытягивании карт",
                    checked  = settings.hapticEnabled,
                    theme    = theme,
                    onCheckedChange = onHapticEnabled,
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                "✦ Lueur Tarot v1.1\nОткрытый исходный код · github.com/Jehaierys/Lueur",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = theme.textSecondary.copy(alpha = 0.25f),
                    fontSize = 9.sp,
                ),
                lineHeight = 16.sp,
            )
        }
    }
}

@Composable
fun SettingsSection(title: String, theme: TarotThemeColors, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text  = title.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = theme.textSecondary.copy(alpha = 0.5f),
                letterSpacing = 1.5.sp,
            )
        )
        content()
    }
}

@Composable
fun SettingsToggle(
    label: String, subtitle: String, checked: Boolean,
    theme: TarotThemeColors, onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(label,    style = MaterialTheme.typography.bodyMedium.copy(color = theme.textPrimary))
            Text(subtitle, style = MaterialTheme.typography.labelSmall.copy(
                color = theme.textSecondary.copy(alpha = 0.5f)))
        }
        Switch(
            checked = checked, onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor   = Color.Black,
                checkedTrackColor   = theme.primary,
                uncheckedTrackColor = theme.divider,
                uncheckedThumbColor = theme.textSecondary.copy(alpha = 0.4f),
            )
        )
    }
}

@Composable
fun ThemeOption(
    selected: Boolean, name: String, subtitle: String,
    badgeColor: Color, theme: TarotThemeColors, onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) badgeColor.copy(alpha = 0.08f) else Color.Transparent)
            .border(
                if (selected) 1.dp else 0.5.dp,
                if (selected) badgeColor.copy(alpha = 0.5f) else theme.divider,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(badgeColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center,
        ) {
            Text("✦", style = MaterialTheme.typography.bodyMedium.copy(color = badgeColor))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(name,     style = MaterialTheme.typography.bodyMedium.copy(
                color = if (selected) badgeColor else theme.textPrimary))
            Text(subtitle, style = MaterialTheme.typography.labelSmall.copy(
                color = theme.textSecondary.copy(alpha = 0.5f)))
        }
        if (selected) Icon(Icons.Default.CheckCircle, null, tint = badgeColor, modifier = Modifier.size(18.dp))
    }
}
