package com.lueur.tarot.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.lueur.tarot.data.CardTheme
import com.lueur.tarot.domain.TarotViewModel
import com.lueur.tarot.ui.screens.*
import com.lueur.tarot.ui.theme.*

sealed class Screen(val route: String, val icon: ImageVector, val labelKey: String) {
    object Main    : Screen("main",    Icons.Default.AutoAwesome, "Расклад")
    object History : Screen("history", Icons.Default.History,     "История")
    object Settings: Screen("settings",Icons.Default.Settings,    "Настройки")
}

val bottomScreens = listOf(Screen.Main, Screen.History, Screen.Settings)

@Composable
fun TarotApp(viewModel: TarotViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val theme = when (uiState.settings.cardTheme) {
        CardTheme.NOIR    -> NoirThemeColors
        CardTheme.LUEUR   -> LueurThemeColors
        CardTheme.CLASSIC -> ClassicThemeColors
    }

    TarotAppTheme(tarotTheme = theme) {
        val navController = rememberNavController()

        Scaffold(
            containerColor = theme.background,
            bottomBar = {
                BottomAppBar(
                    containerColor = theme.surface,
                    tonalElevation = 0.dp,
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDest = navBackStackEntry?.destination

                    bottomScreens.forEach { screen ->
                        val selected = currentDest?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    screen.icon,
                                    contentDescription = screen.labelKey,
                                    tint = if (selected) theme.primary else theme.textSecondary.copy(alpha = 0.4f),
                                )
                            },
                            label = {
                                Text(
                                    screen.labelKey,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (selected) theme.primary else theme.textSecondary.copy(alpha = 0.4f),
                                        fontSize = 10.sp,
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = theme.primary.copy(alpha = 0.1f),
                            ),
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Main.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    fadeIn(animationSpec = androidx.compose.animation.core.tween(200)) +
                    slideInHorizontally { it / 8 }
                },
                exitTransition = {
                    fadeOut(animationSpec = androidx.compose.animation.core.tween(150)) +
                    slideOutHorizontally { -it / 8 }
                },
            ) {
                composable(Screen.Main.route) {
                    MainScreen(
                        uiState = uiState,
                        onDraw = viewModel::drawCard,
                        onShuffle = viewModel::shuffleDeck,
                        onNewSpread = viewModel::startNewSpread,
                        onToggleBottomCard = viewModel::toggleBottomCard,
                        onCardClick = viewModel::selectCardForDetail,
                        onSetGridCols = viewModel::setGridCols,
                        onSetGroupingMode = viewModel::setGroupingMode,
                        onShowRemainingDeck = { viewModel.setShowRemainingDeck(true) },
                    )
                }
                composable(Screen.History.route) {
                    HistoryScreen(
                        history = uiState.spreadHistory,
                        showClearConfirm = uiState.showClearHistoryConfirm,
                        theme = theme,
                        onClearRequest = viewModel::requestClearHistory,
                        onClearConfirm = viewModel::confirmClearHistory,
                        onClearDismiss = viewModel::dismissClearHistoryConfirm,
                        onDeleteSpread = viewModel::deleteSpread,
                    )
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        settings = uiState.settings,
                        theme = theme,
                        onLanguage = viewModel::setLanguage,
                        onCardTheme = viewModel::setCardTheme,
                        onSaveHistory = viewModel::setSaveHistory,
                        onShowEnergy = viewModel::setShowEnergy,
                        onReverseEnabled = viewModel::setReverseEnabled,
                        onHapticEnabled = viewModel::setHapticEnabled,
                    )
                }
            }

            // ── Overlays ──────────────────────────────────────────────────
            if (uiState.selectedCardForDetail != null) {
                CardDetailSheet(
                    drawn = uiState.selectedCardForDetail!!,
                    theme = theme,
                    onDismiss = { viewModel.selectCardForDetail(null) },
                )
            }

            if (uiState.showRemainingDeck) {
                RemainingDeckSheet(
                    remaining = uiState.remainingDeck,
                    theme = theme,
                    onDismiss = { viewModel.setShowRemainingDeck(false) },
                )
            }
        }
    }
}
