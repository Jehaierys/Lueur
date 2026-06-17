package com.lueur.tarot.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.lueur.tarot.R
import com.lueur.tarot.data.*
import com.lueur.tarot.domain.TarotUiState
import com.lueur.tarot.domain.allDrawnCards
import com.lueur.tarot.ui.components.*
import com.lueur.tarot.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: TarotUiState,
    theme: TarotThemeColors,
    onDraw: () -> Unit,
    onShuffle: () -> Unit,
    onNewSpread: () -> Unit,
    onToggleBottomCard: () -> Unit,
    onCardClick: (DrawnCard) -> Unit,
    onAddLineBreak: () -> Unit,
    onShowRemainingDeck: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // #9: LazyColumn so new cards appear at top; deck header stays fixed
    val listState = rememberLazyListState()

    // Auto-scroll to top when a new card is added
    val cardCount = uiState.allDrawnCards.size
    LaunchedEffect(cardCount) {
        if (cardCount > 0) listState.animateScrollToItem(0)
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp),
            reverseLayout = false,
        ) {
            // ── Top: deck controls ──────────────────────────────────────────
            item(key = "deck_header") {
                DeckHeader(
                    uiState  = uiState,
                    theme    = theme,
                    onDraw   = onDraw,
                    onShuffle = onShuffle,
                    onNewSpread = onNewSpread,
                    onToggleBottomCard = onToggleBottomCard,
                    onShowRemainingDeck = onShowRemainingDeck,
                )
            }

            // ── Spread rows (newest row last drawn card at top) ─────────────
            // We reverse the rows list so newest cards appear at top under header
            val reversedRows = uiState.spreadRows
                .filter { it.isNotEmpty() }
                .reversed()

            if (reversedRows.isEmpty()) {
                item(key = "empty_hint") {
                    EmptySpreadHint(theme = theme)
                }
            } else {
                reversedRows.forEachIndexed { rowIdx, row ->
                    item(key = "row_$rowIdx") {
                        SpreadRow(
                            cards = row,
                            theme = theme,
                            showEnergyHints = uiState.settings.showEnergyHints,
                            onCardClick = onCardClick,
                        )
                    }
                }
            }
        }

        // ── Floating action: add line-break ─────────────────────────────────
        if (uiState.allDrawnCards.isNotEmpty()) {
            FloatingLineBreakButton(
                theme = theme,
                onClick = onAddLineBreak,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
            )
        }
    }
}

// ── Deck header ────────────────────────────────────────────────────────────────
@Composable
private fun DeckHeader(
    uiState: TarotUiState,
    theme: TarotThemeColors,
    onDraw: () -> Unit,
    onShuffle: () -> Unit,
    onNewSpread: () -> Unit,
    onToggleBottomCard: () -> Unit,
    onShowRemainingDeck: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(theme.background)
            .padding(top = 8.dp, bottom = 4.dp),
    ) {
        // Row: deck | stats | bottom card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Deck
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TarotCardBack(
                    theme = theme,
                    count = uiState.remainingDeck.size,
                    isAnimating = uiState.isShuffling,
                    modifier = Modifier.clickable(enabled = uiState.remainingDeck.isNotEmpty()) { onDraw() },
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = if (uiState.remainingDeck.isEmpty()) "Колода пуста"
                           else "Нажми для карты",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = theme.textSecondary.copy(alpha = 0.5f),
                        fontSize = 9.sp,
                    )
                )
            }

            // Centre stats
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            ) {
                Text(
                    text = "${uiState.remainingDeck.size}/78",
                    style = MaterialTheme.typography.titleMedium.copy(color = theme.textSecondary),
                )
                Text(
                    text = "в колоде",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = theme.textSecondary.copy(alpha = 0.5f), fontSize = 9.sp
                    )
                )
                if (uiState.allDrawnCards.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${uiState.allDrawnCards.size} карт открыто",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = theme.primary.copy(alpha = 0.7f), fontSize = 9.sp
                        )
                    )
                }
            }

            // Bottom card
            BottomCardWidget(
                card     = uiState.bottomCard,
                isShown  = uiState.showBottomCard,
                cooldown = uiState.bottomCardCooldown,
                theme    = theme,
                onTap    = onToggleBottomCard,
            )
        }

        Spacer(Modifier.height(10.dp))

        // Action buttons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Shuffle icon button
            IconButton(
                onClick = onShuffle,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(theme.secondary.copy(alpha = 0.12f)),
            ) {
                Icon(
                    Icons.Default.Shuffle,
                    contentDescription = "Перемешать",
                    tint = theme.secondary,
                    modifier = Modifier.size(20.dp),
                )
            }

            // New Spread button (#12: bigger, clearer label)
            Button(
                onClick = onNewSpread,
                modifier = Modifier.weight(1f).height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = theme.primary.copy(alpha = 0.18f),
                    contentColor   = theme.primary,
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp),
            ) {
                Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Новый расклад", style = MaterialTheme.typography.labelSmall)
            }

            // Remaining deck button (#11: custom icon, not MoreVert)
            RemainingDeckButton(theme = theme, onClick = onShowRemainingDeck)
        }

        Divider(
            color = theme.divider.copy(alpha = 0.4f),
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

// #11: Custom remaining deck button
@Composable
private fun RemainingDeckButton(theme: TarotThemeColors, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(theme.accent.copy(alpha = 0.10f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        // Custom "stack of cards" icon via canvas
        androidx.compose.foundation.Canvas(modifier = Modifier.size(22.dp)) {
            val w = size.width; val h = size.height
            val cardW = w * 0.65f; val cardH = h * 0.82f
            val rx = 2.5f
            // Draw 3 stacked rectangles offset
            for (i in 2 downTo 0) {
                val ox = i * w * 0.08f; val oy = -i * h * 0.08f
                drawRoundRect(
                    color = theme.accent.copy(alpha = 0.25f + i * 0.2f),
                    topLeft = androidx.compose.ui.geometry.Offset(ox, h - cardH + oy),
                    size = androidx.compose.ui.geometry.Size(cardW, cardH),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(rx),
                )
            }
            // Eye dot
            drawCircle(
                color = theme.accent.copy(alpha = 0.8f),
                radius = w * 0.09f,
                center = androidx.compose.ui.geometry.Offset(cardW * 0.45f, h * 0.42f),
            )
        }
    }
}

// ── Bottom card widget (#13) ────────────────────────────────────────────────
@Composable
private fun BottomCardWidget(
    card: TarotCard?,
    isShown: Boolean,
    cooldown: Boolean,
    theme: TarotThemeColors,
    onTap: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(CARD_WIDTH)
                .height(CARD_HEIGHT)
                .clip(RoundedCornerShape(CARD_RADIUS))
                .background(theme.surface)
                .border(
                    1.dp,
                    theme.secondary.copy(alpha = if (isShown) 0.7f else 0.25f),
                    RoundedCornerShape(CARD_RADIUS)
                )
                .clickable(enabled = !cooldown) { onTap() },
            contentAlignment = Alignment.Center,
        ) {
            if (isShown && card != null) {
                TarotCardFace(
                    drawn = DrawnCard(card = card, orientation = CardOrientation.UPRIGHT),
                    theme = theme,
                    showEnergyHint = false,
                    onClick = { if (!cooldown) onTap() },
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(6.dp),
                ) {
                    Text(
                        "⊙",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = theme.secondary.copy(alpha = 0.35f)
                        )
                    )
                }
            }
        }
        Spacer(Modifier.height(4.dp))
        // #13: removed "скрыть" label
        Text(
            text = "фон колоды",
            style = MaterialTheme.typography.labelSmall.copy(
                color = theme.secondary.copy(alpha = 0.4f),
                fontSize = 9.sp,
            )
        )
    }
}

// ── Spread row ────────────────────────────────────────────────────────────────
// 3 columns, fixed — no grouping controls (#6 removed grouping button)
@Composable
private fun SpreadRow(
    cards: List<DrawnCard>,
    theme: TarotThemeColors,
    showEnergyHints: Boolean,
    onCardClick: (DrawnCard) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(theme.surface.copy(alpha = 0.5f))
            .padding(8.dp),
    ) {
        // Chunk cards into rows of 3 (#7)
        val rowsOf3 = cards.chunked(3)
        rowsOf3.forEach { rowCards ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                rowCards.forEach { drawn ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TarotCardFace(
                            drawn = drawn,
                            theme = theme,
                            showEnergyHint = showEnergyHints,
                            onClick = { onCardClick(drawn) },
                            isCompact = true,
                        )
                        // #5: energy hint on single line, max 2 keywords
                        if (showEnergyHints) {
                            Spacer(Modifier.height(3.dp))
                            EnergyHintChip(
                                card  = drawn.card,
                                theme = theme,
                            )
                        }
                    }
                }
                // Spacers for partial rows
                repeat(3 - rowCards.size) {
                    Box(modifier = Modifier.width(CARD_WIDTH * 0.85f).height(CARD_HEIGHT * 0.85f))
                }
            }
        }
    }
}

// ── Empty hint ────────────────────────────────────────────────────────────────
@Composable
private fun EmptySpreadHint(theme: TarotThemeColors) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "✦",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = theme.primary.copy(alpha = 0.15f)
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            "Нажми на колоду, чтобы вытащить карту",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = theme.textSecondary.copy(alpha = 0.35f),
                textAlign = TextAlign.Center,
            )
        )
    }
}

// ── Line-break FAB ────────────────────────────────────────────────────────────
@Composable
private fun FloatingLineBreakButton(
    theme: TarotThemeColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        color = theme.surfaceVariant,
        shadowElevation = 4.dp,
        border = BorderStroke(0.5.dp, theme.divider),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                Icons.Default.KeyboardReturn,
                contentDescription = null,
                tint = theme.textSecondary.copy(alpha = 0.7f),
                modifier = Modifier.size(14.dp),
            )
            Text(
                "Перенос строки",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = theme.textSecondary.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                )
            )
        }
    }
}
