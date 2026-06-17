package com.lueur.tarot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.lueur.tarot.data.*
import com.lueur.tarot.ui.components.*
import com.lueur.tarot.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailSheet(
    drawn: DrawnCard,
    theme: TarotThemeColors,
    language: AppLanguage,   // #3: current language
    cardTheme: CardTheme,
    onDismiss: () -> Unit,
) {
    val card       = drawn.card
    val isReversed = drawn.orientation == CardOrientation.REVERSED
    val arcanaColor = arcanaColor(card, theme)

    // #3: name only in current language
    val cardName = when (language) {
        AppLanguage.FR -> card.nameFr
        AppLanguage.EN -> card.nameEn
        else           -> card.nameRu
    }
    val energyText = when (language) {
        AppLanguage.FR -> card.energyFr
        AppLanguage.EN -> card.energyEn
        else           -> card.energyRu
    }
    val arcanaLabel = when (card.arcana) {
        Arcana.MAJOR     -> when (language) { AppLanguage.FR -> "Arcane Majeur"; AppLanguage.EN -> "Major Arcana"; else -> "Старший Аркан" }
        Arcana.CUPS      -> when (language) { AppLanguage.FR -> "Coupes";        AppLanguage.EN -> "Cups";         else -> "Кубки" }
        Arcana.WANDS     -> when (language) { AppLanguage.FR -> "Bâtons";        AppLanguage.EN -> "Wands";        else -> "Жезлы" }
        Arcana.SWORDS    -> when (language) { AppLanguage.FR -> "Épées";         AppLanguage.EN -> "Swords";       else -> "Мечи" }
        Arcana.PENTACLES -> when (language) { AppLanguage.FR -> "Pentacles";     AppLanguage.EN -> "Pentacles";    else -> "Пентакли" }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor   = theme.surfaceVariant,
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
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, null, tint = theme.textSecondary)
                }
            }

            // ── Card visual ─────────────────────────────────────────────────
            // #8: show SVG for Waite theme; canvas for others
            if (cardTheme == CardTheme.WAITE) {
                WaiteCardImage(
                    svgAsset = card.svgAsset,
                    modifier = Modifier
                        .size(width = CARD_WIDTH * 1.7f, height = CARD_HEIGHT * 1.7f)
                        .clip(RoundedCornerShape(CARD_RADIUS)),
                )
            } else {
                TarotCardFace(
                    drawn  = drawn,
                    theme  = theme,
                    modifier = Modifier.size(width = CARD_WIDTH * 1.7f, height = CARD_HEIGHT * 1.7f),
                    showEnergyHint = false,
                    onClick = {},
                )
            }

            Spacer(Modifier.height(20.dp))

            // #3: Only current-language name
            Text(
                text  = cardName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color     = arcanaColor,
                    textAlign = TextAlign.Center,
                )
            )
            if (isReversed) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text  = when (language) { AppLanguage.FR -> "INVERSÉE"; AppLanguage.EN -> "REVERSED"; else -> "ПЕРЕВЁРНУТАЯ" },
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = theme.reversed, letterSpacing = 2.sp
                    )
                )
            }

            Spacer(Modifier.height(16.dp))
            Divider(color = theme.divider)
            Spacer(Modifier.height(16.dp))

            // Keywords (#1: 5-7 keywords, single row wraps)
            val kwLabel = when (language) { AppLanguage.FR -> "Énergies clés"; AppLanguage.EN -> "Key energies"; else -> "Ключевые энергии" }
            Text(kwLabel, style = MaterialTheme.typography.titleMedium.copy(color = theme.textSecondary))
            Spacer(Modifier.height(8.dp))
            // #5: keywords in a wrap row
            com.google.accompanist.flowlayout.FlowRow(
                mainAxisSpacing = 6.dp,
                crossAxisSpacing = 6.dp,
            ) {
                card.keywords.forEach { kw ->
                    Surface(
                        color  = arcanaColor.copy(alpha = 0.10f),
                        shape  = RoundedCornerShape(20.dp),
                        border = ButtonDefaults.outlinedButtonBorder,
                    ) {
                        Text(
                            text  = kw,
                            style = MaterialTheme.typography.labelSmall.copy(color = arcanaColor),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Energy description (#1: full paragraph)
            EnergyBlock(label = when (language) { AppLanguage.FR -> "À l'endroit"; AppLanguage.EN -> "Upright"; else -> "Прямая" },
                text = energyText, color = theme.upright, theme = theme)

            if (isReversed) {
                Spacer(Modifier.height(8.dp))
                val revPrefix = when (language) {
                    AppLanguage.FR -> "Énergie bloquée ou inversée: "
                    AppLanguage.EN -> "Blocked or inverted energy: "
                    else           -> "Заблокированная или инвертированная энергия: "
                }
                EnergyBlock(
                    label = when (language) { AppLanguage.FR -> "Inversée"; AppLanguage.EN -> "Reversed"; else -> "Перевёрнутая" },
                    text  = revPrefix + energyText.replaceFirstChar { it.lowercase() },
                    color = theme.reversed,
                    theme = theme,
                )
            }

            Spacer(Modifier.height(16.dp))
            Divider(color = theme.divider)
            Spacer(Modifier.height(10.dp))

            Text(
                text  = "$arcanaLabel · ${card.numberLabel}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = theme.textSecondary.copy(alpha = 0.5f)
                ),
            )
        }
    }
}

// ── Waite SVG card image ──────────────────────────────────────────────────────
@Composable
fun WaiteCardImage(svgAsset: String, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    val resId = remember(svgAsset) {
        ctx.resources.getIdentifier(svgAsset, "raw", ctx.packageName)
    }
    AsyncImage(
        model = ImageRequest.Builder(ctx)
            .data("android.resource://${ctx.packageName}/$resId")
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .build(),
        contentDescription = svgAsset,
        modifier = modifier,
    )
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
            text  = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = color, letterSpacing = 1.5.sp
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text  = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = theme.textPrimary.copy(alpha = 0.85f)
            )
        )
    }
}
