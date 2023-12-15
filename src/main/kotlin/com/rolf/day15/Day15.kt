package com.rolf.day15

import com.rolf.Day
import com.rolf.util.splitLine
import java.util.regex.Pattern

fun main() {
    Day15().run()
}

class Day15 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            splitLine(lines.first(), ",").sumOf {
                hash(it)
            }
        )
    }

    private fun hash(hash: String): Int {
        var result = 0
        for (char in hash) {
            result += char.code
            result *= 17
            result %= 256
        }
        return result
    }

    override fun solve2(lines: List<String>) {
        val steps = splitLine(lines.first(), ",")
        val boxes = List<Box>(256) { Box() }

        for (step in steps) {
            val (label, numberString) =
                splitLine(step, pattern = Pattern.compile("[=-]"))
            val boxNumber = hash(label)

            // -
            if (numberString.isEmpty()) {
                boxes[boxNumber].remove(Lens(label))
            }
            // =
            else {
                boxes[boxNumber].add(Lens(label, numberString.toInt()))
            }
        }

        println(
            boxes.withIndex().sumOf { (index, box) ->
                box.focusingPower(index)
            }
        )
    }
}
