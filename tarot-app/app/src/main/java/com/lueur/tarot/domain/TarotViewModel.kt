package com.lueur.tarot.domain

import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lueur.tarot.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TarotUiState(
    // Session
    val remainingDeck: List<TarotCard> = emptyList(),
    val currentSpread: List<DrawnCard> = emptyList(),
    val bottomCard: TarotCard? = null,              // "дно колоды"
    val sessionDrawnCards: List<TarotCard> = emptyList(), // cards drawn this session (for "посмотреть оставшиеся")
    val isShuffling: Boolean = false,

    // Settings
    val settings: AppSettings = AppSettings(),

    // History
    val spreadHistory: List<Spread> = emptyList(),

    // UI state
    val showRemainingDeck: Boolean = false,
    val selectedCardForDetail: DrawnCard? = null,
    val showClearHistoryConfirm: Boolean = false,
    val deckAnimTrigger: Int = 0,                  // incremented to trigger animation
    val showBottomCard: Boolean = false,
)

class TarotViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepo = SettingsRepository(application)
    private val historyRepo = HistoryRepository(application)

    private val _uiState = MutableStateFlow(TarotUiState())
    val uiState: StateFlow<TarotUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Load settings + history in parallel
            launch { historyRepo.load() }

            launch {
                settingsRepo.settings.collect { s ->
                    _uiState.update { it.copy(settings = s) }
                }
            }
            launch {
                historyRepo.history.collect { h ->
                    _uiState.update { it.copy(spreadHistory = h) }
                }
            }

            // Init deck after settings loaded
            shuffleDeck()
        }
    }

    // ─── Deck operations ──────────────────────────────────────────────────────

    fun shuffleDeck() {
        val deck = TarotDeck.shuffled()
        val bottom = deck.last()
        _uiState.update {
            it.copy(
                remainingDeck = deck,
                bottomCard = bottom,
                isShuffling = true,
                deckAnimTrigger = it.deckAnimTrigger + 1,
            )
        }
        viewModelScope.launch {
            kotlinx.coroutines.delay(600)
            _uiState.update { it.copy(isShuffling = false) }
        }
        haptic(HapticType.SHUFFLE)
    }

    fun drawCard() {
        val state = _uiState.value
        val deck = state.remainingDeck.toMutableList()
        if (deck.isEmpty()) return

        val card = deck.removeAt(0)
        val orientation = if (state.settings.reverseEnabled && Math.random() < 0.3) {
            CardOrientation.REVERSED
        } else {
            CardOrientation.UPRIGHT
        }
        val drawn = DrawnCard(card = card, orientation = orientation)

        _uiState.update {
            it.copy(
                remainingDeck = deck,
                currentSpread = it.currentSpread + drawn,
                sessionDrawnCards = it.sessionDrawnCards + card,
            )
        }
        haptic(HapticType.DRAW)
    }

    fun startNewSpread() {
        val state = _uiState.value
        // Save current spread if non-empty
        if (state.currentSpread.isNotEmpty()) {
            val spread = Spread(
                cards = state.currentSpread,
                gridCols = state.settings.gridCols,
            )
            viewModelScope.launch {
                historyRepo.saveSpread(spread, state.settings.saveHistory)
            }
        }
        _uiState.update {
            it.copy(
                currentSpread = emptyList(),
                showBottomCard = false,
                selectedCardForDetail = null,
            )
        }
        haptic(HapticType.ACTION)
    }

    fun setGridCols(cols: Int) {
        viewModelScope.launch { settingsRepo.setGridCols(cols) }
    }

    fun setGroupingMode(mode: Int) {
        viewModelScope.launch { settingsRepo.setGroupingMode(mode) }
    }

    // ─── Group cards in spread ─────────────────────────────────────────────────
    fun groupedSpread(cards: List<DrawnCard>, groupSize: Int): List<List<DrawnCard>> {
        return cards.chunked(groupSize)
    }

    // ─── Bottom card ──────────────────────────────────────────────────────────
    fun toggleBottomCard() {
        _uiState.update { it.copy(showBottomCard = !it.showBottomCard) }
        haptic(HapticType.CLICK)
    }

    // ─── Card detail ──────────────────────────────────────────────────────────
    fun selectCardForDetail(card: DrawnCard?) {
        _uiState.update { it.copy(selectedCardForDetail = card) }
    }

    // ─── Remaining deck viewer ────────────────────────────────────────────────
    fun setShowRemainingDeck(v: Boolean) {
        _uiState.update { it.copy(showRemainingDeck = v) }
    }

    // ─── History ──────────────────────────────────────────────────────────────
    fun requestClearHistory() {
        _uiState.update { it.copy(showClearHistoryConfirm = true) }
    }

    fun dismissClearHistoryConfirm() {
        _uiState.update { it.copy(showClearHistoryConfirm = false) }
    }

    fun confirmClearHistory() {
        viewModelScope.launch {
            historyRepo.clearAll()
            _uiState.update { it.copy(showClearHistoryConfirm = false) }
        }
    }

    fun deleteSpread(id: String) {
        viewModelScope.launch { historyRepo.deleteSpread(id) }
    }

    // ─── Settings passthrough ─────────────────────────────────────────────────
    fun setLanguage(lang: AppLanguage) = viewModelScope.launch { settingsRepo.setLanguage(lang) }
    fun setCardTheme(theme: CardTheme) = viewModelScope.launch { settingsRepo.setCardTheme(theme) }
    fun setSaveHistory(v: Boolean) = viewModelScope.launch { settingsRepo.setSaveHistory(v) }
    fun setShowEnergy(v: Boolean) = viewModelScope.launch { settingsRepo.setShowEnergy(v) }
    fun setReverseEnabled(v: Boolean) = viewModelScope.launch { settingsRepo.setReverseEnabled(v) }
    fun setHapticEnabled(v: Boolean) = viewModelScope.launch { settingsRepo.setHapticEnabled(v) }

    // ─── Haptic ───────────────────────────────────────────────────────────────
    private enum class HapticType { DRAW, SHUFFLE, ACTION, CLICK }

    private fun haptic(type: HapticType) {
        if (!_uiState.value.settings.hapticEnabled) return
        val ctx = getApplication<Application>()
        val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            ctx.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
        vibrator ?: return
        val ms = when (type) {
            HapticType.DRAW -> 30L
            HapticType.SHUFFLE -> 60L
            HapticType.ACTION -> 20L
            HapticType.CLICK -> 15L
        }
        vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}
