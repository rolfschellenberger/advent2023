package com.rolf.day10

import com.rolf.Day
import com.rolf.util.*

fun main() {
    Day10().run()
}

class Day10 : Day() {

    private val symbolMap = mapOf(
        "L" to "└",
        "7" to "┐",
        "J" to "┘",
        "F" to "┌",
        "." to " "
    )
    private val pipeSymbols: List<String> = listOf("|", "-", "└", "┐", "┘", "┌")
    private val symbolDirection = mapOf(
        "|" to listOf(Direction.NORTH, Direction.SOUTH),
        "-" to listOf(Direction.EAST, Direction.WEST),
        "└" to listOf(Direction.NORTH, Direction.EAST),
        "┐" to listOf(Direction.SOUTH, Direction.WEST),
        "┘" to listOf(Direction.NORTH, Direction.WEST),
        "┌" to listOf(Direction.SOUTH, Direction.EAST),
        " " to listOf()
    )

    override fun solve1(lines: List<String>) {
        val grid = parseGrid(lines)
        val start = grid.find("S").first()
        // We don't know which way to go, so try all
        val (_, loop) = findLoop(grid, start)
        println(loop.size / 2)

    }

    private fun parseGrid(lines: List<String>): MatrixString {
        val grid = MatrixString.build(splitLines(lines))
        grid.replace(symbolMap)
        return grid
    }

    private fun findLoop(grid: MatrixString, start: Point): Pair<String, Set<Point>> {
        for (symbol in pipeSymbols) {
            grid.set(start, symbol)

            val positions = findLoopRound(grid, start)
            if (positions.isNotEmpty()) {
                return symbol to positions
            }
        }
        throw RuntimeException("No loop found")
    }

    private fun findLoopRound(grid: MatrixString, start: Point): Set<Point> {
        val path = mutableSetOf(start)

        var location = start

        while (true) {
            val directions = getDirections(grid, location)
            val neighbours = directions.mapNotNull {
                grid.getForward(location, it)
            }
            val options = neighbours - path - start
            if (options.isEmpty()) {
                // Are we back at start?
                if (neighbours.contains(start)) {
                    // And is start connected to our current location
                    if (isConnected(grid, start, location)) {
                        return path + location
                    }
                }
                return emptySet()
            }
            val next = options.first()
            path.add(next)
            location = next
        }
    }

    private fun isConnected(grid: MatrixString, from: Point, to: Point): Boolean {
        val directions1 = getDirections(grid, from)
        for (direction in directions1) {
            val next = grid.getForward(from, direction)
            if (next == to) return true
        }
        return false
    }

    private fun getDirections(grid: MatrixString, location: Point): List<Direction> {
        val value = grid.get(location)
        return symbolDirection.getValue(value)
    }

    override fun solve2(lines: List<String>) {
        val grid = parseGrid(lines)
        val start = grid.find("S").first()
        // We don't know which way to go, so try all
        val (startSymbol, loop) = findLoop(grid, start)
        grid.set(start, startSymbol)

        // Keep only the loop
        for (point in grid.allPoints()) {
            if (!loop.contains(point)) {
                grid.set(point, " ")
            }
        }

        // Blow up grid to detect locations that are connected to the outside
        val largeGrid = blowUp(grid)

        // Fill with water
        val outside = Point(0, 0)
        val filledLocations = largeGrid.waterFill(outside, setOf("|", "-", "└", "┘", "┐", "┌"), diagonal = false)
        filledLocations.forEach {
            largeGrid.set(it, "~")
        }

        // Now count the number of empty cells
        var count = 0
        for (y in 0 until largeGrid.height() step 3) {
            for (x in 0 until largeGrid.width() step 3) {
                if (largeGrid.get(x + 1, y + 1) == " ") {
                    count++
                }
            }
        }
        println(count)
    }

    private fun blowUp(grid: MatrixString): MatrixString {
        val largeGrid = MatrixString.buildDefault(grid.width() * 3, grid.height() * 3, " ")
        for (point in grid.allPoints()) {
            val patch = blowUpPoint(grid, point)
            for (patchPoint in patch.allPoints()) {
                largeGrid.set(
                    point.x * 3 + patchPoint.x,
                    point.y * 3 + patchPoint.y,
                    patch.get(patchPoint)
                )
            }
        }
        return largeGrid
    }

    private fun blowUpPoint(grid: MatrixString, point: Point): MatrixString {
        return when (val value = grid.get(point)) {
            "└" -> {
                MatrixString.build(
                    listOf(
                        splitLine(" | "),
                        splitLine(" └-"),
                        splitLine("   ")
                    )
                )
            }

            "┐" -> {
                MatrixString.build(
                    listOf(
                        splitLine("   "),
                        splitLine("-┐ "),
                        splitLine(" | ")
                    )
                )
            }

            "┘" -> {
                MatrixString.build(
                    listOf(
                        splitLine(" | "),
                        splitLine("-┘ "),
                        splitLine("   ")
                    )
                )
            }

            "┌" -> {
                MatrixString.build(
                    listOf(
                        splitLine("   "),
                        splitLine(" ┌-"),
                        splitLine(" | ")
                    )
                )
            }

            "-" -> {
                MatrixString.build(
                    listOf(
                        splitLine("   "),
                        splitLine("---"),
                        splitLine("   ")
                    )
                )
            }

            "|" -> {
                MatrixString.build(
                    listOf(
                        splitLine(" | "),
                        splitLine(" | "),
                        splitLine(" | ")
                    )
                )
            }

            " " -> {
                MatrixString.build(
                    listOf(
                        splitLine("   "),
                        splitLine("   "),
                        splitLine("   ")
                    )
                )
            }

            else -> throw RuntimeException("Unknown symbol $value")
        }
    }
}
