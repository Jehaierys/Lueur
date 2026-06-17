package com.lueur.tarot.data

import java.util.UUID

enum class Arcana { MAJOR, CUPS, WANDS, SWORDS, PENTACLES }
enum class CardOrientation { UPRIGHT, REVERSED }
enum class CardTheme { CLASSIC, LUEUR, NOIR, WAITE }

data class TarotCard(
    val id: Int,
    val arcana: Arcana,
    val nameEn: String,
    val nameRu: String,
    val nameFr: String,
    val numberLabel: String,
    val energyEn: String,
    val energyRu: String,
    val energyFr: String,
    val keywords: List<String>,   // 5-7 items
    val svgAsset: String,         // e.g. "card_00"
)

data class DrawnCard(
    val instanceId: String = UUID.randomUUID().toString(),
    val card: TarotCard,
    val orientation: CardOrientation,
    val drawnAtMs: Long = System.currentTimeMillis(),
)

data class Spread(
    val id: String = UUID.randomUUID().toString(),
    val createdAtMs: Long = System.currentTimeMillis(),
    val cards: List<DrawnCard>,
    val note: String = "",
)

object TarotDeck {
    val allCards: List<TarotCard> = buildList {
        add(TarotCard(0, Arcana.MAJOR, "The Fool", "Шут", "Le Mat", "0",
            "The Fool stands at the precipice of a grand adventure, embodying pure potential and childlike trust in the universe. He carries only what he needs, unburdened by the past or anxiety about the future. This card invites you to leap into the unknown with an open heart. Every great journey begins with this single, courageous step into mystery.",
            "Шут стоит на краю обрыва, воплощая чистый потенциал и детское доверие к миру. Он несёт лишь самое необходимое, освободившись от груза прошлого. Карта приглашает прыгнуть в неизвестность с открытым сердцем. Каждое великое путешествие начинается с этого смелого шага в тайну.",
            "Le Fou se tient au bord du précipice, incarnant le potentiel pur et la confiance enfantine en l'univers. Cette carte vous invite à sauter dans l'inconnu avec un coeur ouvert. Chaque grand voyage commence par ce seul pas courageux vers le mystère.",
            listOf("✦ Начало", "✦ Доверие", "✦ Прыжок", "✦ Свобода", "✦ Потенциал", "✦ Приключение"), "card_00"))

        add(TarotCard(1, Arcana.MAJOR, "The Magician", "Маг", "Le Bateleur", "I",
            "The Magician channels divine energy through his body, transforming intention into reality with focused will. He has all four elements at his disposal and knows how to wield them. Above him the infinity symbol reminds us that power is limitless when aligned with purpose. What you need to create already exists within you.",
            "Маг направляет божественную энергию через своё тело, превращая намерение в реальность силой воли. В его распоряжении все четыре элемента. Символ бесконечности над ним напоминает: сила безгранична, когда согласована с целью. Всё необходимое для творения уже есть внутри вас.",
            "Le Magicien canalise l'énergie divine à travers son corps, transformant l'intention en réalité par la volonté focalisée. Il dispose des quatre éléments. Le symbole de l'infini rappelle que le pouvoir est illimité quand il est aligné sur le but.",
            listOf("✦ Воля", "✦ Мастерство", "✦ Манифестация", "✦ Концентрация", "✦ Ресурсы", "✦ Действие"), "card_01"))

        add(TarotCard(2, Arcana.MAJOR, "The High Priestess", "Верховная Жрица", "La Papesse", "II",
            "The High Priestess sits between the pillars of duality, keeper of hidden knowledge and sacred mysteries. She does not speak — she listens, feeling the currents beneath the surface of things. Her wisdom comes not from logic but from deep attunement to the unseen. Trust what you feel before you can explain it.",
            "Верховная Жрица сидит между столпами двойственности, хранительница скрытого знания и священных тайн. Она не говорит — она слушает, чувствуя потоки под поверхностью вещей. Её мудрость приходит из глубокого созвучия с незримым. Доверяй тому, что чувствуешь, прежде чем сможешь объяснить.",
            "La Grande Prêtresse siège entre les piliers de la dualité, gardienne du savoir caché. Elle n'exprime pas — elle écoute, ressentant les courants sous la surface des choses. Sa sagesse vient d'un profond accordage à l'invisible.",
            listOf("✦ Интуиция", "✦ Тайна", "✦ Молчание", "✦ Подсознание", "✦ Глубина", "✦ Сакральное"), "card_02"))

        add(TarotCard(3, Arcana.MAJOR, "The Empress", "Императрица", "L'Impératrice", "III",
            "The Empress is the great mother of the tarot, embodying fertility, abundance and the sensual beauty of the natural world. She rules the cycles of growth — the seed, the flower, the harvest. In her presence creativity flows effortlessly and life multiplies. Allow yourself to receive, to bloom, to be nourished by the world around you.",
            "Императрица — великая мать таро, воплощающая плодородие, изобилие и чувственную красоту природного мира. Она правит циклами роста — семенем, цветком, урожаем. В её присутствии творчество течёт без усилий. Позволь себе получать, расцветать, питаться от окружающего мира.",
            "L'Impératrice est la grande mère du tarot, incarnant la fertilité, l'abondance et la beauté sensuelle du monde naturel. Elle gouverne les cycles de croissance. En sa présence la créativité coule sans effort.",
            listOf("✦ Изобилие", "✦ Рождение", "✦ Природа", "✦ Забота", "✦ Красота", "✦ Чувственность"), "card_03"))

        add(TarotCard(4, Arcana.MAJOR, "The Emperor", "Император", "L'Empereur", "IV",
            "The Emperor represents structure, authority and the power to create order from chaos. He is the father archetype — protective, disciplined and concerned with building lasting foundations. His strength lies not in force but in the wisdom of clear boundaries and consistent action. Where the Empress nurtures, the Emperor directs.",
            "Император представляет структуру, власть и силу создавать порядок из хаоса. Он — архетип отца: защищающий, дисциплинированный, озабоченный созданием прочных основ. Его сила — в мудрости чётких границ и последовательных действий.",
            "L'Empereur représente la structure, l'autorité et le pouvoir de créer l'ordre à partir du chaos. Il est l'archétype du père — protecteur, discipliné et soucieux de bâtir des fondations durables.",
            listOf("✦ Власть", "✦ Структура", "✦ Порядок", "✦ Защита", "✦ Основа", "✦ Дисциплина"), "card_04"))

        add(TarotCard(5, Arcana.MAJOR, "The Hierophant", "Иерофант", "Le Pape", "V",
            "The Hierophant is the bridge between the human and the divine, transmitter of sacred tradition and collective wisdom. He asks you to seek meaning within established structures. This is not blind obedience but the deep nourishment found in belonging to something greater than yourself. What teachings have shaped who you are?",
            "Иерофант — мост между человеческим и божественным, передатчик священной традиции и коллективной мудрости. Он предлагает искать смысл в установленных структурах. Это глубокое питание от принадлежности к чему-то большему, чем ты сам.",
            "Le Hiérophante est le pont entre l'humain et le divin, transmetteur de la tradition sacrée et de la sagesse collective. Il vous invite à chercher le sens au sein des structures établies.",
            listOf("✦ Традиция", "✦ Учение", "✦ Принадлежность", "✦ Духовность", "✦ Обряд", "✦ Мудрость"), "card_05"))

        add(TarotCard(6, Arcana.MAJOR, "The Lovers", "Влюблённые", "Les Amoureux", "VI",
            "The Lovers card is ultimately about the choices we make when guided by our deepest values and desires. It speaks of love and partnership — but also of alignment: when head, heart and soul point in the same direction. The angel above blesses conscious union. What are you choosing to unite with, and does it honour your whole self?",
            "Карта Влюблённых — о выборе, который мы делаем, руководствуясь нашими глубочайшими ценностями. Она говорит о любви и о согласованности: когда ум, сердце и душа указывают в одном направлении. Ангел благословляет осознанный союз.",
            "La carte des Amoureux parle des choix que nous faisons guidés par nos valeurs et désirs les plus profonds. Elle parle aussi d'alignement: quand la tête, le coeur et l'âme pointent dans la même direction.",
            listOf("✦ Выбор", "✦ Союз", "✦ Ценности", "✦ Согласованность", "✦ Любовь", "✦ Гармония"), "card_06"))

        add(TarotCard(7, Arcana.MAJOR, "The Chariot", "Колесница", "Le Chariot", "VII",
            "The Chariot represents the triumph of will over opposing forces — the ability to hold contradictions in tension and move forward anyway. The two sphinxes pull in different directions, yet the charioteer commands both through inner mastery rather than reins. Victory here comes from disciplined focus and unshakeable resolve.",
            "Колесница представляет победу воли над противоборствующими силами — способность удерживать противоречия в напряжении и всё равно двигаться вперёд. Два сфинкса тянут в разные стороны, но возница управляет обоими через внутреннее мастерство.",
            "Le Chariot représente le triomphe de la volonté sur les forces opposées. Les deux sphinx tirent dans des directions différentes, pourtant le conducteur les commande par la maîtrise intérieure.",
            listOf("✦ Победа", "✦ Воля", "✦ Движение", "✦ Контроль", "✦ Решимость", "✦ Направление"), "card_07"))

        add(TarotCard(8, Arcana.MAJOR, "Strength", "Сила", "La Force", "VIII",
            "Strength shows us that true power is gentle — it is the woman who tames the lion not with force but with love and calm authority. This card speaks of inner courage, the kind that faces fear without armour. It is the strength to remain compassionate when everything in you wants to react.",
            "Сила показывает, что истинная власть нежна — это женщина, укрощающая льва не силой, а любовью и спокойным авторитетом. Карта говорит о внутреннем мужестве — том, что встречает страх без доспехов.",
            "La Force nous montre que le vrai pouvoir est doux — c'est la femme qui apprivoise le lion non par la force mais par l'amour et l'autorité calme. Cette carte parle de courage intérieur.",
            listOf("✦ Мягкость", "✦ Укрощение", "✦ Мужество", "✦ Терпение", "✦ Сострадание", "✦ Внутренняя сила"), "card_08"))

        add(TarotCard(9, Arcana.MAJOR, "The Hermit", "Отшельник", "L'Hermite", "IX",
            "The Hermit withdraws from the noise of the world to find the light that can only be discovered in solitude. His lantern holds a six-pointed star — the light of wisdom earned through long inner work. He does not hide; he illuminates a path for those who are ready. Sometimes the most profound guidance comes from going within.",
            "Отшельник удаляется от шума мира, чтобы найти свет, который можно обнаружить только в одиночестве. В его фонаре — шестиконечная звезда, свет мудрости, заработанный долгой внутренней работой. Он не прячется — он освещает путь.",
            "L'Ermite se retire du bruit du monde pour trouver la lumière qui ne peut être découverte que dans la solitude. Sa lanterne contient une étoile — la lumière de la sagesse gagnée par un long travail intérieur.",
            listOf("✦ Уединение", "✦ Мудрость", "✦ Поиск", "✦ Внутренний свет", "✦ Путь", "✦ Созерцание"), "card_09"))

        add(TarotCard(10, Arcana.MAJOR, "Wheel of Fortune", "Колесо Фортуны", "La Roue de Fortune", "X",
            "The Wheel of Fortune turns endlessly, reminding us that change is the only constant. What rises must fall; what falls will rise again. The four living creatures in the corners read their books — wisdom persists through every turn. Your circumstances are shifting: can you stay centred in the eye of the wheel?",
            "Колесо Фортуны вращается бесконечно, напоминая, что перемены — единственная константа. То, что поднимается, упадёт; то, что падает, поднимется. Четыре существа по углам читают книги — мудрость сохраняется через каждый оборот.",
            "La Roue de Fortune tourne sans cesse, nous rappelant que le changement est la seule constante. Ce qui monte doit tomber; ce qui tombe se relèvera. La sagesse persiste à chaque tour.",
            listOf("✦ Цикл", "✦ Судьба", "✦ Карма", "✦ Перемены", "✦ Удача", "✦ Поворот"), "card_10"))

        add(TarotCard(11, Arcana.MAJOR, "Justice", "Справедливость", "La Justice", "XI",
            "Justice holds her scales level and her sword raised — cause and consequence are inseparable, and she misses nothing. This card does not promise that life is fair; it says that truth ultimately asserts itself. Every action creates ripples, every choice has weight. Look clearly at what you have sown and what harvest is now due.",
            "Справедливость держит весы горизонтально, меч поднят — причина и следствие неразделимы. Эта карта не обещает, что жизнь справедлива; она говорит, что истина в конечном счёте утверждает себя.",
            "La Justice tient ses balances à niveau et son épée levée — cause et conséquence sont inséparables. Cette carte dit que la vérité s'affirme finalement. Chaque action crée des ondulations.",
            listOf("✦ Истина", "✦ Равновесие", "✦ Причина", "✦ Следствие", "✦ Закон", "✦ Ответственность"), "card_11"))

        add(TarotCard(12, Arcana.MAJOR, "The Hanged Man", "Повешенный", "Le Pendu", "XII",
            "The Hanged Man has chosen his suspension — his face is serene, illuminated by a halo of insight. He has surrendered the need to act and found that in stillness a different kind of knowing becomes available. This is the pause before revelation, the willingness to sacrifice one way of seeing to perceive something truer.",
            "Повешенный выбрал своё подвешенное состояние — его лицо безмятежно, освещено ореолом прозрения. Он отказался от необходимости действовать и обнаружил, что в тишине становится доступным иной вид знания.",
            "L'Homme Pendu a choisi sa suspension — son visage est serein, illuminé par un halo de perspicacité. Il a renoncé au besoin d'agir et a trouvé que dans l'immobilité un type de connaissance différent devient disponible.",
            listOf("✦ Пауза", "✦ Жертва", "✦ Переворот", "✦ Прозрение", "✦ Отпускание", "✦ Перспектива"), "card_12"))

        add(TarotCard(13, Arcana.MAJOR, "Death", "Смерть", "La Mort", "XIII",
            "Death rides not as a destroyer but as a liberator — what has served its purpose must be released to make way for what is becoming. The rising sun promises renewal even as the old king falls. This card rarely speaks of physical death; it speaks of transformations that feel like dying but are actually the most profound births.",
            "Смерть едет не как разрушитель, а как освободитель — то, что отслужило своё, должно быть отпущено. Восходящее солнце обещает обновление. Эта карта редко говорит о физической смерти; она о трансформациях, которые ощущаются как умирание, но являются глубочайшими рождениями.",
            "La Mort chevauche non comme un destructeur mais comme un libérateur. Le soleil levant promet un renouveau. Cette carte parle rarement de mort physique; elle parle des transformations qui ressemblent à mourir mais sont les naissances les plus profondes.",
            listOf("✦ Конец", "✦ Трансформация", "✦ Переход", "✦ Обновление", "✦ Отпускание", "✦ Перерождение"), "card_13"))

        add(TarotCard(14, Arcana.MAJOR, "Temperance", "Умеренность", "La Tempérance", "XIV",
            "Temperance is the alchemy of the soul — the patient, precise mixing of opposites to create something neither could be alone. The angel pours water between cups without spilling a drop, demonstrating perfect flow and divine timing. This card asks for moderation not as deprivation but as the art of finding exactly the right measure.",
            "Умеренность — алхимия души: терпеливое, точное смешение противоположностей. Ангел переливает воду между кубками, не проливая ни капли, демонстрируя совершенный поток и божественный выбор времени.",
            "La Tempérance est l'alchimie de l'âme — le mélange patient et précis des opposés. L'ange verse de l'eau entre des coupes sans en renverser une goutte, démontrant un flux parfait et un timing divin.",
            listOf("✦ Баланс", "✦ Алхимия", "✦ Поток", "✦ Терпение", "✦ Умеренность", "✦ Гармония"), "card_14"))

        add(TarotCard(15, Arcana.MAJOR, "The Devil", "Дьявол", "Le Diable", "XV",
            "The Devil reveals the chains we wear willingly — addictions, obsessions and false beliefs that bind us to patterns we know no longer serve. Yet look closely: the chains are loose enough to slip off. The prison is mostly in the mind. This card illuminates the shadow self with unflinching honesty.",
            "Дьявол обнажает цепи, которые мы носим добровольно — зависимости, одержимости и ложные убеждения. Но посмотри внимательно: цепи достаточно свободны, чтобы соскользнуть. Тюрьма находится в основном в уме.",
            "Le Diable révèle les chaînes que nous portons volontairement — addictions, obsessions et fausses croyances. Pourtant les chaînes sont assez lâches pour glisser. La prison est surtout dans l'esprit.",
            listOf("✦ Тень", "✦ Привязанность", "✦ Иллюзия", "✦ Освобождение", "✦ Зависимость", "✦ Соблазн"), "card_15"))

        add(TarotCard(16, Arcana.MAJOR, "The Tower", "Башня", "La Tour", "XVI",
            "The Tower is lightning striking what was built on false foundations — sudden, total and ultimately necessary. The crown of ego is blown off; the carefully constructed persona cracks open. What feels like catastrophe is often the most efficient path to liberation. Something that cannot fall was never real to begin with.",
            "Башня — это молния, ударяющая в то, что построено на ложных основаниях: внезапно, тотально и необходимо. Корона эго сброшена; тщательно выстроенная персона трескается. То, что ощущается как катастрофа, часто является путём к освобождению.",
            "La Tour est la foudre frappant ce qui a été construit sur de fausses fondations — soudain, total et finalement nécessaire. La couronne de l'ego est soufflée. Ce qui ne peut pas tomber n'était jamais réel.",
            listOf("✦ Разрушение", "✦ Откровение", "✦ Пробуждение", "✦ Кризис", "✦ Молния", "✦ Освобождение"), "card_16"))

        add(TarotCard(17, Arcana.MAJOR, "The Star", "Звезда", "L'Étoile", "XVII",
            "After the Tower's devastation, the Star pours her healing waters on a world made new. She is naked and unashamed — pure vulnerability as strength. The eight-pointed star above her is bright and unwavering. Hope is not naive. Your wounds are not permanent. Renewal is already flowing.",
            "После разрушения Башни Звезда льёт целебные воды на обновлённый мир. Она обнажена и не стыдится — чистая уязвимость как сила. Надежда — не наивность. Твои раны не вечны. Обновление уже течёт.",
            "Après la dévastation de la Tour, l'Étoile verse ses eaux guérisseuses sur un monde renouvelé. Elle est nue et sans honte — pure vulnérabilité comme force. L'espoir n'est pas naïf. Le renouveau coule déjà.",
            listOf("✦ Надежда", "✦ Исцеление", "✦ Обновление", "✦ Вера", "✦ Уязвимость", "✦ Дар"), "card_17"))

        add(TarotCard(18, Arcana.MAJOR, "The Moon", "Луна", "La Lune", "XVIII",
            "The Moon illuminates a strange and shifting landscape where the winding path between two towers leads somewhere uncertain. This is the realm of dreams, illusions and the deep subconscious where fears take on monstrous shapes. Walk through this territory with awareness: not everything you see is real, and not everything threatening will harm you.",
            "Луна освещает странный пейзаж — извилистая тропа между двумя башнями ведёт в неопределённость. Это царство снов, иллюзий и глубокого подсознания, где страхи принимают чудовищные формы. Иди по этой территории осознанно: не всё, что видишь, реально.",
            "La Lune éclaire un paysage étrange et changeant. C'est le domaine des rêves, illusions et du subconscient profond où les peurs prennent des formes monstrueuses. Traversez ce territoire avec conscience.",
            listOf("✦ Иллюзия", "✦ Сон", "✦ Страх", "✦ Интуиция", "✦ Подсознание", "✦ Неопределённость"), "card_18"))

        add(TarotCard(19, Arcana.MAJOR, "The Sun", "Солнце", "Le Soleil", "XIX",
            "The Sun shines with the clarity and warmth that dissolves all shadows. The child rides joyfully on a white horse, arms open — innocent triumph, the full-bodied celebration of being alive. This card brings vitality, success and the simple delight of existing in a world that is fundamentally good. Let yourself be joyful.",
            "Солнце сияет ясностью и теплом, растворяющим все тени. Ребёнок радостно скачет на белом коне, раскинув руки — невинный триумф, торжество бытия живым. Эта карта несёт жизненность, успех и простую радость существования.",
            "Le Soleil brille avec la clarté et la chaleur qui dissolvent toutes les ombres. L'enfant chevauche joyeusement un cheval blanc, les bras ouverts — triomphe innocent, célébration d'être vivant.",
            listOf("✦ Радость", "✦ Ясность", "✦ Успех", "✦ Жизнь", "✦ Невинность", "✦ Торжество"), "card_19"))

        add(TarotCard(20, Arcana.MAJOR, "Judgement", "Суд", "Le Jugement", "XX",
            "Judgement sounds the angel's trumpet and the dead arise — not to be punished but to be called into their fullness. This is the moment of reckoning that leads to liberation: the chance to review your life with clear eyes and answer the call of your higher purpose. What has been sleeping in you is ready to rise.",
            "Суд звучит трубой ангела, и мёртвые поднимаются — не чтобы быть наказанными, а чтобы быть призванными к своей полноте. Это момент расчёта, ведущий к освобождению: возможность пересмотреть свою жизнь и ответить на призыв высшей цели.",
            "Le Jugement sonne la trompette de l'ange et les morts se lèvent — non pour être punis mais pour être appelés à leur plénitude. C'est le moment de bilan qui mène à la libération.",
            listOf("✦ Призыв", "✦ Пробуждение", "✦ Прощение", "✦ Воскрешение", "✦ Оценка", "✦ Призвание"), "card_20"))

        add(TarotCard(21, Arcana.MAJOR, "The World", "Мир", "Le Monde", "XXI",
            "The World dancer moves in perfect rhythm at the centre of an oval wreath — she has integrated all polarities and dances in the eternal present. This is completion at its highest: not the end of a journey but its full flowering, the moment when you inhabit your gifts so completely that you become them. The cycle is complete. You are whole.",
            "Танцовщица Мира движется в совершенном ритме в центре овального венка — она интегрировала все полярности и танцует в вечном настоящем. Это завершение на высшем уровне: не конец путешествия, а его полное расцветание.",
            "La danseuse du Monde se meut en rythme parfait au centre d'une couronne ovale — elle a intégré toutes les polarités et danse dans l'éternel présent. C'est l'accomplissement à son plus haut niveau.",
            listOf("✦ Целостность", "✦ Завершение", "✦ Интеграция", "✦ Танец", "✦ Полнота", "✦ Цикл"), "card_21"))

        add(TarotCard(22, Arcana.CUPS, "Ace of Cups", "Туз Кубков", "As de Coupes", "Ace",
            "The Ace of Cups is an overflowing vessel of emotional possibility — love, compassion, intuition and creative inspiration pouring forth from a divine source. It marks a new beginning in the emotional realm, an opening of the heart. Something beautiful wants to be felt. Let it in.",
            "Туз Кубков — переполненный сосуд эмоциональных возможностей: любовь, сострадание, интуиция. Он знаменует новое начало в эмоциональной сфере, открытие сердца. Что-то прекрасное хочет быть прочувствовано. Впусти это.",
            "L'As de Coupes est un vase débordant de possibilités émotionnelles — amour, compassion, intuition jaillissant d'une source divine. Il marque un nouveau début dans le domaine émotionnel.",
            listOf("✦ Любовь", "✦ Начало", "✦ Сосуд", "✦ Интуиция", "✦ Дар", "✦ Открытость"), "card_22"))

        add(TarotCard(23, Arcana.CUPS, "Two of Cups", "Двойка Кубков", "Deux de Coupes", "2",
            "Two people face each other holding their cups in recognition — here is the magic of true meeting, where two individuals see and are seen completely. This card blesses partnerships of all kinds: romantic, creative, therapeutic, spiritual. The caduceus between them speaks of alchemical union.",
            "Двое стоят напротив с кубками в знак признания — магия истинной встречи. Карта благословляет партнёрства всех видов: романтические, творческие, духовные. Кадуцей между ними говорит об алхимическом союзе.",
            "Deux personnes se font face tenant leurs coupes en reconnaissance — voici la magie de la vraie rencontre. Cette carte bénit les partenariats de toutes sortes. Le caducée parle d'union alchimique.",
            listOf("✦ Союз", "✦ Зеркало", "✦ Партнёрство", "✦ Встреча", "✦ Алхимия", "✦ Признание"), "card_23"))

        add(TarotCard(24, Arcana.CUPS, "Three of Cups", "Тройка Кубков", "Trois de Coupes", "3",
            "Three women dance in a circle, cups raised — this is the joy of belonging, of friendship that holds you, of community that celebrates your existence. Creativity flows most freely in good company. This card calls you toward collaboration, festivity, and the deep nourishment of shared delight.",
            "Три женщины танцуют в кругу, подняв кубки — это радость принадлежности, дружбы, которая держит тебя. Творчество течёт наиболее свободно в хорошей компании.",
            "Trois femmes dansent en cercle, coupes levées — c'est la joie d'appartenir, de l'amitié qui vous soutient. La créativité coule le plus librement en bonne compagnie.",
            listOf("✦ Праздник", "✦ Дружба", "✦ Сообщество", "✦ Творчество", "✦ Радость", "✦ Танец"), "card_24"))

        add(TarotCard(25, Arcana.CUPS, "Four of Cups", "Четвёрка Кубков", "Quatre de Coupes", "4",
            "A figure sits under a tree, arms crossed, staring at three cups — a fourth is offered by a mysterious hand from a cloud, yet he does not see it. This card speaks of discontent, of being so focused on what is missing that the gifts being offered go unnoticed. Apathy can be its own kind of blindness.",
            "Фигура сидит под деревом, глядя на три кубка перед ней — четвёртый предлагается из облака, но она его не замечает. Карта говорит о недовольстве, о такой сосредоточенности на отсутствующем, что предлагаемые дары остаются незамеченными.",
            "Une figure est assise sous un arbre fixant trois coupes — une quatrième est offerte depuis un nuage, mais elle ne la voit pas. Cette carte parle de mécontentement, d'être si concentré sur ce qui manque que les dons passent inaperçus.",
            listOf("✦ Апатия", "✦ Пауза", "✦ Созерцание", "✦ Слепота", "✦ Выбор", "✦ Недовольство"), "card_25"))

        add(TarotCard(26, Arcana.CUPS, "Five of Cups", "Пятёрка Кубков", "Cinq de Coupes", "5",
            "A cloaked figure stares at three spilled cups, lost in grief — yet behind him two cups remain upright, and a bridge leads home. Loss is real and grief must be honoured. But eventually the card asks: when will you turn around and see what still stands? What remains cannot replace what is gone, but it is still yours.",
            "Закутанная фигура смотрит на три перевёрнутых кубка, погружённая в горе — но позади два кубка стоят прямо и мост ведёт домой. Потеря реальна, и горе должно быть почтено. Но карта спрашивает: когда ты обернёшься?",
            "Une figure enveloppée fixe trois coupes renversées — pourtant derrière elle deux coupes restent droites. La perte est réelle et le deuil doit être honoré. Mais quand vous retournerez-vous pour voir ce qui reste?",
            listOf("✦ Потеря", "✦ Горе", "✦ Сожаление", "✦ Остаток", "✦ Принятие", "✦ Слёзы"), "card_26"))

        add(TarotCard(27, Arcana.CUPS, "Six of Cups", "Шестёрка Кубков", "Six de Coupes", "6",
            "Two children exchange a cup filled with white flowers in a golden courtyard — this is memory, nostalgia, and the sweetness of innocence revisited. The Six of Cups often signals a return to something from the past: a person, a place, a simpler way of being. The gifts received in childhood still nourish us.",
            "Двое детей обмениваются кубком с белыми цветами в золотом дворе — это память, ностальгия и сладость невинности. Шестёрка Кубков часто сигнализирует о возвращении к чему-то из прошлого.",
            "Deux enfants échangent une coupe remplie de fleurs blanches — c'est la mémoire, la nostalgie et la douceur de l'innocence revisitée. Le Six de Coupes signale souvent un retour à quelque chose du passé.",
            listOf("✦ Память", "✦ Детство", "✦ Тепло", "✦ Ностальгия", "✦ Дар", "✦ Возвращение"), "card_27"))

        add(TarotCard(28, Arcana.CUPS, "Seven of Cups", "Семёрка Кубков", "Sept de Coupes", "7",
            "Seven cups float in a dream cloud, each containing a different vision. The silhouette below is overwhelmed by choice and enchantment. This card warns of fantasy mistaken for reality, of scattering energy among too many desires to manifest any of them. Discernment is required.",
            "Семь кубков парят в облаке мечты, каждый содержит разное видение. Силуэт внизу подавлен выбором. Карта предупреждает о фантазии, принятой за реальность, о рассеивании энергии среди слишком многих желаний.",
            "Sept coupes flottent dans un nuage de rêve, chacune contenant une vision différente. La silhouette est submergée par le choix. Cette carte avertit de la fantaisie prise pour la réalité.",
            listOf("✦ Фантазия", "✦ Иллюзия", "✦ Мечта", "✦ Рассеянность", "✦ Туман", "✦ Выбор"), "card_28"))

        add(TarotCard(29, Arcana.CUPS, "Eight of Cups", "Восьмёрка Кубков", "Huit de Coupes", "8",
            "A figure walks away from eight neatly stacked cups into a dark landscape under a partial eclipse. Something has been fulfilled enough to leave — not in anger or failure, but in the quiet recognition that this chapter is complete. The Eight of Cups honours the courage it takes to walk away from what is comfortable but no longer meaningful.",
            "Фигура уходит от восьми аккуратно сложенных кубков. Что-то выполнено достаточно, чтобы уйти — не в гневе, а в тихом признании завершённости. Восьмёрка Кубков чтит мужество уйти от того, что комфортно, но не имеет смысла.",
            "Une figure s'éloigne de huit coupes soigneusement empilées dans un paysage sombre. Quelque chose a été suffisamment accompli pour partir. Le huit honore le courage qu'il faut pour s'éloigner de ce qui est confortable mais n'a plus de sens.",
            listOf("✦ Уход", "✦ Поиск", "✦ Завершение", "✦ Мужество", "✦ Движение", "✦ Смысл"), "card_29"))

        add(TarotCard(30, Arcana.CUPS, "Nine of Cups", "Девятка Кубков", "Neuf de Coupes", "9",
            "The wish card — a well-satisfied figure sits before an arc of nine cups, wearing the quiet smile of someone who has exactly what they wanted. Emotional contentment, sensory pleasure, gratitude and fulfilment are all present here. This card says: something you hoped for is coming to fruition.",
            "Карта желания — вполне довольная фигура сидит перед дугой из девяти кубков с тихой улыбкой. Эмоциональное довольство, чувственное удовольствие, благодарность — всё это присутствует здесь.",
            "La carte du voeu — une figure satisfaite est assise devant un arc de neuf coupes, arborant le sourire tranquille de quelqu'un qui a ce qu'il voulait. Contentement émotionnel, plaisir sensoriel, gratitude.",
            listOf("✦ Исполнение", "✦ Довольство", "✦ Желание", "✦ Удовольствие", "✦ Благодарность", "✦ Полнота"), "card_30"))

        add(TarotCard(31, Arcana.CUPS, "Ten of Cups", "Десятка Кубков", "Dix de Coupes", "10",
            "A couple stands with arms raised as a rainbow arc of cups appears above; their children dance in the foreground, and a beautiful home rests beyond. This is the fulfilment of the emotional life — deep love, family harmony, belonging and joy sustained over time. True happiness is relational.",
            "Пара стоит с поднятыми руками, над ними радуга из кубков; дети танцуют, красивый дом — в пейзаже. Это исполненность эмоциональной жизни — глубокая любовь, семейная гармония, принадлежность и радость.",
            "Un couple se tient les bras levés alors qu'un arc-en-ciel de coupes apparaît au-dessus. Leurs enfants dansent et une belle maison repose au-delà. C'est l'épanouissement de la vie émotionnelle.",
            listOf("✦ Семья", "✦ Гармония", "✦ Радуга", "✦ Дом", "✦ Любовь", "✦ Принадлежность"), "card_31"))

        add(TarotCard(32, Arcana.CUPS, "Page of Cups", "Паж Кубков", "Valet de Coupes", "Page",
            "The Page of Cups stands by the sea holding a cup from which a fish peers out, surprising even the Page himself. This is the messenger of emotional news, the dreamer who receives inspiration from unexpected places, the young soul open to wonder. A creative invitation or sweet surprise may be approaching.",
            "Паж Кубков стоит у моря, держа кубок, из которого выглядывает рыба, удивляя даже самого Пажа. Это вестник эмоциональных новостей, мечтатель, получающий вдохновение из неожиданных мест.",
            "Le Valet de Coupes se tient près de la mer tenant une coupe dont un poisson émerge, surprenant même le Valet. C'est le messager des nouvelles émotionnelles, le rêveur qui reçoit l'inspiration de sources inattendues.",
            listOf("✦ Весть", "✦ Мечта", "✦ Удивление", "✦ Творчество", "✦ Чудо", "✦ Открытость"), "card_32"))

        add(TarotCard(33, Arcana.CUPS, "Knight of Cups", "Рыцарь Кубков", "Chevalier de Coupes", "Knight",
            "The Knight of Cups rides a white horse at a gentle pace, carrying his cup forward with tender care — not charging, but approaching. He is the romantic hero, the idealist who follows his heart, the creative who pursues beauty and meaning. His gift is emotional courage: the willingness to offer what he feels.",
            "Рыцарь Кубков едет на белом коне неспешным шагом, неся кубок вперёд с нежной заботой. Он — романтический герой, идеалист, следующий за сердцем, творческий человек, преследующий красоту.",
            "Le Chevalier de Coupes monte un cheval blanc à un rythme doux, portant sa coupe en avant avec soin tendre — non pas en chargeant, mais en approchant. C'est le héros romantique, l'idéaliste qui suit son coeur.",
            listOf("✦ Романтика", "✦ Идеализм", "✦ Сердце", "✦ Красота", "✦ Поэзия", "✦ Смелость"), "card_33"))

        add(TarotCard(34, Arcana.CUPS, "Queen of Cups", "Королева Кубков", "Reine de Coupes", "Queen",
            "The Queen of Cups sits on a throne at the water's edge, gazing into an ornate cup as if listening to what it tells her. She is the master of emotional intelligence — deeply empathic, creatively gifted, and able to hold space for others' feelings without losing herself. Her gift is loving presence.",
            "Королева Кубков сидит на троне у кромки воды, вглядываясь в украшенный кубок. Она мастер эмоционального интеллекта: глубоко эмпатична, творчески одарена, способна держать пространство для чувств других.",
            "La Reine de Coupes est assise sur un trône au bord de l'eau, regardant dans une coupe ornée comme si elle écoutait ce qu'elle lui dit. Elle est la maîtresse de l'intelligence émotionnelle.",
            listOf("✦ Эмпатия", "✦ Присутствие", "✦ Глубина", "✦ Интуиция", "✦ Забота", "✦ Мудрость"), "card_34"))

        add(TarotCard(35, Arcana.CUPS, "King of Cups", "Король Кубков", "Roi de Coupes", "King",
            "The King of Cups sits on his throne amid turbulent seas, calm as the eye of the storm — he has mastered the art of feeling deeply without being swept away. He embodies emotional maturity, compassionate leadership and the wisdom to navigate both the inner world and the outer with equal grace.",
            "Король Кубков сидит на троне среди бурных морей, спокойный как центр шторма. Он овладел искусством глубокого чувствования, не будучи сметённым. Воплощает эмоциональную зрелость, сострадательное лидерство.",
            "Le Roi de Coupes est assis sur son trône au milieu de mers tumultueuses, calme comme l'oeil de la tempête. Il a maîtrisé l'art de ressentir profondément sans être emporté.",
            listOf("✦ Зрелость", "✦ Спокойствие", "✦ Дипломатия", "✦ Мудрость", "✦ Щедрость", "✦ Баланс"), "card_35"))

        add(TarotCard(36, Arcana.WANDS, "Ace of Wands", "Туз Жезлов", "As de Bâtons", "Ace",
            "The Ace of Wands is the raw spark of creative potential bursting forth — inspiration, new fire and pure possibility. A new project or passion is igniting and the universe hands you a torch. Act while the fire is hot. What will you light?",
            "Туз Жезлов — искра творческого потенциала: вдохновение, новый огонь и чистая возможность. Новый проект или страсть разжигается — вселенная вручает тебе факел. Действуй, пока огонь горячий. Что ты зажжёшь?",
            "L'As de Bâtons est l'étincelle brute du potentiel créatif — inspiration, nouveau feu et pure possibilité. Un nouveau projet s'enflamme et l'univers vous remet une torche. Qu'allez-vous allumer?",
            listOf("✦ Искра", "✦ Вдохновение", "✦ Потенциал", "✦ Начало", "✦ Огонь", "✦ Творчество"), "card_36"))

        add(TarotCard(37, Arcana.WANDS, "Two of Wands", "Двойка Жезлов", "Deux de Bâtons", "2",
            "You stand at the edge of your known world holding a globe, surveying possibilities on the horizon. This is foresight: you have achieved something and now you plan your next expansion. The world is literally in your hands. Where will you direct your fire?",
            "Ты стоишь на краю известного мира, держа глобус, обозревая горизонт возможностей. Это предвидение: ты чего-то достиг и теперь планируешь следующее расширение. Мир буквально в твоих руках.",
            "Vous vous tenez au bord de votre monde connu tenant un globe, sondant les possibilités à l'horizon. C'est la prévoyance: vous avez accompli quelque chose et maintenant vous planifiez votre prochaine expansion.",
            listOf("✦ Горизонт", "✦ Планирование", "✦ Предвидение", "✦ Амбиция", "✦ Выбор", "✦ Путь"), "card_37"))

        add(TarotCard(38, Arcana.WANDS, "Three of Wands", "Тройка Жезлов", "Trois de Bâtons", "3",
            "From a clifftop you watch your ships return — the first stage of your vision has launched and is moving in the world. Trust the plans you have already set in motion. Now is the time to keep watch and hold steady as your efforts bear fruit in distant places.",
            "С вершины скалы ты наблюдаешь, как возвращаются твои корабли. Первый этап твоего видения запущен и движется. Доверяй планам, которые ты уже привёл в движение. Держись стойко, пока твои усилия приносят плоды.",
            "D'une falaise vous regardez vos navires revenir. La première étape de votre vision a été lancée. Faites confiance aux plans que vous avez déjà mis en mouvement. Tenez bon pendant que vos efforts portent leurs fruits.",
            listOf("✦ Корабли", "✦ Ожидание", "✦ Прогресс", "✦ Видение", "✦ Доверие", "✦ Наблюдение"), "card_38"))

        add(TarotCard(39, Arcana.WANDS, "Four of Wands", "Четвёрка Жезлов", "Quatre de Bâtons", "4",
            "Celebration after effort — the four wands form a flower-garlanded canopy under which people dance. This is the joy of homecoming, the harvest festival of work well done. Community, stability and the pleasure of arriving somewhere safe and beloved. Rest and receive the fruits.",
            "Праздник после усилий — четыре жезла образуют увитый гирляндами навес, под которым танцуют люди. Это радость возвращения домой, праздник хорошо сделанной работы. Сообщество, стабильность и удовольствие прийти куда-то безопасному.",
            "Célébration après l'effort — les quatre bâtons forment un dais garni de guirlandes sous lequel les gens dansent. C'est la joie du retour à la maison, la fête des moissons du travail bien fait.",
            listOf("✦ Праздник", "✦ Дом", "✦ Урожай", "✦ Гармония", "✦ Отдых", "✦ Радость"), "card_39"))

        add(TarotCard(40, Arcana.WANDS, "Five of Wands", "Пятёрка Жезлов", "Cinq de Bâtons", "5",
            "Five figures struggle with their staffs in a confused tangle — competition, debate and creative friction are all here. This struggle is not meaningless; it forges clarity and strength. Stand your ground, but remember to also listen. The chaos has a purpose if you stay engaged with it.",
            "Пять фигур борются со своими посохами в запутанном клубке — конкуренция, дебаты и творческое трение. Эта борьба не бессмысленна — она куёт ясность. Стой на своём, но и слушай. Хаос имеет цель, если ты остаёшься вовлечённым.",
            "Cinq figures luttent avec leurs bâtons dans un enchevêtrement confus — compétition, débat et friction créative. Cette lutte n'est pas sans signification; elle forge la clarté. Tenez bon, mais écoutez aussi.",
            listOf("✦ Борьба", "✦ Конкуренция", "✦ Хаос", "✦ Ясность", "✦ Сила", "✦ Напряжение"), "card_40"))

        add(TarotCard(41, Arcana.WANDS, "Six of Wands", "Шестёрка Жезлов", "Six de Bâtons", "6",
            "The victor rides through a crowd with a laurel wreath, his wand held high. Recognition has arrived — the work has been seen and honoured. Accept this moment of triumph without deflecting it. You have earned this, and allowing yourself to receive acknowledgment models something important for others.",
            "Победитель едет через толпу с лавровым венком, жезл поднят. Признание пришло — работа замечена и почтена. Прими этот момент триумфа, не отклоняя его. Ты заслужил это, и позволить себе получить признание важно.",
            "Le vainqueur défile dans une foule avec une couronne de laurier, son bâton levé. La reconnaissance est arrivée — le travail a été vu et honoré. Acceptez ce moment de triomphe.",
            listOf("✦ Победа", "✦ Признание", "✦ Успех", "✦ Слава", "✦ Лидерство", "✦ Триумф"), "card_41"))

        add(TarotCard(42, Arcana.WANDS, "Seven of Wands", "Семёрка Жезлов", "Sept de Bâtons", "7",
            "A figure stands on high ground, fending off six wands from below. You are defending what you have built, and the challenge is real — but you have the advantage of position and conviction. This is about maintaining your stance under pressure. Do not abandon the high ground.",
            "Фигура стоит на высоте, отбиваясь от шести жезлов снизу. Ты защищаешь то, что построил, и испытание реально — но у тебя есть преимущество позиции. Это о сохранении своей позиции под давлением. Не покидай высоту.",
            "Une figure se tient en hauteur, repoussant six bâtons d'en bas. Vous défendez ce que vous avez construit. Vous avez l'avantage de la position et de la conviction. Ne renoncez pas au terrain élevé.",
            listOf("✦ Защита", "✦ Стойкость", "✦ Позиция", "✦ Настойчивость", "✦ Территория", "✦ Вызов"), "card_42"))

        add(TarotCard(43, Arcana.WANDS, "Eight of Wands", "Восьмёрка Жезлов", "Huit de Bâtons", "8",
            "Eight wands streak through the air toward their destination — this is momentum, swift communication and rapid developments all converging at once. Things are moving fast. Stay agile, communicate clearly and trust the acceleration. This is not the time to hesitate.",
            "Восемь жезлов мчатся по воздуху к цели — это импульс, быстрое общение и стремительное развитие, всё сходящееся сразу. Вещи движутся быстро. Оставайся гибким и доверяй ускорению.",
            "Huit bâtons filent dans l'air vers leur destination — c'est la dynamique, la communication rapide et des développements rapides convergeant à la fois. Les choses bougent vite. Restez agile.",
            listOf("✦ Скорость", "✦ Импульс", "✦ Связь", "✦ Движение", "✦ Быстрота", "✦ Поток"), "card_43"))

        add(TarotCard(44, Arcana.WANDS, "Nine of Wands", "Девятка Жезлов", "Neuf de Bâtons", "9",
            "A battered figure holds his wand and faces whatever comes next — he has survived much, and his weariness shows, but he has not fallen. This is resilience: not the absence of wounds but the refusal to stop. One more push remains. You have the strength for it.",
            "Потрёпанная фигура держит жезл и смотрит в лицо тому, что будет дальше. Он пережил многое, и усталость показана, но он не упал. Это стойкость: не отсутствие ран, а отказ останавливаться. Остался ещё один рывок.",
            "Une figure battue tient son bâton et fait face à ce qui vient. Elle a survécu à beaucoup. C'est la résilience: non l'absence de blessures mais le refus de s'arrêter. Un effort de plus reste à faire.",
            listOf("✦ Стойкость", "✦ Выносливость", "✦ Рубеж", "✦ Усталость", "✦ Мужество", "✦ Продолжение"), "card_44"))

        add(TarotCard(45, Arcana.WANDS, "Ten of Wands", "Десятка Жезлов", "Dix de Bâtons", "10",
            "A figure staggers under a crushing bundle of ten wands, barely able to see where he is going. You are carrying too much — too many responsibilities, too many obligations taken on alone. This card asks: what can be delegated, shared or set down? The destination is close. But you must lighten the load.",
            "Фигура шатается под гнётом десяти жезлов. Ты несёшь слишком много — слишком много обязательств, взятых в одиночку. Карта спрашивает: что можно делегировать или положить? Цель близка. Но ты должен облегчить груз.",
            "Une figure chancelle sous un faisceau écrasant de dix bâtons. Vous portez trop. Cette carte demande: que peut-on déléguer, partager ou poser? La destination est proche. Mais vous devez alléger le fardeau.",
            listOf("✦ Бремя", "✦ Ответственность", "✦ Груз", "✦ Помощь", "✦ Ноша", "✦ Предел"), "card_45"))

        add(TarotCard(46, Arcana.WANDS, "Page of Wands", "Паж Жезлов", "Valet de Bâtons", "Page",
            "The Page of Wands is a free spirit burning with curiosity and enthusiasm, always ready to explore the next idea or territory. He carries his wand like a torch, scouting ahead. He is the first flame of inspiration before it knows what it will become. A message of exciting news or a new creative spark is arriving.",
            "Паж Жезлов — свободный дух, горящий любопытством и энтузиазмом, всегда готовый исследовать следующую идею. Он несёт жезл как факел, разведывая путь. Он — первое пламя вдохновения.",
            "Le Valet de Bâtons est un esprit libre brûlant de curiosité et d'enthousiasme, toujours prêt à explorer la prochaine idée. Il porte son bâton comme une torche, scouting en avant.",
            listOf("✦ Энтузиазм", "✦ Весть", "✦ Идея", "✦ Исследование", "✦ Огонь", "✦ Начало"), "card_46"))

        add(TarotCard(47, Arcana.WANDS, "Knight of Wands", "Рыцарь Жезлов", "Chevalier de Bâtons", "Knight",
            "The Knight of Wands charges forward on his rearing horse, impulsive and magnificent, chasing the horizon with reckless passion. He gets things moving when they are stuck. The danger is that he may move too fast for the goal; the gift is that he moves at all. Act boldly but don't forget the destination.",
            "Рыцарь Жезлов бросается вперёд на вздыбленном коне, импульсивный и великолепный, преследуя горизонт с безрассудной страстью. Он приводит вещи в движение. Действуй смело, но не забывай о цели.",
            "Le Chevalier de Bâtons charge en avant sur son cheval cabré, impulsif et magnifique, chassant l'horizon avec une passion téméraire. Il fait avancer les choses. Agissez audacieusement mais n'oubliez pas la destination.",
            listOf("✦ Приключение", "✦ Страсть", "✦ Импульс", "✦ Действие", "✦ Скорость", "✦ Риск"), "card_47"))

        add(TarotCard(48, Arcana.WANDS, "Queen of Wands", "Королева Жезлов", "Reine de Bâtons", "Queen",
            "The Queen of Wands radiates magnetic warmth and creative authority — sunflowers turn toward her naturally, and the black cat at her feet speaks of both independence and power. She is the embodiment of passion in service of purpose: fierce, generous and utterly herself. Her presence ignites those around her.",
            "Королева Жезлов излучает магнетическое тепло и творческий авторитет — подсолнухи тянутся к ней, чёрный кот у ног говорит о независимости и силе. Она воплощает страсть на службе цели: яростная, щедрая и полностью сама себя.",
            "La Reine de Bâtons rayonne d'une chaleur magnétique et d'une autorité créative. Elle est l'incarnation de la passion au service du but: féroce, généreuse et tout à fait elle-même.",
            listOf("✦ Лидерство", "✦ Тепло", "✦ Уверенность", "✦ Харизма", "✦ Сила", "✦ Творчество"), "card_48"))

        add(TarotCard(49, Arcana.WANDS, "King of Wands", "Король Жезлов", "Roi de Bâtons", "King",
            "The King of Wands is the visionary leader, the entrepreneur of the soul — he has taken the raw fire of the suit and shaped it into something that can move others. He leads by inspiration rather than command, and his flame does not diminish what surrounds it but ignites it. He knows where he is going and others follow.",
            "Король Жезлов — провидческий лидер. Он взял сырой огонь своей масти и превратил его во что-то, способное двигать других. Он ведёт через вдохновение, а не команду. Его пламя не уменьшает то, что окружает его, а поджигает его.",
            "Le Roi de Bâtons est le leader visionnaire. Il a pris le feu brut de la suite et l'a façonné en quelque chose qui peut mouvoir les autres. Il dirige par l'inspiration plutôt que par le commandement.",
            listOf("✦ Видение", "✦ Авторитет", "✦ Воля", "✦ Мудрость", "✦ Вдохновение", "✦ Сила"), "card_49"))

        add(TarotCard(50, Arcana.SWORDS, "Ace of Swords", "Туз Мечей", "As d'Épées", "Ace",
            "The Ace of Swords cuts through confusion with absolute clarity — this is the breakthrough moment when truth pierces illusion and the way forward becomes unmistakable. New mental clarity, a decisive idea or a moment of justice arrives. The sword can wound, but in this moment it primarily liberates.",
            "Туз Мечей прорезает замешательство абсолютной ясностью — это прорывной момент, когда истина пронзает иллюзию. Новая умственная ясность, решающая идея или момент справедливости. Меч может ранить, но в этот момент он прежде всего освобождает.",
            "L'As d'Épées tranche la confusion avec une clarté absolue. C'est le moment de percée quand la vérité perce l'illusion. Une nouvelle clarté mentale, une idée décisive ou un moment de justice arrive.",
            listOf("✦ Ясность", "✦ Прорыв", "✦ Истина", "✦ Разум", "✦ Решение", "✦ Освобождение"), "card_50"))

        add(TarotCard(51, Arcana.SWORDS, "Two of Swords", "Двойка Мечей", "Deux d'Épées", "2",
            "Two blindfolded figures sit back to back with crossed swords in a tense standoff. Neither can see; neither will move first. This card speaks of the paralysis that comes from refusing to choose, from protecting yourself so thoroughly that you cannot receive what you need. A decision is required.",
            "Две завязанные фигуры сидят спиной к спине со скрещенными мечами в напряжённом противостоянии. Ни одна не видит; ни одна не двинется первой. Карта говорит о параличе от отказа выбирать. Необходимо принять решение.",
            "Deux figures aux yeux bandés sont assises dos à dos avec des épées croisées dans une impasse tendue. Aucune ne peut voir; aucune ne bougera en premier. Cette carte parle de la paralysie qui vient du refus de choisir.",
            listOf("✦ Тупик", "✦ Выбор", "✦ Слепота", "✦ Блок", "✦ Страх", "✦ Нерешительность"), "card_51"))

        add(TarotCard(52, Arcana.SWORDS, "Three of Swords", "Тройка Мечей", "Trois d'Épées", "3",
            "Three swords pierce a heart against a stormy sky. This is the most honest grief card in the deck — heartbreak, betrayal, separation, sorrow. The storm will pass. The heart will not be permanently destroyed by what it feels. But first, the pain must be fully acknowledged and not bypassed.",
            "Три меча пронзают сердце на фоне грозового неба. Это самая честная карта горя в колоде — разбитое сердце, предательство, разлука. Буря пройдёт. Сердце не будет навсегда уничтожено. Но сначала боль должна быть полностью признана.",
            "Trois épées transpercent un coeur sous un ciel orageux. C'est la carte du deuil la plus honnête du jeu — peine de coeur, trahison, séparation. La tempête passera. Mais d'abord, la douleur doit être pleinement reconnue.",
            listOf("✦ Горе", "✦ Предательство", "✦ Боль", "✦ Разлука", "✦ Буря", "✦ Честность"), "card_52"))

        add(TarotCard(53, Arcana.SWORDS, "Four of Swords", "Четвёрка Мечей", "Quatre d'Épées", "4",
            "A figure lies in meditation on a tomb while swords hang on the wall and one lies beneath him. This is the rest after battle — necessary recovery, retreat and the healing that comes from deliberate stillness. The mind needs silence to process what the body has endured. Do not skip the convalescence.",
            "Фигура лежит в медитации на гробнице. Это отдых после битвы — необходимое восстановление и исцеление от намеренной тишины. Уму нужна тишина, чтобы осмыслить пережитое. Не пропускай выздоровление.",
            "Une figure gît en méditation sur un tombeau. C'est le repos après la bataille — récupération nécessaire et guérison venue de l'immobilité délibérée. L'esprit a besoin de silence pour traiter ce que le corps a enduré.",
            listOf("✦ Отдых", "✦ Восстановление", "✦ Пауза", "✦ Тишина", "✦ Исцеление", "✦ Созерцание"), "card_53"))

        add(TarotCard(54, Arcana.SWORDS, "Five of Swords", "Пятёрка Мечей", "Cinq d'Épées", "5",
            "A figure collects swords from others who walk away in defeat. Victory here is hollow — won through unfair means or at too great a cost to others. This card asks you to examine what you have taken and what it cost. Sometimes winning requires more than merely defeating the opposition.",
            "Фигура собирает мечи у других, уходящих в поражении. Победа здесь пуста — добыта нечестными средствами. Карта просит изучить, что ты взял и чего это стоило. Иногда победа требует большего, чем просто поражение оппонентов.",
            "Une figure ramasse des épées à d'autres qui s'éloignent dans la défaite. La victoire ici est creuse — gagnée par des moyens déloyaux. Cette carte vous demande d'examiner ce que vous avez pris et ce que cela a coûté.",
            listOf("✦ Поражение", "✦ Конфликт", "✦ Потеря", "✦ Цена", "✦ Бесчестие", "✦ Пустая победа"), "card_54"))

        add(TarotCard(55, Arcana.SWORDS, "Six of Swords", "Шестёрка Мечей", "Six d'Épées", "6",
            "A ferryman guides a boat with a woman and child across still waters, away from something difficult. The six swords stand upright in the bow — the trouble is still there, but you are moving away from it. This card speaks of passage, transition and the beginning of calmer waters ahead.",
            "Перевозчик ведёт лодку с женщиной и ребёнком через спокойные воды прочь от чего-то трудного. Шесть мечей стоят в носу — трудность всё ещё там, но ты движешься от неё. Карта говорит о переходе к более спокойным водам.",
            "Un passeur guide un bateau avec une femme et un enfant à travers des eaux calmes, s'éloignant de quelque chose de difficile. Les six épées restent dans la proue. Cette carte parle de passage et du début d'eaux plus calmes.",
            listOf("✦ Переход", "✦ Движение", "✦ Уход", "✦ Лодка", "✦ Туман", "✦ Надежда"), "card_55"))

        add(TarotCard(56, Arcana.SWORDS, "Seven of Swords", "Семёрка Мечей", "Sept d'Épées", "7",
            "A figure walks away carrying five swords while two others are thrust in the earth behind him. The theft here may be of ideas, credit or dignity. Or it may be the necessary cunning required to survive in an unjust situation. Which applies to your situation right now? Examine honestly.",
            "Фигура уходит, унося пять мечей, пока двое других остаются позади. Кража может быть идей, заслуг или достоинства. Или это необходимая хитрость для выживания в несправедливой ситуации. Что применимо к твоей ситуации?",
            "Une figure s'éloigne en portant cinq épées tandis que deux autres sont enfoncées dans la terre derrière. Le vol peut être d'idées, de crédit ou de dignité. Ou la ruse nécessaire pour survivre dans une situation injuste.",
            listOf("✦ Хитрость", "✦ Обман", "✦ Тень", "✦ Выживание", "✦ Кража", "✦ Осторожность"), "card_56"))

        add(TarotCard(57, Arcana.SWORDS, "Eight of Swords", "Восьмёрка Мечей", "Huit d'Épées", "8",
            "A bound figure stands surrounded by eight swords stuck in the earth. She is not truly trapped — the ropes are loose and she could walk away if she tried. The Eight of Swords speaks of a prison built from belief: the story you tell yourself about what is possible. What would change if you questioned that story?",
            "Связанная фигура стоит среди восьми мечей. Она не по-настоящему в ловушке — верёвки слабые. Восьмёрка говорит о тюрьме из убеждений: историей, которую ты рассказываешь себе о том, что возможно. Что изменится, если ты поставишь под сомнение эту историю?",
            "Une figure liée se tient entourée de huit épées. Elle n'est pas vraiment piégée — les cordes sont lâches. Le huit d'épées parle d'une prison construite à partir de croyances: l'histoire que vous vous racontez sur ce qui est possible.",
            listOf("✦ Клетка", "✦ Убеждение", "✦ Ограничение", "✦ Слепота", "✦ Страх", "✦ Путы"), "card_57"))

        add(TarotCard(58, Arcana.SWORDS, "Nine of Swords", "Девятка Мечей", "Neuf d'Épées", "9",
            "A figure sits upright in bed, hands covering her face, in the dark of night. Nine swords hang on the wall above. This is the 3am spiral — anxiety, dread, sleeplessness and the thoughts that seem larger and more final in the dark. They are not as final as they feel. Dawn will come.",
            "Фигура сидит в постели, закрыв лицо руками, в темноте ночи. Девять мечей на стене выше. Это спираль в 3 часа ночи — тревога и мысли, которые кажутся больше и окончательнее в темноте. Они не так окончательны, как ощущается. Рассвет придёт.",
            "Une figure est assise dans son lit, les mains couvrant son visage, dans le noir de la nuit. Neuf épées sont accrochées au mur. C'est la spirale de 3h du matin. Les pensées semblent plus grandes et plus finales dans le noir. L'aube viendra.",
            listOf("✦ Тревога", "✦ Ночь", "✦ Кошмар", "✦ Страх", "✦ Мысли", "✦ Беспокойство"), "card_58"))

        add(TarotCard(59, Arcana.SWORDS, "Ten of Swords", "Десятка Мечей", "Dix d'Épées", "10",
            "Ten swords pierce a fallen figure's back — a brutal ending, a betrayal, a collapse. But the dawn sky behind is beginning to lighten. This is rock bottom: the moment when something is so thoroughly over that renewal must now begin. The only way out is through. The worst has already happened.",
            "Десять мечей пронзают упавшую фигуру — жестокий конец, предательство, крах. Но рассветное небо начинает светлеть. Это самое дно: момент, когда что-то настолько исчерпано, что обновление должно начаться. Худшее уже произошло.",
            "Dix épées transpercent une figure tombée — une fin brutale, une trahison, un effondrement. Mais le ciel de l'aube commence à s'éclaircir. C'est le fond du gouffre: le renouveau doit maintenant commencer.",
            listOf("✦ Конец", "✦ Предательство", "✦ Крах", "✦ Рассвет", "✦ Рубеж", "✦ Начало"), "card_59"))

        add(TarotCard(60, Arcana.SWORDS, "Page of Swords", "Паж Мечей", "Valet d'Épées", "Page",
            "The Page of Swords holds his sword aloft, wind whipping around him, alert to everything. He is the eternal questioner, the one who notices what others miss, who speaks truth before tact. Brilliant but sometimes cutting before considering. His energy asks: what question haven't you asked yet?",
            "Паж Мечей держит меч высоко, ветер кружится вокруг него, бдительный ко всему. Он вечный вопрошатель, тот, кто замечает то, что другие упускают. Блестящий, но иногда режет прежде, чем думает. Какой вопрос ты ещё не задал?",
            "Le Valet d'Épées tient son épée haut, le vent fouettant autour de lui, alerte à tout. Il est l'éternel questionneur, celui qui remarque ce que les autres manquent. Quel est la question que vous n'avez pas encore posée?",
            listOf("✦ Наблюдение", "✦ Слово", "✦ Мысль", "✦ Честность", "✦ Острота", "✦ Любопытство"), "card_60"))

        add(TarotCard(61, Arcana.SWORDS, "Knight of Swords", "Рыцарь Мечей", "Chevalier d'Épées", "Knight",
            "The Knight of Swords charges into battle with no hesitation — sword raised, horse at full gallop, hair streaming. He is fast, decisive and sometimes reckless. When something needs to happen right now, he is the energy required. But speed without direction causes chaos.",
            "Рыцарь Мечей бросается в бой без колебаний — меч поднят, конь в полном галопе. Он быстрый, решительный и иногда безрассудный. Когда что-то должно произойти прямо сейчас, он — необходимая энергия. Но скорость без направления создаёт хаос.",
            "Le Chevalier d'Épées charge au combat sans hésitation — épée levée, cheval au galop. Il est rapide, décisif et parfois imprudent. Quand quelque chose doit se passer maintenant, il est l'énergie requise.",
            listOf("✦ Скорость", "✦ Натиск", "✦ Решимость", "✦ Прямота", "✦ Буря", "✦ Сила"), "card_61"))

        add(TarotCard(62, Arcana.SWORDS, "Queen of Swords", "Королева Мечей", "Reine d'Épées", "Queen",
            "The Queen of Swords has known loss and has refined herself in it. She sits upright, sword raised, one hand extended in a gesture of judgment tempered by compassion. She sees clearly and does not flinch from truth, but her insight carries the wisdom of lived experience.",
            "Королева Мечей познала потерю и отточила себя в ней. Она сидит прямо, меч поднят. Она видит ясно и не уклоняется от истины, но её прозрение несёт мудрость прожитого опыта. Резкая, но справедливая.",
            "La Reine d'Épées a connu la perte et s'est affinée dedans. Elle voit clairement et n'évite pas la vérité, mais sa perspicacité porte la sagesse de l'expérience vécue.",
            listOf("✦ Ясность", "✦ Аналитика", "✦ Независимость", "✦ Честность", "✦ Правда", "✦ Проницательность"), "card_62"))

        add(TarotCard(63, Arcana.SWORDS, "King of Swords", "Король Мечей", "Roi d'Épées", "King",
            "The King of Swords commands with intellectual precision — seated on his throne, sword raised, he embodies the rule of clear thinking and principled decision-making. He can seem cold but he is fair. He operates in the realm of law, ethics and truth. His authority comes from the quality of his reasoning.",
            "Король Мечей командует с интеллектуальной точностью — seated на троне, меч поднят. Он может казаться холодным, но он справедлив. Он действует в сфере закона, этики и истины. Его авторитет исходит из качества его рассуждений.",
            "Le Roi d'Épées commande avec précision intellectuelle. Il peut sembler froid mais il est juste. Il opère dans le domaine de la loi, de l'éthique et de la vérité. Son autorité vient de la qualité de son raisonnement.",
            listOf("✦ Интеллект", "✦ Закон", "✦ Авторитет", "✦ Прямота", "✦ Разум", "✦ Власть"), "card_63"))

        add(TarotCard(64, Arcana.PENTACLES, "Ace of Pentacles", "Туз Пентаклей", "As de Pentacles", "Ace",
            "The Ace of Pentacles is the seed of material potential — a coin held in a divine hand, offered above a blooming garden. This is the beginning of something real: a new financial opportunity, a physical project, a chance to build something lasting. The garden is already there; you only need to plant.",
            "Туз Пентаклей — семя материального потенциала: монета, предлагаемая над цветущим садом. Это начало чего-то реального: новая финансовая возможность, физический проект, шанс построить что-то прочное. Сад уже там; тебе нужно только посадить.",
            "L'As de Pentacles est la graine du potentiel matériel — une pièce offerte au-dessus d'un jardin en fleurs. C'est le début de quelque chose de réel: une nouvelle opportunité financière, un projet physique, une chance de construire quelque chose de durable.",
            listOf("✦ Семя", "✦ Возможность", "✦ Земля", "✦ Материальность", "✦ Начало", "✦ Дар"), "card_64"))

        add(TarotCard(65, Arcana.PENTACLES, "Two of Pentacles", "Двойка Пентаклей", "Deux de Pentacles", "2",
            "A juggler balances two pentacles in a figure-eight loop, dancing at the edge of the sea. Life requires this constant adjustment — the ability to keep multiple things moving without losing your footing. Flexibility here is a skill, not a weakness. Which priorities need rebalancing?",
            "Жонглёр балансирует двумя пентаклями в петле восьмёрки, танцуя у края моря. Жизнь требует такой постоянной корректировки — способности держать несколько вещей в движении. Гибкость — это навык, а не слабость.",
            "Un jongleur équilibre deux pentacles dans une boucle en huit, dansant au bord de la mer. La vie exige cet ajustement constant. La flexibilité est une compétence, pas une faiblesse. Quelles priorités ont besoin d'être rééquilibrées?",
            listOf("✦ Баланс", "✦ Адаптация", "✦ Жонглёр", "✦ Гибкость", "✦ Движение", "✦ Приоритеты"), "card_65"))

        add(TarotCard(66, Arcana.PENTACLES, "Three of Pentacles", "Тройка Пентаклей", "Trois de Pentacles", "3",
            "An architect presents his plan to two monks — this is the card of skilled collaboration, of work that requires the combined expertise of more than one mind. Masterful results come from showing your work, accepting feedback, and building with others rather than alone.",
            "Архитектор представляет свой план двум монахам — это карта умелого сотрудничества, работы, требующей совместной экспертизы. Мастерские результаты приходят от показа своей работы и строительства вместе с другими, а не в одиночку.",
            "Un architecte présente son plan à deux moines — c'est la carte de la collaboration habile, du travail qui nécessite l'expertise combinée de plus d'un esprit. Les résultats magistraux viennent de montrer son travail et de construire avec les autres.",
            listOf("✦ Мастерство", "✦ Сотрудничество", "✦ План", "✦ Компетентность", "✦ Строительство", "✦ Обратная связь"), "card_66"))

        add(TarotCard(67, Arcana.PENTACLES, "Four of Pentacles", "Четвёрка Пентаклей", "Quatre de Pentacles", "4",
            "A figure clutches four pentacles tightly — one on his head, two under his feet, one hugged to his chest. Security and control feel paramount here, but the city behind him is empty of people. What is the cost of holding on so tightly? Security that isolates is a cage.",
            "Фигура крепко сжимает четыре пентакля — один на голове, два под ногами, один прижат к груди. Безопасность и контроль кажутся первостепенными, но город позади пуст от людей. Какова цена такого жёсткого удержания? Безопасность, которая изолирует, — это клетка.",
            "Une figure serre quatre pentacles fort. La sécurité et le contrôle semblent primordiaux ici, mais la ville derrière est vide de personnes. Quel est le coût d'une telle emprise? La sécurité qui isole est une cage.",
            listOf("✦ Безопасность", "✦ Удержание", "✦ Контроль", "✦ Страх", "✦ Бережность", "✦ Изоляция"), "card_67"))

        add(TarotCard(68, Arcana.PENTACLES, "Five of Pentacles", "Пятёрка Пентаклей", "Cinq de Pentacles", "5",
            "Two ragged figures pass a church in the snow, excluded from its warmth. Poverty, lack, exclusion and the fear of not having enough. And yet the stained glass above bears pentacles — abundance exists somewhere nearby. What support is available that you haven't sought? Asking for help is not weakness.",
            "Две оборванные фигуры проходят мимо церкви в снегу, исключённые из её тепла. Бедность, нужда, исключение. Но витражи выше несут пентакли — изобилие существует рядом. Какая поддержка доступна, которую ты ещё не искал?",
            "Deux figures en haillons passent devant une église dans la neige, exclues de sa chaleur. Pauvreté, manque, exclusion. Pourtant les vitraux portent des pentacles — l'abondance existe à proximité. Quelle aide n'avez-vous pas encore cherchée?",
            listOf("✦ Нужда", "✦ Трудности", "✦ Исключение", "✦ Помощь", "✦ Поиск", "✦ Надежда"), "card_68"))

        add(TarotCard(69, Arcana.PENTACLES, "Six of Pentacles", "Шестёрка Пентаклей", "Six de Pentacles", "6",
            "A merchant weighs out coins to two supplicants below him — one hand holds scales, the other gives. Generosity, charitable giving and the flow of resources are all here. But the question of who has the power in this exchange is also present. True generosity flows without conditions.",
            "Торговец взвешивает монеты для двух просителей — одной рукой весы, другой даёт. Щедрость, благотворительность и поток ресурсов. Но вопрос о том, кто имеет власть в этом обмене, тоже присутствует. Истинная щедрость течёт без условий.",
            "Un marchand pèse des pièces pour deux suppliants. Générosité, charité et flux de ressources. Mais la question de qui a le pouvoir dans cet échange est également présente. La vraie générosité coule sans conditions.",
            listOf("✦ Щедрость", "✦ Даяние", "✦ Изобилие", "✦ Поток", "✦ Власть", "✦ Баланс"), "card_69"))

        add(TarotCard(70, Arcana.PENTACLES, "Seven of Pentacles", "Семёрка Пентаклей", "Sept de Pentacles", "7",
            "A gardener leans on his hoe, looking at his crop of seven pentacles still on the vine. The work has been done; now he waits. This is the card of patient investment — you have planted and tended. The harvest is forming. Trust the process and resist the urge to pull up what is still growing.",
            "Садовник опирается на мотыгу, глядя на урожай из семи пентаклей всё ещё на лозе. Работа сделана; теперь он ждёт. Это карта терпеливых инвестиций. Доверяй процессу и сопротивляйся желанию вырвать то, что ещё растёт.",
            "Un jardinier s'appuie sur sa houe, regardant sa récolte de sept pentacles encore sur la vigne. Le travail a été fait; maintenant il attend. C'est la carte de l'investissement patient. Faites confiance au processus.",
            listOf("✦ Ожидание", "✦ Терпение", "✦ Инвестиции", "✦ Рост", "✦ Урожай", "✦ Доверие"), "card_70"))

        add(TarotCard(71, Arcana.PENTACLES, "Eight of Pentacles", "Восьмёрка Пентаклей", "Huit de Pentacles", "8",
            "A craftsman chips intently at a pentacle while seven more complete ones hang on the wall behind him. This is the mastery card — the hours of focused practice that produce excellence. The work itself is the reward. Skill is built through repetition and attention. What are you perfecting?",
            "Мастер тщательно чеканит пентакль, пока семь завершённых висят на стене позади. Это карта мастерства — часы сосредоточенной практики, дающие совершенство. Сама работа — это награда. Что ты оттачиваешь?",
            "Un artisan frappe attentivement un pentacle tandis que sept autres complets sont accrochés au mur derrière lui. C'est la carte de la maîtrise. Le travail lui-même est la récompense. Qu'est-ce que vous perfectionnez?",
            listOf("✦ Труд", "✦ Навык", "✦ Мастерство", "✦ Практика", "✦ Совершенство", "✦ Ремесло"), "card_71"))

        add(TarotCard(72, Arcana.PENTACLES, "Nine of Pentacles", "Девятка Пентаклей", "Neuf de Pentacles", "9",
            "A finely dressed woman walks through her garden, a falcon on her wrist and a snail at her feet. She has built something beautiful through discipline and taste, and she enjoys it with quiet confidence. This is the card of earned luxury, self-sufficiency and well-deserved independence.",
            "Изысканно одетая женщина идёт через свой сад с соколом на запястье и улиткой у ног. Она построила что-то прекрасное через дисциплину и вкус, и наслаждается этим с тихой уверенностью. Это карта заслуженной роскоши и независимости.",
            "Une femme élégamment vêtue traverse son jardin avec un faucon à son poignet. Elle a construit quelque chose de beau par la discipline et le goût, et en jouit avec une confiance tranquille. C'est la carte du luxe mérité et de l'indépendance bien gagnée.",
            listOf("✦ Достаток", "✦ Независимость", "✦ Вкус", "✦ Сад", "✦ Дисциплина", "✦ Роскошь"), "card_72"))

        add(TarotCard(73, Arcana.PENTACLES, "Ten of Pentacles", "Десятка Пентаклей", "Dix de Pentacles", "10",
            "An old man sits outside a turreted home with children and grandchildren around him. The ten pentacles on the card form the Kabbalistic Tree of Life. This is legacy: wealth that spans generations, family that sustains through time, the fullness of a well-lived life. What are you building that will outlast you?",
            "Старый человек сидит снаружи дома с детьми и внуками вокруг. Десять пентаклей образуют Каббалистическое Древо Жизни. Это наследие: богатство, охватывающее поколения, семья, сохраняющаяся во времени.",
            "Un vieil homme est assis à l'extérieur d'une maison avec des enfants et petits-enfants autour. Les dix pentacles forment l'Arbre de Vie kabbalistique. C'est l'héritage: la richesse qui s'étend sur des générations.",
            listOf("✦ Наследие", "✦ Семья", "✦ Богатство", "✦ Традиция", "✦ Дом", "✦ Полнота"), "card_73"))

        add(TarotCard(74, Arcana.PENTACLES, "Page of Pentacles", "Паж Пентаклей", "Valet de Pentacles", "Page",
            "The Page of Pentacles holds a single coin up in wonder and study — he is the eternal student, the one who takes the time to understand things thoroughly before acting. Diligent, grounded and patient, he builds the foundations that others will stand on. A new opportunity for learning is opening.",
            "Паж Пентаклей держит монету вверх в изумлении и изучении. Он вечный студент, тот, кто берёт время тщательно понять вещи. Дисциплинированный, приземлённый и терпеливый — он строит основы, на которых стоят другие.",
            "Le Valet de Pentacles tient une seule pièce en l'air dans l'émerveillement et l'étude. Il est l'éternel étudiant, celui qui prend le temps de comprendre les choses avant d'agir. Diligent, ancré et patient.",
            listOf("✦ Учёба", "✦ Амбиция", "✦ Земля", "✦ Практика", "✦ Внимание", "✦ Потенциал"), "card_74"))

        add(TarotCard(75, Arcana.PENTACLES, "Knight of Pentacles", "Рыцарь Пентаклей", "Chevalier de Pentacles", "Knight",
            "The Knight of Pentacles sits on his still horse in a ploughed field — unlike his fiery or watery counterparts, he does not charge. He methodically works the ground, returning to the same field until it yields. Reliability and steadiness are his gifts. Slow and steady wins this race.",
            "Рыцарь Пентаклей сидит на неподвижном коне в вспаханном поле. В отличие от своих аналогов, он не заряжает. Он методично работает землю, возвращаясь к тому же полю, пока оно не даст урожай. Надёжность — его дар.",
            "Le Chevalier de Pentacles est assis sur son cheval immobile dans un champ labouré. Contrairement à ses homologues, il ne charge pas. Il travaille méthodiquement le terrain. La fiabilité et la constance sont ses dons.",
            listOf("✦ Методичность", "✦ Надёжность", "✦ Трудолюбие", "✦ Терпение", "✦ Поле", "✦ Устойчивость"), "card_75"))

        add(TarotCard(76, Arcana.PENTACLES, "Queen of Pentacles", "Королева Пентаклей", "Reine de Pentacles", "Queen",
            "The Queen of Pentacles sits in a lush garden, a rabbit at her feet, a pentacle cradled in her lap. She is the embodiment of abundant care — nurturing through practical action, creating beauty and security for all those in her domain. The earth flourishes in her presence. Abundance flows from attentive care.",
            "Королева Пентаклей сидит в пышном саду, пентакль в её коленях. Она воплощает обильную заботу — питая через практические действия, создавая красоту и безопасность. Земля процветает в её присутствии.",
            "La Reine de Pentacles est assise dans un jardin luxuriant, un pentacle dans son giron. Elle est l'incarnation des soins abondants — nourrissant par l'action pratique, créant beauté et sécurité. La terre s'épanouit en sa présence.",
            listOf("✦ Забота", "✦ Природа", "✦ Изобилие", "✦ Практичность", "✦ Мать", "✦ Сад"), "card_76"))

        add(TarotCard(77, Arcana.PENTACLES, "King of Pentacles", "Король Пентаклей", "Roi de Pentacles", "King",
            "The King of Pentacles leans back on his vine-entwined throne, surrounded by symbols of wealth and success earned through years of disciplined effort. He is the self-made sovereign — he built this. His gift is the knowledge that abundance is a practice, not a luck. Steady and prosperous.",
            "Король Пентаклей откидывается на своём троне, оплетённом лозой, окружённый символами богатства, заработанного годами дисциплинированных усилий. Он создал это сам. Его дар — знание того, что изобилие — это практика, а не удача.",
            "Le Roi de Pentacles se penche en arrière sur son trône enlacé de vigne, entouré de symboles de richesse gagnée par des années d'efforts disciplinés. Il a construit cela. Son don est la connaissance que l'abondance est une pratique.",
            listOf("✦ Богатство", "✦ Лидерство", "✦ Стабильность", "✦ Дисциплина", "✦ Мудрость", "✦ Власть"), "card_77"))

    }

    fun shuffled(): MutableList<TarotCard> = allCards.toMutableList().apply { shuffle() }
}
