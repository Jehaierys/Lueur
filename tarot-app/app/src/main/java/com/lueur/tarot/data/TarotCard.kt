package com.lueur.tarot.data

import java.util.UUID

// ─── Card arcana types ────────────────────────────────────────────────────────
enum class Arcana { MAJOR, CUPS, WANDS, SWORDS, PENTACLES }

// ─── Orientation ──────────────────────────────────────────────────────────────
enum class CardOrientation { UPRIGHT, REVERSED }

// ─── Theme style ─────────────────────────────────────────────────────────────
enum class CardTheme { CLASSIC, LUEUR, NOIR }

// ─── A single tarot card ──────────────────────────────────────────────────────
data class TarotCard(
    val id: Int,
    val arcana: Arcana,
    val nameEn: String,
    val nameRu: String,
    val nameFr: String,
    val numberLabel: String,           // "0", "I", "XIV", "Ace", "2", etc.
    val energyEn: String,
    val energyRu: String,
    val energyFr: String,
    val keywords: List<String>,        // Universal short glyphs
)

// ─── A drawn card instance (with orientation + position) ──────────────────────
data class DrawnCard(
    val instanceId: String = UUID.randomUUID().toString(),
    val card: TarotCard,
    val orientation: CardOrientation,
    val drawnAtMs: Long = System.currentTimeMillis(),
)

// ─── A spread (layout) ────────────────────────────────────────────────────────
data class Spread(
    val id: String = UUID.randomUUID().toString(),
    val createdAtMs: Long = System.currentTimeMillis(),
    val cards: List<DrawnCard>,
    val gridCols: Int = 1,             // How many columns in the grid
    val note: String = "",
)

// ─── Full deck definition ─────────────────────────────────────────────────────
object TarotDeck {

    val allCards: List<TarotCard> = buildList {

        // ══════════════════════ MAJOR ARCANA (0-21) ══════════════════════════

        add(TarotCard(0, Arcana.MAJOR, "The Fool", "Шут", "Le Mat",
            "0",
            "Beginnings, innocence, spontaneity, free spirit",
            "Начало, невинность, спонтанность, свободный дух",
            "Commencements, innocence, spontanéité, esprit libre",
            listOf("✦ Начало", "✦ Прыжок", "✦ Доверие")))

        add(TarotCard(1, Arcana.MAJOR, "The Magician", "Маг", "Le Bateleur",
            "I",
            "Willpower, desire, creation, manifestation",
            "Воля, желание, творение, манифестация",
            "Volonté, désir, création, manifestation",
            listOf("✦ Воля", "✦ Действие", "✦ Мастерство")))

        add(TarotCard(2, Arcana.MAJOR, "The High Priestess", "Верховная Жрица", "La Papesse",
            "II",
            "Intuition, sacred knowledge, divine feminine, subconscious",
            "Интуиция, сакральное знание, женское начало, подсознание",
            "Intuition, connaissance sacrée, féminin divin, inconscient",
            listOf("✦ Глубина", "✦ Молчание", "✦ Тайна")))

        add(TarotCard(3, Arcana.MAJOR, "The Empress", "Императрица", "L'Impératrice",
            "III",
            "Femininity, beauty, nature, abundance, nurturing",
            "Женственность, красота, природа, изобилие, забота",
            "Féminité, beauté, nature, abondance, bienveillance",
            listOf("✦ Рост", "✦ Изобилие", "✦ Тело")))

        add(TarotCard(4, Arcana.MAJOR, "The Emperor", "Император", "L'Empereur",
            "IV",
            "Authority, structure, protection, fatherhood",
            "Авторитет, структура, защита, отцовство",
            "Autorité, structure, protection, paternité",
            listOf("✦ Порядок", "✦ Сила", "✦ Основа")))

        add(TarotCard(5, Arcana.MAJOR, "The Hierophant", "Иерофант", "Le Pape",
            "V",
            "Tradition, conformity, morality, ethics, spiritual guidance",
            "Традиция, конформизм, мораль, духовное руководство",
            "Tradition, conformité, moralité, guidance spirituelle",
            listOf("✦ Учение", "✦ Путь", "✦ Традиция")))

        add(TarotCard(6, Arcana.MAJOR, "The Lovers", "Влюблённые", "Les Amoureux",
            "VI",
            "Love, harmony, relationships, values alignment, choices",
            "Любовь, гармония, отношения, выбор, ценности",
            "Amour, harmonie, relations, alignement, choix",
            listOf("✦ Выбор", "✦ Союз", "✦ Ценности")))

        add(TarotCard(7, Arcana.MAJOR, "The Chariot", "Колесница", "Le Chariot",
            "VII",
            "Control, willpower, success, determination, direction",
            "Контроль, воля, успех, решимость, направление",
            "Contrôle, volonté, succès, détermination, direction",
            listOf("✦ Движение", "✦ Победа", "✦ Фокус")))

        add(TarotCard(8, Arcana.MAJOR, "Strength", "Сила", "La Force",
            "VIII",
            "Strength, courage, persuasion, patience, inner power",
            "Сила, мужество, терпение, внутренняя мощь",
            "Force, courage, persuasion, patience, pouvoir intérieur",
            listOf("✦ Мягкость", "✦ Укрощение", "✦ Сердце")))

        add(TarotCard(9, Arcana.MAJOR, "The Hermit", "Отшельник", "L'Hermite",
            "IX",
            "Soul searching, introspection, inner guidance, solitude",
            "Поиск себя, интроспекция, внутренний свет, одиночество",
            "Introspection, guidance intérieur, solitude",
            listOf("✦ Уход", "✦ Свет", "✦ Одиночество")))

        add(TarotCard(10, Arcana.MAJOR, "Wheel of Fortune", "Колесо Фортуны", "La Roue de Fortune",
            "X",
            "Good luck, karma, life cycles, destiny, a turning point",
            "Удача, карма, жизненные циклы, судьба, поворотный момент",
            "Chance, karma, cycles de vie, destin, tournant",
            listOf("✦ Цикл", "✦ Удача", "✦ Судьба")))

        add(TarotCard(11, Arcana.MAJOR, "Justice", "Справедливость", "La Justice",
            "XI",
            "Justice, fairness, truth, cause and effect, law",
            "Справедливость, честность, истина, причина и следствие",
            "Justice, équité, vérité, cause et effet, loi",
            listOf("✦ Равновесие", "✦ Истина", "✦ Закон")))

        add(TarotCard(12, Arcana.MAJOR, "The Hanged Man", "Повешенный", "Le Pendu",
            "XII",
            "Sacrifice, new perspectives, enlightenment, suspension",
            "Жертва, новый взгляд, просветление, пауза",
            "Sacrifice, nouvelles perspectives, illumination, suspension",
            listOf("✦ Пауза", "✦ Жертва", "✦ Взгляд")))

        add(TarotCard(13, Arcana.MAJOR, "Death", "Смерть", "La Mort",
            "XIII",
            "Endings, change, transformation, transition",
            "Завершение, перемены, трансформация, переход",
            "Fins, changement, transformation, transition",
            listOf("✦ Конец", "✦ Переход", "✦ Обновление")))

        add(TarotCard(14, Arcana.MAJOR, "Temperance", "Умеренность", "La Tempérance",
            "XIV",
            "Balance, moderation, patience, purpose, meaning",
            "Баланс, умеренность, терпение, смысл",
            "Équilibre, modération, patience, sens",
            listOf("✦ Алхимия", "✦ Баланс", "✦ Поток")))

        add(TarotCard(15, Arcana.MAJOR, "The Devil", "Дьявол", "Le Diable",
            "XV",
            "Shadow self, attachment, addiction, bondage, restriction",
            "Тень, привязанность, зависимость, оковы",
            "Ombre, attachement, dépendance, restriction",
            listOf("✦ Тень", "✦ Соблазн", "✦ Цепи")))

        add(TarotCard(16, Arcana.MAJOR, "The Tower", "Башня", "La Tour",
            "XVI",
            "Sudden change, upheaval, chaos, revelation, awakening",
            "Внезапные перемены, хаос, откровение, пробуждение",
            "Changement soudain, chaos, révélation, éveil",
            listOf("✦ Разрушение", "✦ Откровение", "✦ Молния")))

        add(TarotCard(17, Arcana.MAJOR, "The Star", "Звезда", "L'Étoile",
            "XVII",
            "Hope, faith, purpose, renewal, spirituality",
            "Надежда, вера, цель, обновление, духовность",
            "Espoir, foi, but, renouveau, spiritualité",
            listOf("✦ Надежда", "✦ Свет", "✦ Дар")))

        add(TarotCard(18, Arcana.MAJOR, "The Moon", "Луна", "La Lune",
            "XVIII",
            "Illusion, fear, subconscious, intuition, dreams",
            "Иллюзия, страх, подсознание, интуиция, сны",
            "Illusion, peur, inconscient, intuition, rêves",
            listOf("✦ Иллюзия", "✦ Сон", "✦ Глубина")))

        add(TarotCard(19, Arcana.MAJOR, "The Sun", "Солнце", "Le Soleil",
            "XIX",
            "Positivity, warmth, fun, success, vitality",
            "Позитивность, тепло, радость, успех, жизненность",
            "Positivité, chaleur, joie, succès, vitalité",
            listOf("✦ Радость", "✦ Ясность", "✦ Жизнь")))

        add(TarotCard(20, Arcana.MAJOR, "Judgement", "Суд", "Le Jugement",
            "XX",
            "Reflection, reckoning, awakening, absolution, inner calling",
            "Рефлексия, пробуждение, прощение, внутренний зов",
            "Réflexion, éveil, absolution, appel intérieur",
            listOf("✦ Зов", "✦ Прощение", "✦ Возрождение")))

        add(TarotCard(21, Arcana.MAJOR, "The World", "Мир", "Le Monde",
            "XXI",
            "Completion, integration, accomplishment, travel, wholeness",
            "Завершённость, интеграция, достижение, целостность",
            "Accomplissement, intégration, réalisation, plénitude",
            listOf("✦ Целостность", "✦ Полнота", "✦ Завершение")))

        // ══════════════════════ CUPS (Кубки) ══════════════════════════════════
        val cupsEn = listOf(
            "Ace" to "New love, creative beginnings, deep intuition",
            "2" to "Unified love, partnership, attraction, connection",
            "3" to "Celebration, friendship, creativity, community",
            "4" to "Apathy, contemplation, disconnectedness, avoidance",
            "5" to "Loss, grief, disappointment, sorrow, regret",
            "6" to "Nostalgia, memories, reunion, happy memories",
            "7" to "Fantasy, illusion, wishful thinking, imagination",
            "8" to "Withdrawal, escapism, abandonment, deep thinking",
            "9" to "Satisfaction, emotional stability, luxury, fulfilment",
            "10" to "Joy, marital happiness, family, fulfilment",
            "Page" to "Emotional intelligence, intuition, sensitivity",
            "Knight" to "Romance, charm, imagination, empathy, creativity",
            "Queen" to "Compassion, calm, gentle strength, emotional maturity",
            "King" to "Emotional balance, generosity, diplomacy, wisdom"
        )
        val cupsRu = listOf(
            "Туз" to "Новая любовь, творческое начало, глубокая интуиция",
            "2" to "Объединённая любовь, партнёрство, влечение",
            "3" to "Праздник, дружба, творчество, сообщество",
            "4" to "Апатия, созерцание, отстранённость",
            "5" to "Потеря, горе, разочарование, сожаление",
            "6" to "Ностальгия, воспоминания, воссоединение",
            "7" to "Фантазия, иллюзия, мечтательность",
            "8" to "Уход, бегство, оставление, глубокое раздумье",
            "9" to "Удовлетворение, эмоциональная стабильность, роскошь",
            "10" to "Радость, семейное счастье, исполнение",
            "Паж" to "Эмоциональный интеллект, интуиция, чувствительность",
            "Рыцарь" to "Романтика, обаяние, воображение, эмпатия",
            "Королева" to "Сострадание, спокойствие, нежная сила",
            "Король" to "Эмоциональный баланс, щедрость, дипломатия"
        )
        val cupsFr = listOf(
            "As" to "Nouvel amour, débuts créatifs, intuition profonde",
            "2" to "Amour uni, partenariat, attraction, connexion",
            "3" to "Célébration, amitié, créativité, communauté",
            "4" to "Apathie, contemplation, déconnexion",
            "5" to "Perte, chagrin, déception, regret",
            "6" to "Nostalgie, souvenirs, réunion, joie passée",
            "7" to "Fantaisie, illusion, pensée magique",
            "8" to "Retrait, évasion, abandon, réflexion",
            "9" to "Satisfaction, stabilité émotionnelle, luxe",
            "10" to "Joie, bonheur familial, épanouissement",
            "Valet" to "Intelligence émotionnelle, intuition, sensibilité",
            "Chevalier" to "Romance, charme, imagination, empathie",
            "Reine" to "Compassion, calme, douceur, maturité émotionnelle",
            "Roi" to "Équilibre émotionnel, générosité, diplomatie"
        )
        val cupsNamesEn = listOf("Ace of Cups","Two of Cups","Three of Cups","Four of Cups","Five of Cups",
            "Six of Cups","Seven of Cups","Eight of Cups","Nine of Cups","Ten of Cups",
            "Page of Cups","Knight of Cups","Queen of Cups","King of Cups")
        val cupsNamesRu = listOf("Туз Кубков","Двойка Кубков","Тройка Кубков","Четвёрка Кубков","Пятёрка Кубков",
            "Шестёрка Кубков","Семёрка Кубков","Восьмёрка Кубков","Девятка Кубков","Десятка Кубков",
            "Паж Кубков","Рыцарь Кубков","Королева Кубков","Король Кубков")
        val cupsNamesFr = listOf("As de Coupes","Deux de Coupes","Trois de Coupes","Quatre de Coupes","Cinq de Coupes",
            "Six de Coupes","Sept de Coupes","Huit de Coupes","Neuf de Coupes","Dix de Coupes",
            "Valet de Coupes","Chevalier de Coupes","Reine de Coupes","Roi de Coupes")
        val cupsNums = listOf("Ace","2","3","4","5","6","7","8","9","10","Page","Knight","Queen","King")
        val cupsKw = listOf(
            listOf("✦ Любовь","✦ Сосуд","✦ Начало"),listOf("✦ Союз","✦ Зеркало","✦ Два"),
            listOf("✦ Праздник","✦ Три","✦ Поток"),listOf("✦ Пауза","✦ Чаша","✦ Выбор"),
            listOf("✦ Потеря","✦ Слёзы","✦ Отпускание"),listOf("✦ Память","✦ Детство","✦ Тепло"),
            listOf("✦ Мечта","✦ Семь","✦ Туман"),listOf("✦ Уход","✦ Море","✦ Поиск"),
            listOf("✦ Довольство","✦ Девять","✦ Полнота"),listOf("✦ Радуга","✦ Семья","✦ Дом"),
            listOf("✦ Мечтатель","✦ Весть","✦ Чувство"),listOf("✦ Романтик","✦ Поток","✦ Сердце"),
            listOf("✦ Мать","✦ Море","✦ Сострадание"),listOf("✦ Мудрец","✦ Глубина","✦ Баланс")
        )
        cupsEn.forEachIndexed { i, (_, en) ->
            add(TarotCard(22+i, Arcana.CUPS, cupsNamesEn[i], cupsNamesRu[i], cupsNamesFr[i],
                cupsNums[i], en, cupsRu[i].second, cupsFr[i].second, cupsKw[i]))
        }

        // ══════════════════════ WANDS (Жезлы) ══════════════════════════════════
        val wandsEn = listOf(
            "Ace" to "Inspiration, new opportunities, growth, potential",
            "2" to "Future planning, progress, decisions, discovery",
            "3" to "Expansion, foresight, travel, looking ahead",
            "4" to "Celebration, harmony, homecoming, community",
            "5" to "Conflict, disagreements, competition, tension",
            "6" to "Victory, recognition, public reward, progress",
            "7" to "Perseverance, defensive, maintaining control",
            "8" to "Rapid action, movement, quick decisions, sudden travel",
            "9" to "Resilience, courage, persistence, grit, last stand",
            "10" to "Burden, responsibility, hard work, completion",
            "Page" to "Inspiration, freedom-thinking, confidence, new ideas",
            "Knight" to "Energy, passion, adventure, impulsiveness",
            "Queen" to "Courage, determination, self-confidence, warmth",
            "King" to "Charisma, leadership, big-picture thinking, boldness"
        )
        val wandsRu = listOf(
            "Туз" to "Вдохновение, новые возможности, рост, потенциал",
            "2" to "Планирование, прогресс, решения, открытие",
            "3" to "Расширение, предвидение, путешествие",
            "4" to "Праздник, гармония, возвращение домой, сообщество",
            "5" to "Конфликт, разногласия, конкуренция, напряжение",
            "6" to "Победа, признание, публичная награда, прогресс",
            "7" to "Настойчивость, защита, сохранение контроля",
            "8" to "Быстрое действие, движение, внезапные перемены",
            "9" to "Устойчивость, мужество, настойчивость, последний рубеж",
            "10" to "Бремя, ответственность, тяжёлый труд, завершение",
            "Паж" to "Вдохновение, независимое мышление, новые идеи",
            "Рыцарь" to "Энергия, страсть, авантюризм, импульсивность",
            "Королева" to "Смелость, решимость, уверенность, теплота",
            "Король" to "Харизма, лидерство, масштабное мышление, смелость"
        )
        val wandsFr = listOf(
            "As" to "Inspiration, nouvelles opportunités, croissance, potentiel",
            "2" to "Planification, progrès, décisions, découverte",
            "3" to "Expansion, prévoyance, voyage, regard vers l'avenir",
            "4" to "Célébration, harmonie, retour au foyer, communauté",
            "5" to "Conflit, désaccords, compétition, tension",
            "6" to "Victoire, reconnaissance, récompense, progrès",
            "7" to "Persévérance, défensive, maintien du contrôle",
            "8" to "Action rapide, mouvement, décisions soudaines",
            "9" to "Résilience, courage, persistance, dernier rempart",
            "10" to "Fardeau, responsabilité, travail acharné",
            "Valet" to "Inspiration, pensée libre, confiance, nouvelles idées",
            "Chevalier" to "Énergie, passion, aventure, impulsivité",
            "Reine" to "Courage, détermination, confiance, chaleur",
            "Roi" to "Charisme, leadership, vision large, audace"
        )
        val wandsNamesEn = listOf("Ace of Wands","Two of Wands","Three of Wands","Four of Wands","Five of Wands",
            "Six of Wands","Seven of Wands","Eight of Wands","Nine of Wands","Ten of Wands",
            "Page of Wands","Knight of Wands","Queen of Wands","King of Wands")
        val wandsNamesRu = listOf("Туз Жезлов","Двойка Жезлов","Тройка Жезлов","Четвёрка Жезлов","Пятёрка Жезлов",
            "Шестёрка Жезлов","Семёрка Жезлов","Восьмёрка Жезлов","Девятка Жезлов","Десятка Жезлов",
            "Паж Жезлов","Рыцарь Жезлов","Королева Жезлов","Король Жезлов")
        val wandsNamesFr = listOf("As de Bâtons","Deux de Bâtons","Trois de Bâtons","Quatre de Bâtons","Cinq de Bâtons",
            "Six de Bâtons","Sept de Bâtons","Huit de Bâtons","Neuf de Bâtons","Dix de Bâtons",
            "Valet de Bâtons","Chevalier de Bâtons","Reine de Bâtons","Roi de Bâtons")
        val wandsNums = listOf("Ace","2","3","4","5","6","7","8","9","10","Page","Knight","Queen","King")
        val wandsKw = listOf(
            listOf("✦ Искра","✦ Огонь","✦ Начало"),listOf("✦ Горизонт","✦ Два","✦ Выбор"),
            listOf("✦ Путь","✦ Три","✦ Даль"),listOf("✦ Дом","✦ Четыре","✦ Радость"),
            listOf("✦ Борьба","✦ Пять","✦ Хаос"),listOf("✦ Успех","✦ Шесть","✦ Слава"),
            listOf("✦ Стойкость","✦ Семь","✦ Защита"),listOf("✦ Скорость","✦ Восемь","✦ Полёт"),
            listOf("✦ Выносливость","✦ Девять","✦ Рубеж"),listOf("✦ Груз","✦ Десять","✦ Ноша"),
            listOf("✦ Искатель","✦ Огонь","✦ Идея"),listOf("✦ Странник","✦ Скорость","✦ Страсть"),
            listOf("✦ Солнце","✦ Огонь","✦ Сила"),listOf("✦ Лидер","✦ Пламя","✦ Видение")
        )
        wandsEn.forEachIndexed { i, (_, en) ->
            add(TarotCard(36+i, Arcana.WANDS, wandsNamesEn[i], wandsNamesRu[i], wandsNamesFr[i],
                wandsNums[i], en, wandsRu[i].second, wandsFr[i].second, wandsKw[i]))
        }

        // ══════════════════════ SWORDS (Мечи) ══════════════════════════════════
        val swordsEn = listOf(
            "Ace" to "Breakthrough, clarity, sharp mind, truth, new idea",
            "2" to "Difficult decisions, indecision, stalemate, blocked emotions",
            "3" to "Heartbreak, suffering, grief, sorrow, separation",
            "4" to "Rest, recuperation, relaxation, contemplation",
            "5" to "Defeat, dishonor, resentment, conflict",
            "6" to "Transition, leaving behind, moving on, rite of passage",
            "7" to "Betrayal, deception, getting away with something",
            "8" to "Isolation, self-imposed restriction, powerlessness",
            "9" to "Anxiety, worry, fear, depression, nightmares",
            "10" to "Failure, collapse, defeat, endings, loss",
            "Page" to "Talkative, curious, mentally alert, alert",
            "Knight" to "Assertive, direct, forceful, quick-thinking",
            "Queen" to "Independent, analytical, complex, communicative",
            "King" to "Intellectual, analytical, direct authority"
        )
        val swordsRu = listOf(
            "Туз" to "Прорыв, ясность, острый ум, истина, новая идея",
            "2" to "Трудные решения, нерешительность, тупик, заблокированные эмоции",
            "3" to "Разбитое сердце, страдание, горе, печаль, разлука",
            "4" to "Отдых, восстановление, расслабление, созерцание",
            "5" to "Поражение, бесчестие, злоба, конфликт",
            "6" to "Переход, оставление позади, движение вперёд",
            "7" to "Предательство, обман, уход от ответственности",
            "8" to "Изоляция, самоограничение, бессилие",
            "9" to "Тревога, беспокойство, страх, депрессия, кошмары",
            "10" to "Провал, крах, поражение, завершения, потеря",
            "Паж" to "Болтливый, любопытный, умственно бдительный",
            "Рыцарь" to "Настойчивый, прямой, стремительный, быстромыслящий",
            "Королева" to "Независимая, аналитическая, сложная, коммуникабельная",
            "Король" to "Интеллектуальный, аналитический, прямой авторитет"
        )
        val swordsFr = listOf(
            "As" to "Percée, clarté, esprit vif, vérité, nouvelle idée",
            "2" to "Décisions difficiles, indécision, impasse, émotions bloquées",
            "3" to "Chagrin d'amour, souffrance, deuil, séparation",
            "4" to "Repos, récupération, relaxation, contemplation",
            "5" to "Défaite, déshonneur, ressentiment, conflit",
            "6" to "Transition, départ, avancement, rite de passage",
            "7" to "Trahison, tromperie, esquiver les conséquences",
            "8" to "Isolement, restriction auto-imposée, impuissance",
            "9" to "Anxiété, inquiétude, peur, dépression, cauchemars",
            "10" to "Échec, effondrement, défaite, fins, perte",
            "Valet" to "Bavard, curieux, alerte mentalement",
            "Chevalier" to "Assertif, direct, impétueux, vif d'esprit",
            "Reine" to "Indépendante, analytique, complexe, communicative",
            "Roi" to "Intellectuel, analytique, autorité directe"
        )
        val swordsNamesEn = listOf("Ace of Swords","Two of Swords","Three of Swords","Four of Swords","Five of Swords",
            "Six of Swords","Seven of Swords","Eight of Swords","Nine of Swords","Ten of Swords",
            "Page of Swords","Knight of Swords","Queen of Swords","King of Swords")
        val swordsNamesRu = listOf("Туз Мечей","Двойка Мечей","Тройка Мечей","Четвёрка Мечей","Пятёрка Мечей",
            "Шестёрка Мечей","Семёрка Мечей","Восьмёрка Мечей","Девятка Мечей","Десятка Мечей",
            "Паж Мечей","Рыцарь Мечей","Королева Мечей","Король Мечей")
        val swordsNamesFr = listOf("As d'Épées","Deux d'Épées","Trois d'Épées","Quatre d'Épées","Cinq d'Épées",
            "Six d'Épées","Sept d'Épées","Huit d'Épées","Neuf d'Épées","Dix d'Épées",
            "Valet d'Épées","Chevalier d'Épées","Reine d'Épées","Roi d'Épées")
        val swordsNums = listOf("Ace","2","3","4","5","6","7","8","9","10","Page","Knight","Queen","King")
        val swordsKw = listOf(
            listOf("✦ Прорыв","✦ Истина","✦ Разум"),listOf("✦ Выбор","✦ Слепота","✦ Тишина"),
            listOf("✦ Боль","✦ Сердце","✦ Дождь"),listOf("✦ Покой","✦ Сон","✦ Меч"),
            listOf("✦ Потеря","✦ Конфликт","✦ Пять"),listOf("✦ Уход","✦ Туман","✦ Лодка"),
            listOf("✦ Обман","✦ Тень","✦ Скрытность"),listOf("✦ Клетка","✦ Страх","✦ Путы"),
            listOf("✦ Ночь","✦ Тревога","✦ Кошмар"),listOf("✦ Конец","✦ Крах","✦ Рассвет"),
            listOf("✦ Слово","✦ Мысль","✦ Скорость"),listOf("✦ Буря","✦ Натиск","✦ Меч"),
            listOf("✦ Ясность","✦ Холод","✦ Правда"),listOf("✦ Закон","✦ Разум","✦ Власть")
        )
        swordsEn.forEachIndexed { i, (_, en) ->
            add(TarotCard(50+i, Arcana.SWORDS, swordsNamesEn[i], swordsNamesRu[i], swordsNamesFr[i],
                swordsNums[i], en, swordsRu[i].second, swordsFr[i].second, swordsKw[i]))
        }

        // ══════════════════════ PENTACLES (Пентакли) ════════════════════════════
        val pentEn = listOf(
            "Ace" to "New financial opportunity, manifestation, abundance",
            "2" to "Multiple priorities, time management, balance",
            "3" to "Teamwork, collaboration, learning, implementation",
            "4" to "Security, conservation, frugality, control",
            "5" to "Financial loss, poverty, lack mindset, isolation",
            "6" to "Generosity, charity, giving, prosperity, sharing",
            "7" to "Perseverance, investment, sustainability, long-view",
            "8" to "Apprenticeship, mastery, skill development, craft",
            "9" to "Luxury, self-sufficiency, financial independence",
            "10" to "Wealth, family, abundance, legacy, inheritance",
            "Page" to "Ambition, desire, diligence, new beginnings",
            "Knight" to "Efficiency, routine, conservative, methodical",
            "Queen" to "Nurturing, practical, providing, down-to-earth",
            "King" to "Wealth, business, leadership, discipline, stability"
        )
        val pentRu = listOf(
            "Туз" to "Новая финансовая возможность, манифестация, изобилие",
            "2" to "Множество приоритетов, тайм-менеджмент, баланс",
            "3" to "Командная работа, сотрудничество, обучение",
            "4" to "Безопасность, бережливость, контроль",
            "5" to "Финансовые потери, бедность, изоляция",
            "6" to "Щедрость, благотворительность, даяние, процветание",
            "7" to "Настойчивость, инвестиции, долгосрочный взгляд",
            "8" to "Ученичество, мастерство, развитие навыков",
            "9" to "Роскошь, самодостаточность, финансовая независимость",
            "10" to "Богатство, семья, изобилие, наследие",
            "Паж" to "Амбиции, желание, усердие, новые начинания",
            "Рыцарь" to "Эффективность, рутина, консерватизм, методичность",
            "Королева" to "Забота, практичность, обеспечение, приземлённость",
            "Король" to "Богатство, бизнес, лидерство, дисциплина, стабильность"
        )
        val pentFr = listOf(
            "As" to "Nouvelle opportunité financière, manifestation, abondance",
            "2" to "Priorités multiples, gestion du temps, équilibre",
            "3" to "Travail d'équipe, collaboration, apprentissage",
            "4" to "Sécurité, épargne, frugalité, contrôle",
            "5" to "Perte financière, pauvreté, isolement",
            "6" to "Générosité, charité, partage, prospérité",
            "7" to "Persévérance, investissement, durabilité",
            "8" to "Apprentissage, maîtrise, développement des compétences",
            "9" to "Luxe, autosuffisance, indépendance financière",
            "10" to "Richesse, famille, abondance, héritage",
            "Valet" to "Ambition, désir, diligence, nouveaux débuts",
            "Chevalier" to "Efficacité, routine, conservateur, méthodique",
            "Reine" to "Bienveillance, pratique, fournissant, terre-à-terre",
            "Roi" to "Richesse, affaires, leadership, discipline, stabilité"
        )
        val pentNamesEn = listOf("Ace of Pentacles","Two of Pentacles","Three of Pentacles","Four of Pentacles",
            "Five of Pentacles","Six of Pentacles","Seven of Pentacles","Eight of Pentacles",
            "Nine of Pentacles","Ten of Pentacles","Page of Pentacles","Knight of Pentacles",
            "Queen of Pentacles","King of Pentacles")
        val pentNamesRu = listOf("Туз Пентаклей","Двойка Пентаклей","Тройка Пентаклей","Четвёрка Пентаклей",
            "Пятёрка Пентаклей","Шестёрка Пентаклей","Семёрка Пентаклей","Восьмёрка Пентаклей",
            "Девятка Пентаклей","Десятка Пентаклей","Паж Пентаклей","Рыцарь Пентаклей",
            "Королева Пентаклей","Король Пентаклей")
        val pentNamesFr = listOf("As de Pentacles","Deux de Pentacles","Trois de Pentacles","Quatre de Pentacles",
            "Cinq de Pentacles","Six de Pentacles","Sept de Pentacles","Huit de Pentacles",
            "Neuf de Pentacles","Dix de Pentacles","Valet de Pentacles","Chevalier de Pentacles",
            "Reine de Pentacles","Roi de Pentacles")
        val pentNums = listOf("Ace","2","3","4","5","6","7","8","9","10","Page","Knight","Queen","King")
        val pentKw = listOf(
            listOf("✦ Монета","✦ Земля","✦ Дар"),listOf("✦ Баланс","✦ Жонглёр","✦ Два"),
            listOf("✦ Мастер","✦ Три","✦ Строение"),listOf("✦ Скупость","✦ Четыре","✦ Удержание"),
            listOf("✦ Нужда","✦ Пять","✦ Холод"),listOf("✦ Дар","✦ Поток","✦ Шесть"),
            listOf("✦ Ожидание","✦ Семь","✦ Рост"),listOf("✦ Труд","✦ Восемь","✦ Навык"),
            listOf("✦ Сад","✦ Девять","✦ Достаток"),listOf("✦ Наследие","✦ Десять","✦ Дом"),
            listOf("✦ Ученик","✦ Земля","✦ Амбиция"),listOf("✦ Методичность","✦ Поле","✦ Труд"),
            listOf("✦ Мать-земля","✦ Сад","✦ Забота"),listOf("✦ Царь","✦ Трон","✦ Богатство")
        )
        pentEn.forEachIndexed { i, (_, en) ->
            add(TarotCard(64+i, Arcana.PENTACLES, pentNamesEn[i], pentNamesRu[i], pentNamesFr[i],
                pentNums[i], en, pentRu[i].second, pentFr[i].second, pentKw[i]))
        }
    }

    fun shuffled(): MutableList<TarotCard> = allCards.toMutableList().apply { shuffle() }
}
