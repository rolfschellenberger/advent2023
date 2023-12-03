package com.rolf.day03

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.isNumeric
import com.rolf.util.splitLines

fun main() {
    Day03().run()
}

class Day03 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val numbers = findNumbers(grid)

        // Now we need to know which numbers are connected to a symbol and sum those
        println(
            numbers.filter {
                isNextToSymbol(grid, it)
            }.sumOf {
                it.value
            }
        )
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val numbers = findNumbers(grid)

        // Now we need to know which numbers are connected to a gear and group them by gear
        val gears = mutableMapOf<Point, MutableList<Long>>()
        for (number in numbers) {
            val connectedGears = getConnectedGears(grid, number)
            for (gear in connectedGears) {
                val numberList = gears.getOrDefault(gear, mutableListOf())
                numberList.add(number.value)
                gears[gear] = numberList
            }
        }

        // Sum the ratios of the gears that have exactly 2 part numbers
        println(
            gears.filter {
                it.value.size == 2
            }.map {
                it.value[0] * it.value[1]
            }.sum()
        )
    }

    private fun findNumbers(grid: MatrixString): List<Number> {
        val numbers = mutableListOf<Number>()
        val positions = mutableListOf<Point>()
        for (y in 0 until grid.height()) {
            for (x in 0 until grid.width()) {
                val point = Point(x, y)
                if (grid.get(point).isNumeric()) {
                    positions.add(point)
                } else {
                    if (positions.isNotEmpty()) {
                        // Found a new number
                        numbers.add(buildNumber(grid, positions))
                    }
                    positions.clear()
                }
            }
            if (positions.isNotEmpty()) {
                // Found a new number
                numbers.add(buildNumber(grid, positions))
            }
            positions.clear()
        }
        return numbers
    }

    private fun buildNumber(grid: MatrixString, positions: List<Point>): Number {
        val number = positions.joinToString("") {
            grid.get(it)
        }.toLong()
        return Number(positions.toSet(), number)
    }

    private fun isNextToSymbol(grid: MatrixString, number: Number): Boolean {
        val symbols = number.points
            .map {
                grid.getNeighbours(it)
            }
            .flatten()
            .map {
                grid.get(it)
            }
            .filter {
                it != "." && !it.isNumeric()
            }
        return symbols.isNotEmpty()
    }

    private fun getConnectedGears(grid: MatrixString, number: Number): Set<Point> {
        return number.points
            .asSequence()
            .map {
                grid.getNeighbours(it)
            }
            .flatten()
            .map {
                grid.get(it) to it
            }
            .filter {
                it.first == "*"
            }
            .map {
                it.second
            }.toSet()
    }
}
