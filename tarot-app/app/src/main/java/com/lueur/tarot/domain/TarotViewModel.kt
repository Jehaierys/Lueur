package com.lueur.tarot.domain

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lueur.tarot.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

data class TarotUiState(
    // Deck
    val remainingDeck: List<TarotCard> = emptyList(),
    val bottomCard: TarotCard? = null,
    val showBottomCard: Boolean = false,
    val bottomCardCooldown: Boolean = false,   // #13: 0.7s block after reveal

    // Current spread – rows allow manual line-breaks (#7)
    val spreadRows: List<List<DrawnCard>> = listOf(emptyList()),

    // History
    val spreadHistory: List<Spread> = emptyList(),

    // Settings
    val settings: AppSettings = AppSettings(),

    // UI
    val showRemainingDeck: Boolean = false,
    val selectedCardForDetail: DrawnCard? = null,
    val showClearHistoryConfirm: Boolean = false,
    val isShuffling: Boolean = false,
)

// Flat view of all drawn cards (for display)
val TarotUiState.allDrawnCards: List<DrawnCard>
    get() = spreadRows.flatten()

val TarotUiState.drawnCount: Int
    get() = allDrawnCards.size

class TarotViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepo = SettingsRepository(application)
    private val historyRepo  = HistoryRepository(application)

    private val _uiState = MutableStateFlow(TarotUiState())
    val uiState: StateFlow<TarotUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch { historyRepo.load() }
            launch { settingsRepo.settings.collect { s -> _uiState.update { it.copy(settings = s) } } }
            launch { historyRepo.history.collect { h -> _uiState.update { it.copy(spreadHistory = h) } } }
            initDeck()
        }
    }

    // ── Deck ─────────────────────────────────────────────────────────────────

    private fun initDeck() {
        val deck = TarotDeck.shuffled()
        _uiState.update {
            it.copy(
                remainingDeck = deck.dropLast(1),
                bottomCard    = deck.last(),
                isShuffling   = false,
            )
        }
    }

    /** Draw one card from the top of the deck. #14: random reversed % per card */
    fun drawCard() {
        val state = _uiState.value
        val deck  = state.remainingDeck.toMutableList()
        if (deck.isEmpty()) return

        val card = deck.removeAt(0)

        // #14: each card gets a fresh random reversal threshold 10-90%
        val reversalChance = if (state.settings.reverseEnabled)
            Random.nextInt(10, 91) / 100f else 0f
        val orientation = if (Random.nextFloat() < reversalChance)
            CardOrientation.REVERSED else CardOrientation.UPRIGHT

        val drawn = DrawnCard(card = card, orientation = orientation)

        // Append to last row
        val rows = state.spreadRows.toMutableList()
        val lastRow = rows.last().toMutableList()
        lastRow.add(drawn)
        rows[rows.lastIndex] = lastRow

        _uiState.update { it.copy(remainingDeck = deck, spreadRows = rows) }
        haptic(HapticType.DRAW)
    }

    /** Add a manual line-break (new row) in the spread (#7) */
    fun addLineBreak() {
        val rows = _uiState.value.spreadRows.toMutableList()
        // Don't add break if last row is already empty
        if (rows.last().isNotEmpty()) {
            rows.add(emptyList())
            _uiState.update { it.copy(spreadRows = rows) }
        }
        haptic(HapticType.CLICK)
    }

    /** Shuffle (#12 revised): put drawn cards + bottom back, reshuffle, keep table clear */
    fun shuffleDeck() {
        val state = _uiState.value
        val drawnCards = state.allDrawnCards.map { it.card }
        val bottom     = listOfNotNull(state.bottomCard)

        val fullDeck = (state.remainingDeck + drawnCards + bottom)
            .toMutableList().also { it.shuffle() }

        _uiState.update {
            it.copy(
                remainingDeck  = fullDeck.dropLast(1),
                bottomCard     = fullDeck.last(),
                spreadRows     = listOf(emptyList()),
                showBottomCard = false,
                isShuffling    = true,
            )
        }
        viewModelScope.launch {
            kotlinx.coroutines.delay(600)
            _uiState.update { it.copy(isShuffling = false) }
        }
        haptic(HapticType.SHUFFLE)
    }

    /** New spread (#12): save current, clear table, keep deck as-is */
    fun startNewSpread() {
        val state = _uiState.value
        if (state.allDrawnCards.isNotEmpty()) {
            val spread = Spread(cards = state.allDrawnCards)
            viewModelScope.launch {
                historyRepo.saveSpread(spread, state.settings.saveHistory)
            }
        }
        _uiState.update {
            it.copy(
                spreadRows     = listOf(emptyList()),
                showBottomCard = false,
                selectedCardForDetail = null,
            )
        }
        haptic(HapticType.ACTION)
    }

    // ── Bottom card (#13) ─────────────────────────────────────────────────────

    fun toggleBottomCard() {
        val state = _uiState.value
        if (state.bottomCardCooldown) return

        if (!state.showBottomCard) {
            // Reveal: start cooldown
            _uiState.update { it.copy(showBottomCard = true, bottomCardCooldown = true) }
            viewModelScope.launch {
                kotlinx.coroutines.delay(700)
                _uiState.update { it.copy(bottomCardCooldown = false) }
            }
        } else {
            // Return card to random position in deck
            val card = state.bottomCard ?: return
            val deck = state.remainingDeck.toMutableList()
            val pos  = if (deck.isEmpty()) 0 else Random.nextInt(deck.size)
            deck.add(pos, card)

            // New bottom is last card
            val newBottom = deck.removeAt(deck.lastIndex)
            _uiState.update {
                it.copy(
                    showBottomCard = false,
                    bottomCard     = newBottom,
                    remainingDeck  = deck,
                )
            }
        }
        haptic(HapticType.CLICK)
    }

    // ── Card detail ───────────────────────────────────────────────────────────

    fun selectCardForDetail(card: DrawnCard?) {
        _uiState.update { it.copy(selectedCardForDetail = card) }
    }

    // ── Remaining deck ────────────────────────────────────────────────────────

    fun setShowRemainingDeck(v: Boolean) {
        _uiState.update { it.copy(showRemainingDeck = v) }
    }

    // ── History ───────────────────────────────────────────────────────────────

    fun requestClearHistory()  { _uiState.update { it.copy(showClearHistoryConfirm = true) } }
    fun dismissClearHistoryConfirm() { _uiState.update { it.copy(showClearHistoryConfirm = false) } }

    fun confirmClearHistory() {
        viewModelScope.launch {
            historyRepo.clearAll()
            _uiState.update { it.copy(showClearHistoryConfirm = false) }
        }
    }

    fun deleteSpread(id: String) { viewModelScope.launch { historyRepo.deleteSpread(id) } }

    // ── Settings ──────────────────────────────────────────────────────────────

    fun setLanguage(lang: AppLanguage) {
        viewModelScope.launch { settingsRepo.setLanguage(lang) }
    }
    fun setCardTheme(theme: CardTheme)   = viewModelScope.launch { settingsRepo.setCardTheme(theme) }
    fun setSaveHistory(v: Boolean)       = viewModelScope.launch { settingsRepo.setSaveHistory(v) }
    fun setShowEnergy(v: Boolean)        = viewModelScope.launch { settingsRepo.setShowEnergy(v) }
    fun setReverseEnabled(v: Boolean)    = viewModelScope.launch { settingsRepo.setReverseEnabled(v) }
    fun setHapticEnabled(v: Boolean)     = viewModelScope.launch { settingsRepo.setHapticEnabled(v) }

    // ── Haptic ────────────────────────────────────────────────────────────────

    private enum class HapticType { DRAW, SHUFFLE, ACTION, CLICK }

    private fun haptic(type: HapticType) {
        if (!_uiState.value.settings.hapticEnabled) return
        val ctx = getApplication<Application>()
        val vib: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            ctx.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
        val ms = when (type) {
            HapticType.DRAW    -> 30L
            HapticType.SHUFFLE -> 60L
            HapticType.ACTION  -> 20L
            HapticType.CLICK   -> 15L
        }
        vib?.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}
