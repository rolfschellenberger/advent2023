package com.rolf.day07

class Hand2(
    cards: List<Char>,
    bid: Int,
) : Hand(cards, bid, listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')) {
    override fun getBestType(): HandType {
        // See what is the best hand type to create from this hand when changing jokers
        // To make it the best option possible:
        // 1. Look for the most frequent card and join those
        // 2. If there are multiple cards with the same frequency, join one, it doesn't matter
        val bestCards = getBestCards()
        val tempHand = Hand2(
            bestCards, bid
        )
        return tempHand.getType()
    }

    override fun getBestCards(): List<Char> {
        val cardGroups = cards.groupBy {
            it
        }
        var largestGroup = 0
        var largestChar = ' '
        for (group in cardGroups) {
            if (group.key != 'J' && group.value.size > largestGroup) {
                largestGroup = group.value.size
                largestChar = group.key
            }
        }
        return cards.map {
            if (it == 'J') largestChar else it
        }
    }

    override fun toString(): String {
        return "Hand2(cards=$cards, bid=$bid, type=${getType()}, bestCards=${getBestCards()}, bestType=${getBestType()})"
    }
}
