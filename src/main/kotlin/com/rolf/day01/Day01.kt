package com.rolf.day01

import com.rolf.Day

fun main() {
    Day01().run()
}

class Day01 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            lines
                .filter { it.isNotBlank() }
                .sumOf {
                    toValue(it)
                }
        )
    }

    private fun toValue(line: String): Long {
        // Keep only the digits in the input
        val digits = line.filter { it.isDigit() }
        val number = "${digits.first()}${digits.last()}"
        return number.toLong()
    }

    override fun solve2(lines: List<String>) {
        println(
            lines.filter {
                it.isNotBlank()
            }.map {
                textToNumber(it)
            }.sumOf {
                toValue(it)
            }
        )
    }

    private fun textToNumber(line: String): String {
        // Replace numbers without breaking other words, such as 'eightwo'
        return line
            .replace("one", "one1one")
            .replace("two", "two2two")
            .replace("three", "three3three")
            .replace("four", "four4four")
            .replace("five", "five5five")
            .replace("six", "six6six")
            .replace("seven", "seven7seven")
            .replace("eight", "eight8eight")
            .replace("nine", "nine9nine")
    }
}
