package com.rolf.day04

import com.rolf.Day
import com.rolf.util.splitLine
import kotlin.math.pow

fun main() {
    Day04().run()
}

class Day04 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            lines.map {
                parseCard(it)
            }.sumOf {
                calculateScore(it.intersect())
            }
        )
    }

    private fun calculateScore(intersect: Int): Int {
        return 2.0.pow(intersect.toDouble() - 1).toInt()
    }

    override fun solve2(lines: List<String>) {
        val cards = lines.map {
            parseCard(it)
        }.associateBy {
            it.id
        }

        // Calculate the won cards
        for ((id, card) in cards) {
            val intersect = card.intersect()
            // The next X cards are won
            for (i in (id + 1)..(id + intersect)) {
                // Multiplied by the card count
                val cardToWin = cards.getValue(i)
                cardToWin.win(card.count)
            }
        }

        // Sum the total card count
        println(
            cards.map {
                it.value.count
            }.sum()
        )
    }

    private fun parseCard(line: String): Card {
        val id = getId(line)
        val card = getCard(line)
        val have = getHave(line)
        return Card(id, card, have)
    }

    private fun getId(line: String): Int {
        val (cardId, _) = splitLine(line, ": ")
        val parts = splitLine(cardId, " ")
        return parts.last().toInt()
    }

    private fun getHave(line: String): Set<Int> {
        val (_, have) = splitLine(line, " | ")
        return splitLine(have, " ")
            .filterNot { it.isBlank() }
            .map {
                it.toInt()
            }.toSet()
    }

    private fun getCard(line: String): Set<Int> {
        val (cards, _) = splitLine(line, " | ")
        val (_, numbers) = splitLine(cards, ": ")
        return splitLine(numbers, " ")
            .filterNot { it.isBlank() }
            .map {
                it.toInt()
            }.toSet()
    }
}
