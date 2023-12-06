package com.rolf.day06

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day06().run()
}

class Day06 : Day() {
    override fun solve1(lines: List<String>) {
        val races = parseRaces(lines[0], lines[1])
        println(
            races.map {
                it.wins()
            }.reduce { a, b -> a * b }
        )
    }

    private fun parseRaces(timeLine: String, distanceLine: String): List<Race> {
        val times = parseNumbers(timeLine)
        val distances = parseNumbers(distanceLine)
        return times.zip(distances).map {
            Race(it.first, it.second)
        }
    }

    override fun solve2(lines: List<String>) {
        val race = parseRace(lines[0], lines[1])
        println(
            race.wins()
        )
    }

    private fun parseRace(timeLine: String, distanceLine: String): Race {
        val times = parseNumbers(timeLine)
        val distances = parseNumbers(distanceLine)
        val time = times.joinToString("").toLong()
        val distance = distances.joinToString("").toLong()
        return Race(time, distance)
    }

    private fun parseNumbers(line: String): List<Long> {
        val (_, numbersString) = splitLine(line, ":")
        return splitLine(numbersString, " ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }
    }
}
