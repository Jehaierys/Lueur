package com.lueur.tarot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.lueur.tarot.data.*
import com.lueur.tarot.ui.components.*
import com.lueur.tarot.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailSheet(
    drawn: DrawnCard,
    theme: TarotThemeColors,
    onDismiss: () -> Unit,
) {
    val card = drawn.card
    val isReversed = drawn.orientation == CardOrientation.REVERSED
    val arcanaColor = when (card.arcana) {
        Arcana.MAJOR     -> theme.majorColor
        Arcana.CUPS      -> theme.cupsColor
        Arcana.WANDS     -> theme.wandsColor
        Arcana.SWORDS    -> theme.swordsColor
        Arcana.PENTACLES -> theme.pentaclesColor
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = theme.surfaceVariant,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .size(width = 36.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(theme.divider)
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Закрыть", tint = theme.textSecondary)
                }
            }

            // Card visual (large)
            TarotCardFace(
                drawn = drawn,
                theme = theme,
                modifier = Modifier.size(width = CARD_WIDTH * 1.6f, height = CARD_HEIGHT * 1.6f),
                showEnergyHint = false,
                onClick = {},
            )

            Spacer(Modifier.height(20.dp))

            // Card name
            Text(
                text = card.nameRu,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = arcanaColor,
                    textAlign = TextAlign.Center,
                )
            )
            if (isReversed) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "ПЕРЕВЁРНУТАЯ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = theme.reversed,
                        letterSpacing = 2.sp,
                    )
                )
            }

            Spacer(Modifier.height(4.dp))
            Text(
                text = "${card.nameFr}  ·  ${card.nameEn}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = theme.textSecondary,
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = theme.divider)
            Spacer(Modifier.height(16.dp))

            // Keywords
            Text(
                text = "Ключевые энергии",
                style = MaterialTheme.typography.titleMedium.copy(color = theme.textSecondary),
            )
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                card.keywords.forEach { kw ->
                    Surface(
                        color = arcanaColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp),
                        border = ButtonDefaults.outlinedButtonBorder,
                    ) {
                        Text(
                            text = kw,
                            style = MaterialTheme.typography.labelSmall.copy(color = arcanaColor),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Energy description
            EnergyBlock(
                label = "Прямая",
                text = card.energyRu,
                color = theme.upright,
                theme = theme,
            )

            if (isReversed) {
                Spacer(Modifier.height(8.dp))
                EnergyBlock(
                    label = "Перевёрнутая",
                    text = "Заблокированная или инвертированная энергия: ${card.energyRu.lowercase()}",
                    color = theme.reversed,
                    theme = theme,
                )
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = theme.divider)
            Spacer(Modifier.height(12.dp))

            // Arcana info
            val arcanaLabel = when (card.arcana) {
                Arcana.MAJOR     -> "Старший Аркан"
                Arcana.CUPS      -> "Кубки"
                Arcana.WANDS     -> "Жезлы"
                Arcana.SWORDS    -> "Мечи"
                Arcana.PENTACLES -> "Пентакли"
            }
            Text(
                text = "Аркан: $arcanaLabel  ·  ${card.numberLabel}",
                style = MaterialTheme.typography.labelSmall.copy(color = theme.textSecondary.copy(alpha = 0.6f)),
            )
        }
    }
}

@Composable
private fun EnergyBlock(label: String, text: String, color: Color, theme: TarotThemeColors) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.07f))
            .padding(12.dp),
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = color,
                letterSpacing = 1.5.sp,
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = theme.textPrimary.copy(alpha = 0.85f),
            )
        )
    }
}
