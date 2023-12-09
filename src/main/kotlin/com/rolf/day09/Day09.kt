package com.rolf.day09

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day09().run()
}

class Day09 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            lines.map { line ->
                splitLine(line, " ").map { number ->
                    number.toInt()
                }
            }.sumOf {
                getValue(it)
            }
        )
    }

    private fun getValue(values: List<Int>): Int {
        if (values.all { it == 0 }) return 0
        return values.last() + getValue(getNextSequence(values))
    }

    override fun solve2(lines: List<String>) {
        println(
            lines.map { line ->
                splitLine(line, " ").map { number ->
                    number.toInt()
                }
            }.sumOf {
                getValue2(it)
            }
        )
    }

    private fun getValue2(values: List<Int>): Int {
        if (values.all { it == 0 }) return 0
        return values.first() - getValue2(getNextSequence(values))
    }

    private fun getNextSequence(values: List<Int>): List<Int> {
        return (0 until values.size - 1).map {
            values[it + 1] - values[it]
        }
    }
}
