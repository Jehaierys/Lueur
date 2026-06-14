package com.lueur.tarot.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.lueur.tarot.data.*
import com.lueur.tarot.domain.TarotUiState
import com.lueur.tarot.ui.components.*
import com.lueur.tarot.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: TarotUiState,
    onDraw: () -> Unit,
    onShuffle: () -> Unit,
    onNewSpread: () -> Unit,
    onToggleBottomCard: () -> Unit,
    onCardClick: (DrawnCard) -> Unit,
    onSetGridCols: (Int) -> Unit,
    onSetGroupingMode: (Int) -> Unit,
    onShowRemainingDeck: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val theme = LocalTarotTheme.current

    Scaffold(
        modifier = modifier,
        containerColor = theme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "✦ Lueur Tarot",
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(8.dp))

            // ── Deck + bottom card row ─────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Deck (clickable to draw)
                DeckSection(
                    count = uiState.remainingDeck.size,
                    isAnimating = uiState.isShuffling,
                    theme = theme,
                    isEmpty = uiState.remainingDeck.isEmpty(),
                    onDraw = onDraw,
                )

                // Middle status
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                ) {
                    Text(
                        text = "${uiState.remainingDeck.size}/78",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = theme.textSecondary,
                        )
                    )
                    Text(
                        text = "в колоде",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = theme.textSecondary.copy(alpha = 0.6f),
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    if (uiState.currentSpread.isNotEmpty()) {
                        Text(
                            text = "${uiState.currentSpread.size} карт",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = theme.primary.copy(alpha = 0.7f),
                            )
                        )
                        Text(
                            text = "в раскладе",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = theme.textSecondary.copy(alpha = 0.5f),
                            )
                        )
                    }
                }

                // Bottom card (фон колоды)
                BottomCardSection(
                    card = uiState.bottomCard,
                    isRevealed = uiState.showBottomCard,
                    theme = theme,
                    onToggle = onToggleBottomCard,
                )
            }

            Spacer(Modifier.height(16.dp))

            // ── Action buttons ─────────────────────────────────────────
            ActionButtonsRow(
                theme = theme,
                onShuffle = onShuffle,
                onNewSpread = onNewSpread,
                onShowRemaining = onShowRemainingDeck,
                hasSpread = uiState.currentSpread.isNotEmpty(),
                deckEmpty = uiState.remainingDeck.isEmpty(),
            )

            Spacer(Modifier.height(8.dp))

            // ── Grid/grouping controls ─────────────────────────────────
            if (uiState.currentSpread.isNotEmpty()) {
                LayoutControlRow(
                    gridCols = uiState.settings.gridCols,
                    groupingMode = uiState.settings.groupingMode,
                    theme = theme,
                    onSetGridCols = onSetGridCols,
                    onSetGroupingMode = onSetGroupingMode,
                )
                Spacer(Modifier.height(8.dp))
            }

            // ── Spread display ─────────────────────────────────────────
            Divider(color = theme.divider.copy(alpha = 0.5f))
            Spacer(Modifier.height(8.dp))

            SpreadGrid(
                cards = uiState.currentSpread,
                gridCols = uiState.settings.gridCols,
                groupingMode = uiState.settings.groupingMode,
                theme = theme,
                showEnergyHints = uiState.settings.showEnergyHints,
                onCardClick = onCardClick,
            )

            Spacer(Modifier.height(80.dp))
        }
    }
}

// ─── Deck widget ───────────────────────────────────────────────────────────────
@Composable
fun DeckSection(
    count: Int,
    isAnimating: Boolean,
    theme: TarotThemeColors,
    isEmpty: Boolean,
    onDraw: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isEmpty) {
            Box(
                modifier = Modifier
                    .width(CARD_WIDTH)
                    .height(CARD_HEIGHT)
                    .clip(RoundedCornerShape(CARD_RADIUS))
                    .border(1.dp, theme.divider, RoundedCornerShape(CARD_RADIUS)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "∅",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = theme.textSecondary.copy(alpha = 0.3f)
                    )
                )
            }
        } else {
            TarotCardBack(
                theme = theme,
                count = count,
                isAnimating = isAnimating,
                modifier = Modifier
                    .clickable { onDraw() },
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = if (isEmpty) "Колода пуста" else "Нажми для карты",
            style = MaterialTheme.typography.labelSmall.copy(
                color = theme.textSecondary.copy(alpha = 0.5f),
            )
        )
    }
}

// ─── Bottom card ──────────────────────────────────────────────────────────────
@Composable
fun BottomCardSection(
    card: TarotCard?,
    isRevealed: Boolean,
    theme: TarotThemeColors,
    onToggle: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(CARD_WIDTH)
                .height(CARD_HEIGHT)
                .clip(RoundedCornerShape(CARD_RADIUS))
                .border(1.dp, theme.secondary.copy(alpha = 0.3f), RoundedCornerShape(CARD_RADIUS))
                .background(theme.surface)
                .clickable { onToggle() },
            contentAlignment = Alignment.Center,
        ) {
            if (isRevealed && card != null) {
                TarotCardFace(
                    drawn = DrawnCard(card = card, orientation = CardOrientation.UPRIGHT),
                    theme = theme,
                    showEnergyHint = false,
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("⊙", style = MaterialTheme.typography.titleLarge.copy(color = theme.secondary.copy(alpha = 0.4f)))
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Фон\nколоды",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = theme.textSecondary.copy(alpha = 0.4f),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = if (isRevealed) "скрыть" else "дно колоды",
            style = MaterialTheme.typography.labelSmall.copy(
                color = theme.secondary.copy(alpha = 0.5f),
            )
        )
    }
}

// ─── Action buttons ────────────────────────────────────────────────────────────
@Composable
fun ActionButtonsRow(
    theme: TarotThemeColors,
    onShuffle: () -> Unit,
    onNewSpread: () -> Unit,
    onShowRemaining: () -> Unit,
    hasSpread: Boolean,
    deckEmpty: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Shuffle
        FilledTonalButton(
            onClick = onShuffle,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = theme.secondary.copy(alpha = 0.15f),
                contentColor = theme.secondary,
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text("Shuffle", style = MaterialTheme.typography.labelSmall)
        }

        // New spread
        FilledTonalButton(
            onClick = onNewSpread,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = theme.primary.copy(alpha = 0.15f),
                contentColor = theme.primary,
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text("Расклад", style = MaterialTheme.typography.labelSmall)
        }

        // Remaining deck (hidden, overflow)
        IconButton(
            onClick = onShowRemaining,
        ) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "Оставшиеся карты",
                tint = theme.textSecondary.copy(alpha = 0.5f),
            )
        }
    }
}

// ─── Grid / grouping control ──────────────────────────────────────────────────
@Composable
fun LayoutControlRow(
    gridCols: Int,
    groupingMode: Int,
    theme: TarotThemeColors,
    onSetGridCols: (Int) -> Unit,
    onSetGroupingMode: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "Столбцы:",
            style = MaterialTheme.typography.labelSmall.copy(color = theme.textSecondary),
        )
        listOf(1, 2, 3).forEach { cols ->
            FilterChip(
                selected = gridCols == cols,
                onClick = { onSetGridCols(cols) },
                label = { Text("$cols", style = MaterialTheme.typography.labelSmall) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = theme.primary.copy(alpha = 0.2f),
                    selectedLabelColor = theme.primary,
                    containerColor = theme.surface,
                    labelColor = theme.textSecondary,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = gridCols == cols,
                    borderColor = theme.divider,
                    selectedBorderColor = theme.primary.copy(alpha = 0.3f),
                ),
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(
            "Группы:",
            style = MaterialTheme.typography.labelSmall.copy(color = theme.textSecondary),
        )
        listOf(2, 3).forEach { mode ->
            FilterChip(
                selected = groupingMode == mode,
                onClick = { onSetGroupingMode(mode) },
                label = { Text("по $mode", style = MaterialTheme.typography.labelSmall) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = theme.accent.copy(alpha = 0.2f),
                    selectedLabelColor = theme.accent,
                    containerColor = theme.surface,
                    labelColor = theme.textSecondary,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = groupingMode == mode,
                    borderColor = theme.divider,
                    selectedBorderColor = theme.accent.copy(alpha = 0.3f),
                ),
            )
        }
    }
}

// ─── Spread grid ──────────────────────────────────────────────────────────────
@Composable
fun SpreadGrid(
    cards: List<DrawnCard>,
    gridCols: Int,
    groupingMode: Int,
    theme: TarotThemeColors,
    showEnergyHints: Boolean,
    onCardClick: (DrawnCard) -> Unit,
) {
    if (cards.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "✦",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = theme.primary.copy(alpha = 0.2f)
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Нажми на колоду, чтобы вытащить карту",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = theme.textSecondary.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center,
                )
            )
        }
        return
    }

    val groups = cards.chunked(groupingMode)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        groups.forEachIndexed { groupIndex, group ->
            GroupSection(
                group = group,
                groupIndex = groupIndex,
                gridCols = gridCols,
                theme = theme,
                showEnergyHints = showEnergyHints,
                onCardClick = onCardClick,
            )
        }
    }
}

@Composable
fun GroupSection(
    group: List<DrawnCard>,
    groupIndex: Int,
    gridCols: Int,
    theme: TarotThemeColors,
    showEnergyHints: Boolean,
    onCardClick: (DrawnCard) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(theme.surface.copy(alpha = 0.5f))
            .padding(8.dp),
    ) {
        // Group header
        Text(
            text = "✦ Группа ${groupIndex + 1}",
            style = MaterialTheme.typography.labelSmall.copy(
                color = theme.textSecondary.copy(alpha = 0.5f),
            ),
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp),
        )

        // Adaptive grid
        val rows = group.chunked(gridCols)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                row.forEach { drawn ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TarotCardFace(
                            drawn = drawn,
                            theme = theme,
                            showEnergyHint = showEnergyHints,
                            onClick = { onCardClick(drawn) },
                            isCompact = gridCols >= 3,
                        )
                        if (showEnergyHints) {
                            Spacer(Modifier.height(3.dp))
                            EnergyHintChip(card = drawn.card, theme = theme)
                        }
                    }
                }
                // Placeholders to keep alignment in partial rows
                repeat(gridCols - row.size) {
                    Spacer(Modifier.width(CARD_WIDTH))
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}
