package com.rolf.day06

import com.rolf.Day

fun main() {
    Day06().run()
}

class Day06 : Day() {
    override fun solve1(lines: List<String>) {
        val races = listOf(
            Race(46, 358),
            Race(68, 1054),
            Race(98, 1807),
            Race(66, 1080)
        )
        println(
            races.map {
                it.wins()
            }.map {
                it.size
            }.reduce { a, b -> a * b }
        )
    }

    override fun solve2(lines: List<String>) {
        val races = listOf(
            Race(46689866, 358105418071080),
        )
        println(
            races.map {
                it.wins()
            }.map {
                it.size
            }.reduce { a, b -> a * b }
        )
    }
}
