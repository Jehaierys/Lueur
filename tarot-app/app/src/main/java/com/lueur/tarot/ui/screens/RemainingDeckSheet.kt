package com.lueur.tarot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.lueur.tarot.data.*
import com.lueur.tarot.ui.components.*
import com.lueur.tarot.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemainingDeckSheet(
    remaining: List<TarotCard>,
    theme: TarotThemeColors,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = theme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        "Оставшиеся карты",
                        style = MaterialTheme.typography.titleLarge.copy(color = theme.primary),
                    )
                    Text(
                        "${remaining.size} карт в колоде",
                        style = MaterialTheme.typography.labelSmall.copy(color = theme.textSecondary.copy(alpha = 0.6f)),
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, null, tint = theme.textSecondary)
                }
            }

            Divider(color = theme.divider, modifier = Modifier.padding(horizontal = 24.dp))
            Spacer(Modifier.height(8.dp))

            if (remaining.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Все карты вытащены",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = theme.textSecondary.copy(alpha = 0.4f),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 60.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(remaining) { card ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            TarotCardFace(
                                drawn = DrawnCard(card = card, orientation = CardOrientation.UPRIGHT),
                                theme = theme,
                                modifier = Modifier.size(width = 52.dp, height = 84.dp),
                                showEnergyHint = false,
                                isCompact = true,
                            )
                        }
                    }
                }
            }
        }
    }
}
