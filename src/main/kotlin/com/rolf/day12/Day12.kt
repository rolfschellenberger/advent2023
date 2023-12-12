package com.rolf.day12

import com.rolf.Day
import com.rolf.util.splitLine
import java.util.regex.Pattern

fun main() {
    Day12().run()
}

class Day12 : Day() {
    override fun solve1(lines: List<String>) {
        var sum = 0L
        for (line in lines) {
            val (pattern, expected) = splitLine(line, " ")
            val expectedSequence = splitLine(expected, ",").map { it.toInt() }

            val options = generateOptions(pattern)
            val matches = options.map {
                toSequence(it)
            }.count {
                it == expectedSequence
            }
            sum += matches
        }
        println(sum)
    }

    private fun generateOptions(pattern: String): List<String> {
        val options = mutableListOf<String>()
        if (pattern.contains("?")) {
            val a = pattern.replaceFirst("?", ".")
            val b = pattern.replaceFirst("?", "#")
            if (a.contains("?")) {
                options.addAll(generateOptions(a))
            } else {
                options.add(a)
            }
            if (b.contains("?")) {
                options.addAll(generateOptions(b))
            } else {
                options.add(b)
            }
        }
        return options
    }

    private fun toSequence(line: String): List<Int> {
        val split = splitLine(line, pattern = Pattern.compile("\\.+")).filter { it.isNotEmpty() }
        return split.map {
            it.length
        }
    }

    override fun solve2(lines: List<String>) {
        var sum = 0L
        for (line in lines) {
            val (pattern, expected) = splitLine(line, " ")
            val newPattern = makeNewPattern(pattern)
            val expectedSequence = splitLine(expected, ",").map { it.toInt() }
            val newExpectedSequence = makeNewExpected(expectedSequence)
            sum += countArrangements(newPattern, newExpectedSequence)
        }
        println(sum)
    }

    private fun makeNewPattern(pattern: String): String {
        val new = mutableListOf<String>()
        for (i in 0 until 5) {
            new.add(pattern)
        }
        return new.joinToString("?")
    }

    private fun makeNewExpected(expectedSequence: List<Int>): List<Int> {
        val new = mutableListOf<Int>()
        for (i in 0 until 5) {
            new.addAll(expectedSequence)
        }
        return new
    }

    private val cache = mutableMapOf<String, Long>()

    private fun countArrangements(pattern: String, expectedSequence: List<Int>): Long {
        // Check cache for speed
        val key = "$pattern|$expectedSequence"
        if (cache.containsKey(key)) return cache.getValue(key)

        // Break if expected is empty
        if (expectedSequence.isEmpty()) {
            return if (!pattern.contains("#")) 1 else 0
        }

        val size = expectedSequence.first()
        var total = 0L
        for (i in pattern.indices) {
            val range = saveSubstring(pattern, i, i + size)
            if (
                i + size <= pattern.length &&
                range.all { it != '.' } &&
                (i == 0 || pattern[i - 1] != '#') &&
                (i + size == pattern.length || pattern[i + size] != '#')
            ) {
                total += countArrangements(
                    saveSubstring(pattern, i + size + 1),
                    expectedSequence.subList(1, expectedSequence.size)
                )
            }

            if (pattern[i] == '#') break
        }

        // Add to cache
        cache[key] = total
        return total
    }

    private fun saveSubstring(input: String, startIndex: Int): String {
        return if (startIndex >= input.length) "" else input.substring(startIndex)
    }

    private fun saveSubstring(input: String, startIndex: Int, endIndex: Int): String {
        return if (startIndex >= input.length) "" else input.substring(startIndex, minOf(endIndex, input.length))
    }
}
