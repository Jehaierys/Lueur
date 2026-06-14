package com.lueur.tarot.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.lueur.tarot.data.*
import com.lueur.tarot.ui.components.*
import com.lueur.tarot.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    history: List<Spread>,
    showClearConfirm: Boolean,
    theme: TarotThemeColors,
    onClearRequest: () -> Unit,
    onClearConfirm: () -> Unit,
    onClearDismiss: () -> Unit,
    onDeleteSpread: (String) -> Unit,
) {
    Scaffold(
        containerColor = theme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "История раскладов",
                        style = MaterialTheme.typography.headlineMedium.copy(color = theme.primary),
                    )
                },
                actions = {
                    if (history.isNotEmpty()) {
                        IconButton(onClick = onClearRequest) {
                            Icon(
                                Icons.Default.DeleteSweep,
                                contentDescription = "Очистить историю",
                                tint = theme.accent.copy(alpha = 0.7f),
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = theme.background,
                ),
            )
        }
    ) { padding ->

        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✦", style = MaterialTheme.typography.headlineLarge.copy(
                        color = theme.primary.copy(alpha = 0.15f)
                    ))
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "История раскладов пуста",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = theme.textSecondary.copy(alpha = 0.4f),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(history, key = { it.id }) { spread ->
                    SpreadHistoryCard(
                        spread = spread,
                        theme = theme,
                        onDelete = { onDeleteSpread(spread.id) },
                    )
                }
            }
        }

        // Clear confirm dialog
        if (showClearConfirm) {
            AlertDialog(
                onDismissRequest = onClearDismiss,
                containerColor = theme.surfaceVariant,
                title = {
                    Text(
                        "Очистить историю?",
                        style = MaterialTheme.typography.titleLarge.copy(color = theme.textPrimary)
                    )
                },
                text = {
                    Text(
                        "Все сохранённые расклады будут удалены. Это действие нельзя отменить.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = theme.textSecondary)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = onClearConfirm,
                        colors = ButtonDefaults.textButtonColors(contentColor = theme.accent)
                    ) {
                        Text("Удалить всё")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onClearDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = theme.textSecondary)
                    ) {
                        Text("Отмена")
                    }
                },
            )
        }
    }
}

@Composable
fun SpreadHistoryCard(
    spread: Spread,
    theme: TarotThemeColors,
    onDelete: () -> Unit,
) {
    val dateStr = remember(spread.createdAtMs) {
        SimpleDateFormat("d MMM yyyy, HH:mm", Locale.getDefault())
            .format(Date(spread.createdAtMs))
    }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.surface),
        border = BorderStroke(0.5.dp, theme.divider),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = dateStr,
                        style = MaterialTheme.typography.titleMedium.copy(color = theme.primary),
                    )
                    Text(
                        text = "${spread.cards.size} карт  ·  ${spread.gridCols} столбца",
                        style = MaterialTheme.typography.labelSmall.copy(color = theme.textSecondary.copy(alpha = 0.6f)),
                    )
                }
                Row {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Развернуть",
                            tint = theme.textSecondary,
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Удалить",
                            tint = theme.accent.copy(alpha = 0.5f),
                        )
                    }
                }
            }

            // Card thumbnails strip
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 8.dp),
            ) {
                items(spread.cards) { drawn ->
                    TarotCardFace(
                        drawn = drawn,
                        theme = theme,
                        modifier = Modifier.size(width = 40.dp, height = 65.dp),
                        showEnergyHint = false,
                        isCompact = true,
                    )
                }
            }

            // Expanded detail
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider(color = theme.divider)
                    Spacer(Modifier.height(8.dp))
                    spread.cards.forEachIndexed { idx, drawn ->
                        val isReversed = drawn.orientation == CardOrientation.REVERSED
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "${idx + 1}.",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = theme.textSecondary.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier.width(20.dp),
                            )
                            Text(
                                text = drawn.card.nameRu,
                                style = MaterialTheme.typography.bodyMedium.copy(color = theme.textPrimary),
                                modifier = Modifier.weight(1f),
                            )
                            if (isReversed) {
                                Text(
                                    text = "↓",
                                    style = MaterialTheme.typography.labelSmall.copy(color = theme.reversed),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
