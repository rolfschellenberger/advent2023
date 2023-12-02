package com.rolf.day02

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day02().run()
}

class Day02 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            lines.map {
                toGame(it)
            }.filter {
                it.hasMax(12, 13, 14)
            }.sumOf {
                it.id
            }
        )
    }

    override fun solve2(lines: List<String>) {
        println(
            lines.map {
                toGame(it)
            }.sumOf {
                it.power()
            }
        )
    }

    private fun toGame(line: String): Game {
        val (id, rest) = splitLine(line, delimiter = ": ")
        val (_, gameId) = splitLine(id, "Game ")
        val rounds = splitLine(rest, delimiter = "; ")
        val allRounds = rounds.map {
            val colors = splitLine(it, ", ")
            var red = 0
            var green = 0
            var blue = 0
            for (colorCount in colors) {
                val (count, color) = splitLine(colorCount, " ")
                when (color) {
                    "red" -> red = count.toInt()
                    "green" -> green = count.toInt()
                    "blue" -> blue = count.toInt()
                    else -> throw RuntimeException("Unknown color $color")
                }
            }
            Round(red, green, blue)
        }
        return Game(gameId.toInt(), allRounds)
    }
}
