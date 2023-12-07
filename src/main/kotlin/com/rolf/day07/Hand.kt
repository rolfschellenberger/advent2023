package com.rolf.day07

data class Hand(
    val cards: List<Char>,
    val bid: Int
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        // Returns 0 if the main string and the other string are equal
        // Returns a negative number if the other string’s ASCII value is bigger than the main string
        // Returns a positive number if the other string’s ASCII value is smaller than the main string

        // Compare type
        val type = getType()
        val otherType = other.getType()
        val typeCompare = type.compareTo(otherType)
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

        return 0
    }

    private fun getType(): HandType {
        val cardGroups = cards.groupBy {
            it
        }
        val cardCounts = cardGroups.map {
            it.value.size
        }.toList().sorted()

        if (cardGroups.size == 1) return HandType.FIVE_OF_A_KIND
        if (
            cardGroups.size == 2
            && cardCounts[0] == 1 || cardCounts[1] == 4
        ) return HandType.FOUR_OF_A_KIND
        if (
            cardGroups.size == 2
            && cardCounts[0] == 2 && cardCounts[1] == 3
        ) return HandType.FULL_HOUSE
        if (
            cardGroups.size == 3
            && cardCounts[0] == 1 && cardCounts[1] == 1 && cardCounts[2] == 3
        ) return HandType.THREE_OF_A_KIND
        if (
            cardGroups.size == 3
            && cardCounts[0] == 1 && cardCounts[1] == 2 && cardCounts[2] == 2
        ) return HandType.TWO_PAIR
        if (
            cardGroups.size == 4
            && cardCounts[0] == 1 && cardCounts[1] == 1 && cardCounts[2] == 1 && cardCounts[3] == 2
        ) return HandType.ONE_PAIR

        return HandType.HIGH_CARD
    }

    private fun getCardTypes(): List<CardType> {
        return cards.map {
            CardType.fromValue(it)
        }
    }

    override fun toString(): String {
        return "Hand(cards=$cards, bid=$bid, type=${getType()})"
    }
}
