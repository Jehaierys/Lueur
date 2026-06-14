package com.lueur.tarot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class AppLanguage(val code: String, val label: String) {
    SYSTEM("system", "System"),
    RU("ru", "Русский"),
    EN("en", "English"),
    FR("fr", "Français");
}

enum class GridLayout(val cols: Int, val labelKey: String) {
    SINGLE(1, "layout_1"),
    TWO(2, "layout_2"),
    THREE(3, "layout_3"),
    TWO_BY_TWO(2, "layout_2x2"),   // alias – used via rows
    THREE_BY_THREE(3, "layout_3x3");
}

data class AppSettings(
    val language: AppLanguage = AppLanguage.SYSTEM,
    val cardTheme: CardTheme = CardTheme.NOIR,
    val saveHistory: Boolean = true,
    val showEnergyHints: Boolean = true,   // false = advanced mode
    val reverseEnabled: Boolean = true,
    val hapticEnabled: Boolean = true,
    val gridCols: Int = 3,
    val groupingMode: Int = 3,             // 2 or 3
)

class SettingsRepository(private val context: Context) {

    private object Keys {
        val LANGUAGE = stringPreferencesKey("language")
        val CARD_THEME = stringPreferencesKey("card_theme")
        val SAVE_HISTORY = booleanPreferencesKey("save_history")
        val SHOW_ENERGY = booleanPreferencesKey("show_energy")
        val REVERSE_ENABLED = booleanPreferencesKey("reverse_enabled")
        val HAPTIC_ENABLED = booleanPreferencesKey("haptic_enabled")
        val GRID_COLS = intPreferencesKey("grid_cols")
        val GROUPING_MODE = intPreferencesKey("grouping_mode")
    }

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            language = AppLanguage.values().firstOrNull { it.code == prefs[Keys.LANGUAGE] }
                ?: AppLanguage.SYSTEM,
            cardTheme = CardTheme.values().firstOrNull { it.name == prefs[Keys.CARD_THEME] }
                ?: CardTheme.LUEUR,
            saveHistory = prefs[Keys.SAVE_HISTORY] ?: true,
            showEnergyHints = prefs[Keys.SHOW_ENERGY] ?: true,
            reverseEnabled = prefs[Keys.REVERSE_ENABLED] ?: true,
            hapticEnabled = prefs[Keys.HAPTIC_ENABLED] ?: true,
            gridCols = prefs[Keys.GRID_COLS] ?: 3,
            groupingMode = prefs[Keys.GROUPING_MODE] ?: 3,
        )
    }

    suspend fun update(block: suspend (MutablePreferences) -> Unit) {
        context.settingsDataStore.edit { block(it) }
    }

    suspend fun setLanguage(lang: AppLanguage) = update { it[Keys.LANGUAGE] = lang.code }
    suspend fun setCardTheme(theme: CardTheme) = update { it[Keys.CARD_THEME] = theme.name }
    suspend fun setSaveHistory(v: Boolean) = update { it[Keys.SAVE_HISTORY] = v }
    suspend fun setShowEnergy(v: Boolean) = update { it[Keys.SHOW_ENERGY] = v }
    suspend fun setReverseEnabled(v: Boolean) = update { it[Keys.REVERSE_ENABLED] = v }
    suspend fun setHapticEnabled(v: Boolean) = update { it[Keys.HAPTIC_ENABLED] = v }
    suspend fun setGridCols(v: Int) = update { it[Keys.GRID_COLS] = v }
    suspend fun setGroupingMode(v: Int) = update { it[Keys.GROUPING_MODE] = v }
}
