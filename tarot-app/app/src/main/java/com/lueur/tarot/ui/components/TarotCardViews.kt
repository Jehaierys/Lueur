package com.lueur.tarot.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.lueur.tarot.data.*
import com.lueur.tarot.ui.theme.*
import kotlin.math.*

// ─── Card dimensions ──────────────────────────────────────────────────────────
val CARD_WIDTH  = 80.dp
val CARD_HEIGHT = 130.dp
val CARD_RADIUS = 12.dp

// ─── Noir fixed palette (referenced directly in canvas) ───────────────────────
private val NoirRose   = Color(0xFFFF4081)
private val NoirYellow = Color(0xFFFFFF00)
private val NoirRed    = Color(0xFFFF0000)
private val NoirLime   = Color(0xFF32CD32)
private val NoirBlack  = Color(0xFF121212)

// ─── Card face ────────────────────────────────────────────────────────────────
@Composable
fun TarotCardFace(
    drawn: DrawnCard,
    theme: TarotThemeColors,
    modifier: Modifier = Modifier,
    showEnergyHint: Boolean = true,
    onClick: () -> Unit = {},
    isCompact: Boolean = false,
) {
    val arcanaColor = arcanaColor(drawn.card, theme)
    val isReversed  = drawn.orientation == CardOrientation.REVERSED

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(initialScale = 0.7f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn(),
    ) {
        Box(
            modifier = modifier
                .width(if (isCompact) CARD_WIDTH * 0.85f else CARD_WIDTH)
                .height(if (isCompact) CARD_HEIGHT * 0.85f else CARD_HEIGHT)
                .clip(RoundedCornerShape(CARD_RADIUS))
                .background(theme.cardFront)
                .border(1.5.dp, arcanaColor.copy(alpha = if (theme.isNoir) 0.9f else 0.6f),
                    RoundedCornerShape(CARD_RADIUS))
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            // #2: rotate only art canvas, not the text overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationZ = if (isReversed) 180f else 0f },
            ) {
                when {
                    theme.isNoir  -> NoirCardFaceCanvas(drawn.card, arcanaColor)
                    theme.isLueur -> LueurCardFaceCanvas(drawn.card, arcanaColor)
                    // Classic canvas (parchment)
                    else          -> ClassicCardFaceCanvas(drawn.card, arcanaColor)
                }
            }

            // Labels overlay
            Column(
                modifier = Modifier.fillMaxSize().padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = drawn.card.numberLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = arcanaColor,
                        fontSize = if (isCompact) 8.sp else 10.sp,
                    ),
                    modifier = Modifier.align(Alignment.Start).padding(start = 4.dp, top = 2.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = drawn.card.nameRu,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (theme.isNoir) NoirYellow else theme.textPrimary,
                        fontSize = if (isCompact) 7.sp else 8.sp,
                        textAlign = TextAlign.Center,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = theme.cardFront.copy(alpha = if (theme.isNoir) 0.92f else 0.7f),
                            shape = RoundedCornerShape(
                                bottomStart = CARD_RADIUS, bottomEnd = CARD_RADIUS)
                        )
                        .padding(horizontal = 3.dp, vertical = 2.dp)
                )
            }

            // Reversed dot
            if (isReversed) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(theme.reversed)
                )
            }
        }
    }
}

// ─── Noir card face ──────────────────────────────────────────────────────────
// Palette rule: Rose dominant, Yellow light, Red + Lime as sparks
// Style: organic, non-geometric, abstract breathing shapes
@Composable
private fun NoirCardFaceCanvas(card: TarotCard, arcanaColor: Color) {
    // Two independent pulsing animations — different speeds per card
    val t1 = rememberInfiniteTransition(label = "n1")
    val pulse by t1.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1800 + (card.id % 9) * 220, easing = FastOutSlowInEasing),
            RepeatMode.Reverse),
        label = "pulse"
    )
    val t2 = rememberInfiniteTransition(label = "n2")
    val drift by t2.animateFloat(
        initialValue = -1f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(2600 + (card.id % 7) * 310, easing = LinearEasing),
            RepeatMode.Reverse),
        label = "drift"
    )

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val seed = card.id.toLong()
        val rng  = java.util.Random(seed)

        // ── 1. Deep background — pure #121212 with Rose breathing haze ──────
        // Rose is dominant: large soft aura in the upper area
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    NoirRose.copy(alpha = 0.18f + pulse * 0.10f),
                    Color.Transparent,
                ),
                center = Offset(w * (0.35f + drift * 0.12f), h * 0.32f),
                radius  = h * 0.62f,
            )
        )

        // Yellow secondary aura — lower, smaller, counter-drifts
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    NoirYellow.copy(alpha = 0.10f + pulse * 0.07f),
                    Color.Transparent,
                ),
                center = Offset(w * (0.65f - drift * 0.10f), h * 0.68f),
                radius  = h * 0.40f,
            )
        )

        // ── 2. Organic blob field ─────────────────────────────────────────────
        // 5 blobs: first 2 Rose, 1 Yellow, 1 Red, 1 Lime
        val blobColors = listOf(NoirRose, NoirRose, NoirYellow, NoirRed, NoirLime)
        val blobAlpha  = listOf(0.22f, 0.18f, 0.14f, 0.16f, 0.13f)

        repeat(5) { i ->
            val bx = w * (0.15f + rng.nextFloat() * 0.70f)
            val by = h * (0.10f + rng.nextFloat() * 0.75f)
            val br = w * (0.09f + rng.nextFloat() * 0.13f)
            // ellipse distortion: squash/stretch each blob differently
            val scaleX = 0.6f + rng.nextFloat() * 0.8f
            val scaleY = 0.6f + rng.nextFloat() * 0.8f

            val animAlpha = blobAlpha[i] * (0.5f + pulse * 0.5f)

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        blobColors[i].copy(alpha = animAlpha),
                        Color.Transparent,
                    ),
                    center = Offset(bx, by),
                    radius = br * maxOf(scaleX, scaleY),
                ),
                radius = br * maxOf(scaleX, scaleY),
                center = Offset(bx, by),
                blendMode = BlendMode.Screen,
            )
        }

        // ── 3. Flowing organic lines — NOT geometric ──────────────────────────
        // Each card gets unique bezier flows seeded by card.id
        val rng2 = java.util.Random(seed + 1)
        val lineColor = if (rng2.nextBoolean()) NoirRose else NoirYellow

        repeat(3) { li ->
            val p = Path()
            val x0 = rng2.nextFloat() * w * 0.3f
            val y0 = h * (0.15f + li * 0.28f)
            val cx1 = w * 0.3f + rng2.nextFloat() * w * 0.2f
            val cy1 = y0 - 12f + rng2.nextFloat() * 24f + drift * 6f
            val cx2 = w * 0.6f + rng2.nextFloat() * w * 0.15f
            val cy2 = y0 + 8f  - rng2.nextFloat() * 20f - drift * 4f
            val x3  = w * 0.75f + rng2.nextFloat() * w * 0.25f
            val y3  = y0 + rng2.nextFloat() * 18f - 9f

            p.moveTo(x0, y0)
            p.cubicTo(cx1, cy1, cx2, cy2, x3, y3)

            drawPath(
                path  = p,
                color = lineColor.copy(alpha = 0.09f + (1 - li * 0.02f) * pulse * 0.06f),
                style = Stroke(width = 0.7f + li * 0.2f),
                blendMode = BlendMode.Screen,
            )
        }

        // ── 4. Constellation — arcana-specific dot pattern ────────────────────
        val dotCount = when (card.arcana) {
            Arcana.MAJOR     -> 6
            Arcana.CUPS      -> 4
            Arcana.WANDS     -> 3
            Arcana.SWORDS    -> 5
            Arcana.PENTACLES -> 5
        }
        // Primary dots: Rose
        val rng3 = java.util.Random(seed + 99)
        repeat(dotCount) {
            val dx = w * (0.18f + rng3.nextFloat() * 0.64f)
            val dy = h * (0.15f + rng3.nextFloat() * 0.60f)
            val dr = 1.2f + rng3.nextFloat() * 1.8f

            // dot core (Rose)
            drawCircle(
                color  = NoirRose.copy(alpha = 0.55f + pulse * 0.35f),
                radius = dr,
                center = Offset(dx, dy),
                blendMode = BlendMode.Screen,
            )
            // soft halo
            drawCircle(
                color  = NoirRose.copy(alpha = 0.08f + pulse * 0.06f),
                radius = dr * 4.5f,
                center = Offset(dx, dy),
                blendMode = BlendMode.Screen,
            )
        }

        // 1-2 accent sparks (Yellow or Lime, never same card twice)
        val sparkColor = if (card.id % 2 == 0) NoirYellow else NoirLime
        repeat(2) {
            val sx = w * (0.22f + rng3.nextFloat() * 0.56f)
            val sy = h * (0.20f + rng3.nextFloat() * 0.55f)
            drawCircle(
                color  = sparkColor.copy(alpha = 0.65f + pulse * 0.25f),
                radius = 1.0f + rng3.nextFloat() * 0.8f,
                center = Offset(sx, sy),
                blendMode = BlendMode.Screen,
            )
            drawCircle(
                color  = sparkColor.copy(alpha = 0.07f + pulse * 0.04f),
                radius = 6f + rng3.nextFloat() * 4f,
                center = Offset(sx, sy),
                blendMode = BlendMode.Screen,
            )
        }

        // ── 5. Edge vignette — keeps black frame crisp ────────────────────────
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, NoirBlack.copy(alpha = 0.55f)),
                center = Offset(w / 2f, h / 2f),
                radius = maxOf(w, h) * 0.75f,
            )
        )
    }
}

// ─── Noir card back ───────────────────────────────────────────────────────────
@Composable
private fun NoirCardBackCanvas() {
    val t = rememberInfiniteTransition(label = "nb")
    val pulse by t.animateFloat(
        initialValue = 0.3f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            tween(2200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "nb_p"
    )
    val rot by t.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(12000, easing = LinearEasing), RepeatMode.Restart),
        label = "nb_r"
    )

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        val cx = w / 2f;   val cy = h / 2f

        // Rose outer breathing ring
        for (i in 1..4) {
            val r = w * 0.08f * i
            drawCircle(
                color = NoirRose.copy(alpha = pulse * 0.13f * (5 - i) / 4f),
                radius = r, center = Offset(cx, cy),
                style = Stroke(width = 0.9f),
            )
        }

        // Yellow rotating cross-lines
        withTransform({ rotate(rot, Offset(cx, cy)) }) {
            for (i in 0..5) {
                val angle = i * 30f * PI.toFloat() / 180f
                drawLine(
                    color = NoirYellow.copy(alpha = 0.055f),
                    start = Offset(cx, cy),
                    end   = Offset(cx + cos(angle) * w, cy + sin(angle) * w),
                    strokeWidth = 0.5f,
                )
            }
        }

        // Centre core: Rose glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    NoirRose.copy(alpha = pulse * 0.55f),
                    NoirRose.copy(alpha = 0f),
                ),
                center = Offset(cx, cy), radius = 14f,
            ),
            radius = 14f, center = Offset(cx, cy),
        )

        // Lime accent spark
        drawCircle(
            color  = NoirLime.copy(alpha = pulse * 0.5f),
            radius = 2.5f, center = Offset(cx + 14f, cy - 10f),
            blendMode = BlendMode.Screen,
        )
        // Red accent spark
        drawCircle(
            color  = NoirRed.copy(alpha = (1f - pulse) * 0.4f),
            radius = 2f, center = Offset(cx - 12f, cy + 12f),
            blendMode = BlendMode.Screen,
        )

        // Vignette
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, NoirBlack.copy(alpha = 0.6f)),
                center = Offset(cx, cy), radius = maxOf(w, h) * 0.72f,
            )
        )
    }
}

// ─── Lueur card face ──────────────────────────────────────────────────────────
@Composable
private fun LueurCardFaceCanvas(card: TarotCard, accentColor: Color) {
    val t = rememberInfiniteTransition(label = "lg")
    val glowAlpha by t.animateFloat(
        initialValue = 0.3f, targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            tween(2000 + (card.id % 5) * 300, easing = FastOutSlowInEasing),
            RepeatMode.Reverse),
        label = "la"
    )
    val shiftX by t.animateFloat(
        initialValue = -15f, targetValue = 15f,
        animationSpec = infiniteRepeatable(
            tween(3000 + (card.id % 7) * 400, easing = LinearEasing),
            RepeatMode.Reverse),
        label = "ls"
    )

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height; val seed = card.id.toLong()

        drawRect(brush = Brush.radialGradient(
            colors = listOf(accentColor.copy(alpha = 0.15f), Color.Transparent),
            center = Offset(w / 2 + shiftX, h / 3), radius = h * 0.7f,
        ))

        val rng = java.util.Random(seed)
        repeat(6) {
            val bx = w * (0.2f + rng.nextFloat() * 0.6f)
            val by = h * (0.1f + rng.nextFloat() * 0.8f)
            val br = w * (0.08f + rng.nextFloat() * 0.14f)
            val ba = (0.15f + rng.nextFloat() * 0.25f) * glowAlpha
            drawCircle(color = accentColor.copy(alpha = ba), radius = br,
                center = Offset(bx, by), blendMode = BlendMode.Screen)
        }

        val path = Path(); val rng2 = java.util.Random(seed + 1)
        for (i in 0..3) {
            val yBase = h * (0.2f + i * 0.18f); path.reset()
            for (xi in 0..10) {
                val x = w * xi / 10f
                val y = yBase + sin(xi * 0.9f + seed % 5) * 8f
                if (xi == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            drawPath(path, accentColor.copy(alpha = 0.1f + i * 0.03f), style = Stroke(0.8f))
        }

        val dotCount = when (card.arcana) {
            Arcana.MAJOR -> 5; Arcana.CUPS -> 4; Arcana.WANDS -> 3
            Arcana.SWORDS -> 4; Arcana.PENTACLES -> 5
        }
        val rng3 = java.util.Random(seed + 99)
        repeat(dotCount) {
            val dx = w * (0.25f + rng3.nextFloat() * 0.5f)
            val dy = h * (0.2f  + rng3.nextFloat() * 0.5f)
            val dr = 1.5f + rng3.nextFloat() * 2f
            drawCircle(color = accentColor.copy(alpha = 0.5f + glowAlpha * 0.3f),
                radius = dr, center = Offset(dx, dy), blendMode = BlendMode.Screen)
        }
    }
}

// ─── Classic card face ────────────────────────────────────────────────────────
@Composable
private fun ClassicCardFaceCanvas(card: TarotCard, accentColor: Color) {
    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height

        drawRect(brush = Brush.linearGradient(colors = listOf(Color(0xFF2A1505), Color(0xFF1A0A02))))

        drawRect(color = accentColor.copy(alpha = 0.25f),
            topLeft = Offset(4f, 4f), size = Size(w - 8f, h - 8f), style = Stroke(0.8f))
        drawRect(color = accentColor.copy(alpha = 0.12f),
            topLeft = Offset(8f, 8f), size = Size(w - 16f, h - 16f), style = Stroke(0.5f))

        listOf(Offset(12f,12f), Offset(w-12f,12f), Offset(12f,h-12f), Offset(w-12f,h-12f))
            .forEach { c ->
                drawCircle(color = accentColor.copy(alpha = 0.5f), radius = 2.5f, center = c)
                drawCircle(color = accentColor.copy(alpha = 0.2f), radius = 5f, center = c, style = Stroke(0.5f))
            }

        drawCircle(
            brush = Brush.radialGradient(
                listOf(accentColor.copy(alpha = 0.2f), Color.Transparent),
                center = Offset(w/2, h*0.4f), radius = w * 0.35f,
            ),
            radius = w * 0.35f, center = Offset(w/2, h*0.4f),
        )
    }
}

// ─── Waite SVG card face (inline in card grid) ───────────────────────────────
@Composable
private fun WaiteSvgFace(card: TarotCard) {
    val ctx   = androidx.compose.ui.platform.LocalContext.current
    val resId = remember(card.svgAsset) {
        ctx.resources.getIdentifier(card.svgAsset, "raw", ctx.packageName)
    }
    if (resId != 0) {
        coil.compose.AsyncImage(
            model = coil.request.ImageRequest.Builder(ctx)
                .data("android.resource://${ctx.packageName}/$resId")
                .decoderFactory(coil.decode.SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentDescription = card.nameEn,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
        )
    } else {
        // fallback — classic canvas
        ClassicCardFaceCanvas(card, Color(0xFFD4AF37))
    }
}

// ─── Card back ────────────────────────────────────────────────────────────────
@Composable
fun TarotCardBack(
    theme: TarotThemeColors,
    modifier: Modifier = Modifier,
    count: Int = 1,
    isAnimating: Boolean = false,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isAnimating) 360f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "shuffle",
    )
    Box(modifier = modifier.graphicsLayer { rotationY = rotation }) {
        if (count > 2) {
            repeat(minOf(3, count - 1)) { i ->
                CardBackSingle(theme, Modifier.offset(x = (i * 1.5).dp, y = (-(i * 1.5)).dp))
            }
        }
        CardBackSingle(theme, Modifier.offset(x = (minOf(2, count - 1) * 1.5).dp))

        if (count > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-6).dp)
                    .size(22.dp)
                    .clip(RoundedCornerShape(50))
                    .background(theme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Text(count.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
private fun CardBackSingle(theme: TarotThemeColors, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(CARD_WIDTH).height(CARD_HEIGHT)
            .clip(RoundedCornerShape(CARD_RADIUS))
            .background(theme.cardBack)
            .border(1.dp, theme.primary.copy(alpha = if (theme.isNoir) 0.8f else 0.4f),
                RoundedCornerShape(CARD_RADIUS)),
    ) {
        when {
            theme.isNoir  -> NoirCardBackCanvas()
            theme.isLueur -> LueurCardBackCanvas(theme)
            else          -> ClassicCardBackCanvas(theme)
        }
    }
}

@Composable
private fun LueurCardBackCanvas(theme: TarotThemeColors) {
    val t = rememberInfiniteTransition(label = "lb")
    val pulse by t.animateFloat(
        initialValue = 0.4f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "lbp"
    )
    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        for (i in 1..5) {
            drawCircle(color = theme.primary.copy(alpha = pulse * 0.12f * (6 - i) / 5f),
                radius = w * 0.1f * i, center = Offset(w/2, h/2), style = Stroke(0.8f))
        }
        for (i in 0..12) {
            val x = w * i / 12f
            drawLine(theme.secondary.copy(alpha = 0.06f),
                Offset(x, 0f), Offset(x + h * 0.3f, h), strokeWidth = 0.5f)
        }
        drawCircle(theme.primary.copy(alpha = pulse * 0.4f), 10f, Offset(w/2, h/2))
        drawCircle(
            color = theme.secondary.copy(alpha = pulse * 0.2f),
            radius = 18f,
            center = Offset(w/2, h/2),
            style = Stroke(1f)
        )
    }
}

@Composable
private fun ClassicCardBackCanvas(theme: TarotThemeColors) {
    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        for (i in 0..20) {
            val t = i / 20f
            drawLine(theme.primary.copy(alpha = 0.08f), Offset(0f, h*t), Offset(w, h*t), 0.5f)
            drawLine(theme.primary.copy(alpha = 0.08f), Offset(w*t, 0f), Offset(w*t, h), 0.5f)
        }
        val cx = w/2; val cy = h/2; val r = w * 0.28f
        val star = Path()
        for (i in 0..8) {
            val angle = (i * 45f - 90f) * PI.toFloat() / 180f
            val rr = if (i % 2 == 0) r else r * 0.45f
            val x = cx + cos(angle) * rr; val y = cy + sin(angle) * rr
            if (i == 0) star.moveTo(x, y) else star.lineTo(x, y)
        }
        star.close()
        drawPath(star, theme.primary.copy(alpha = 0.35f))
        drawPath(star, theme.primary.copy(alpha = 0.5f), style = Stroke(0.8f))
    }
}

// ─── Energy hint chip ─────────────────────────────────────────────────────────
@Composable
fun EnergyHintChip(card: TarotCard, theme: TarotThemeColors, modifier: Modifier = Modifier) {
    val color = arcanaColor(card, theme)
    // #5: strictly one line — use width constraint + show only first 2 keywords
    val displayKw = card.keywords.take(2).joinToString("  ")
    Surface(
        modifier = modifier.widthIn(max = CARD_WIDTH * 0.85f),
        color  = color.copy(alpha = 0.12f),
        shape  = RoundedCornerShape(20.dp),
        border = BorderStroke(0.5.dp, color.copy(alpha = 0.35f)),
    ) {
        androidx.compose.material3.Text(
            text     = displayKw,
            style    = MaterialTheme.typography.labelSmall.copy(color = color, fontSize = 8.sp),
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
        )
    }
}

// ─── Helpers ─────────────────────────────────────────────────────────────────
fun arcanaColor(card: TarotCard, theme: TarotThemeColors): Color = when (card.arcana) {
    Arcana.MAJOR     -> theme.majorColor
    Arcana.CUPS      -> theme.cupsColor
    Arcana.WANDS     -> theme.wandsColor
    Arcana.SWORDS    -> theme.swordsColor
    Arcana.PENTACLES -> theme.pentaclesColor
}
