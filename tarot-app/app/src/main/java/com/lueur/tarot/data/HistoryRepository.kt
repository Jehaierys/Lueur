package com.lueur.tarot.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.File

class HistoryRepository(private val context: Context) {

    private val gson = Gson()
    private val historyFile: File get() = File(context.filesDir, "spread_history.json")

    private val _history = MutableStateFlow<List<Spread>>(emptyList())
    val history: StateFlow<List<Spread>> = _history

    suspend fun load() = withContext(Dispatchers.IO) {
        runCatching {
            if (historyFile.exists()) {
                val json = historyFile.readText()
                val type = object : TypeToken<List<SpreadJson>>() {}.type
                val raw: List<SpreadJson> = gson.fromJson(json, type) ?: emptyList()
                _history.value = raw.map { it.toSpread() }
            }
        }
    }

    suspend fun saveSpread(spread: Spread, enabled: Boolean) {
        if (!enabled) return
        val updated = listOf(spread) + _history.value
        _history.value = updated
        persistAsync(updated)
    }

    suspend fun clearAll() {
        _history.value = emptyList()
        withContext(Dispatchers.IO) { historyFile.delete() }
    }

    suspend fun deleteSpread(id: String) {
        val updated = _history.value.filter { it.id != id }
        _history.value = updated
        persistAsync(updated)
    }

    private suspend fun persistAsync(list: List<Spread>) = withContext(Dispatchers.IO) {
        runCatching {
            val raw = list.map { SpreadJson.fromSpread(it) }
            historyFile.writeText(gson.toJson(raw))
        }
    }

    // ─── JSON DTOs (store only card IDs to avoid giant files) ─────────────────
    data class DrawnCardJson(
        val instanceId: String,
        val cardId: Int,
        val orientation: String,
        val drawnAtMs: Long,
    ) {
        fun toDrawnCard() = DrawnCard(
            instanceId = instanceId,
            card = TarotDeck.allCards.first { it.id == cardId },
            orientation = CardOrientation.valueOf(orientation),
            drawnAtMs = drawnAtMs,
        )
        companion object {
            fun from(c: DrawnCard) = DrawnCardJson(c.instanceId, c.card.id, c.orientation.name, c.drawnAtMs)
        }
    }

    data class SpreadJson(
        val id: String,
        val createdAtMs: Long,
        val cards: List<DrawnCardJson>,
        val gridCols: Int,
        val label: String,
        val note: String,
    ) {
        fun toSpread() = Spread(
            id = id,
            createdAtMs = createdAtMs,
            cards = cards.map { it.toDrawnCard() },
            gridCols = gridCols,
            label = label,
            note = note
        )
        companion object {
            fun fromSpread(s: Spread) = SpreadJson(
                s.id,
                s.createdAtMs,
                s.cards.map { DrawnCardJson.from(it) },
                s.gridCols,
                s.label,
                s.note)
        }
    }
}
