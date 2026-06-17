package com.lueur.tarot.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.lueur.tarot.data.*
import com.lueur.tarot.domain.TarotViewModel
import com.lueur.tarot.ui.screens.*
import com.lueur.tarot.ui.theme.*

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Main     : Screen("main",     Icons.Default.AutoAwesome, "Расклад")
    object History  : Screen("history",  Icons.Default.History,     "История")
    object Settings : Screen("settings", Icons.Default.Settings,    "Настройки")
}

val bottomScreens = listOf(Screen.Main, Screen.History, Screen.Settings)

@Composable
fun TarotApp(viewModel: TarotViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val settings = uiState.settings

    // #8: unified dark background always, only cards change
    // All UI uses a single base dark theme for shell; card renderer picks its style
    val theme = UnifiedDarkTheme   // always this for the shell

    TarotAppTheme(tarotTheme = theme) {
        val navController = rememberNavController()
        val ctx = LocalContext.current

        Scaffold(
            containerColor = theme.background,
            bottomBar = {
                BottomAppBar(
                    containerColor = theme.surface,
                    tonalElevation = 0.dp,
                ) {
                    val entry by navController.currentBackStackEntryAsState()
                    val cur   = entry?.destination
                    bottomScreens.forEach { screen ->
                        val selected = cur?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick  = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState    = true
                                }
                            },
                            icon = {
                                Icon(
                                    screen.icon, screen.label,
                                    tint = if (selected) theme.primary
                                           else theme.textSecondary.copy(alpha = 0.4f),
                                )
                            },
                            label = {
                                Text(
                                    screen.label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (selected) theme.primary
                                                else theme.textSecondary.copy(alpha = 0.4f),
                                        fontSize = 10.sp,
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = theme.primary.copy(alpha = 0.12f),
                            ),
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController    = navController,
                startDestination = Screen.Main.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    fadeIn(tween(200)) + slideInHorizontally { it / 8 }
                },
                exitTransition = {
                    fadeOut(tween(150)) + slideOutHorizontally { -it / 8 }
                },
            ) {
                composable(Screen.Main.route) {
                    MainScreen(
                        uiState  = uiState,
                        theme    = theme,
                        onDraw   = viewModel::drawCard,
                        onShuffle = viewModel::shuffleDeck,
                        onNewSpread = viewModel::startNewSpread,
                        onToggleBottomCard = viewModel::toggleBottomCard,
                        onCardClick = viewModel::selectCardForDetail,
                        onAddLineBreak = viewModel::addLineBreak,
                        onShowRemainingDeck = { viewModel.setShowRemainingDeck(true) },
                    )
                }
                composable(Screen.History.route) {
                    HistoryScreen(
                        history          = uiState.spreadHistory,
                        showClearConfirm = uiState.showClearHistoryConfirm,
                        theme            = theme,
                        onClearRequest   = viewModel::requestClearHistory,
                        onClearConfirm   = viewModel::confirmClearHistory,
                        onClearDismiss   = viewModel::dismissClearHistoryConfirm,
                        onDeleteSpread   = viewModel::deleteSpread,
                    )
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        settings         = settings,
                        theme            = theme,
                        onLanguage       = { lang ->
                            viewModel.setLanguage(lang)
                            // #10: restart activity to apply locale
                            restartActivity(ctx)
                        },
                        onCardTheme      = viewModel::setCardTheme,
                        onSaveHistory    = viewModel::setSaveHistory,
                        onShowEnergy     = viewModel::setShowEnergy,
                        onReverseEnabled = viewModel::setReverseEnabled,
                        onHapticEnabled  = viewModel::setHapticEnabled,
                    )
                }
            }

            // Overlays
            if (uiState.selectedCardForDetail != null) {
                CardDetailSheet(
                    drawn     = uiState.selectedCardForDetail!!,
                    theme     = theme,
                    language  = settings.language,
                    cardTheme = settings.cardTheme,
                    onDismiss = { viewModel.selectCardForDetail(null) },
                )
            }
            if (uiState.showRemainingDeck) {
                RemainingDeckSheet(
                    remaining = uiState.remainingDeck,
                    theme     = theme,
                    onDismiss = { viewModel.setShowRemainingDeck(false) },
                )
            }
        }
    }
}

private fun restartActivity(ctx: Context) {
    val intent = (ctx as? Activity)?.intent ?: return
    ctx.finish()
    ctx.startActivity(intent)
}
