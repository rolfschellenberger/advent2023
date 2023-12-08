package com.rolf.day07

open class Hand(
    val cards: List<Char>,
    val bid: Int,
    private val cardTypes: List<Char> = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        // Compare type
        val bestType = getBestType()
        val otherBestType = other.getBestType()
        val typeCompare = bestType.compareTo(otherBestType)
        if (typeCompare != 0) return typeCompare

        // Compare the card order
        val cardTypes = getCardTypes()
        val otherCardTypes = other.getCardTypes()
        for (i in 0..cardTypes.size) {
            val type1 = cardTypes[i]
            val type2 = otherCardTypes[i]
            val compareType = type1.compareTo(type2)
            if (compareType != 0) return compareType
        }

        // Equal
        return 0
    }

    open fun getBestType(): HandType {
        return getType()
    }

    open fun getBestCards(): List<Char> {
        return cards
    }

    internal fun getType(): HandType {
        val cardCounts = cards.groupBy {
            it
        }.map {
            it.value.size
        }.toList().sorted()

        return when (cardCounts) {
            listOf(5) -> HandType.FIVE_OF_A_KIND
            listOf(1, 4) -> HandType.FOUR_OF_A_KIND
            listOf(2, 3) -> HandType.FULL_HOUSE
            listOf(1, 1, 3) -> HandType.THREE_OF_A_KIND
            listOf(1, 2, 2) -> HandType.TWO_PAIR
            listOf(1, 1, 1, 2) -> HandType.ONE_PAIR
            listOf(1, 1, 1, 1, 1) -> HandType.HIGH_CARD
            else -> throw RuntimeException("Unexpected card groups: $cards")
        }
    }

    private fun getCardTypes(): List<Int> {
        return cards.map {
            cardTypes.indexOf(it)
        }
    }

    override fun toString(): String {
        return "Hand(cards=$cards, bid=$bid, type=${getType()}, bestCards=${getBestCards()}, bestType=${getBestType()})"
    }
}
