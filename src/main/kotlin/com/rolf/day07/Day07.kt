package com.rolf.day07

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day07().run()
}

class Day07 : Day() {
    override fun solve1(lines: List<String>) {
        val hands = lines.map {
            parse(it)
        }
        val sortedHands = hands.sortedDescending()
        println(hands)
        println(sortedHands)
        println(
            sortedHands.mapIndexed { index, hand ->
                (index + 1) * hand.bid
            }.sum()
        )
    }

    private fun parse(it: String): Hand {
        val (cards, bid) = splitLine(it, " ")
        return Hand(cards.toCharArray().toList(), bid.toInt())
    }

    override fun solve2(lines: List<String>) {
        val hands = lines.map {
            parse(it)
        }
        val sortedHands = hands.sortedDescending()
        println(hands)
        println(sortedHands)
        println(
            sortedHands.mapIndexed { index, hand ->
                (index + 1) * hand.bid
            }.sum()
        )
    }
}
