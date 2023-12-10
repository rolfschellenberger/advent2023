package com.rolf.day10

import com.rolf.Day
import com.rolf.util.*

fun main() {
    Day10().run()
}

class Day10 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()

        // We don't know which way to go, so try all
        for (symbol in listOf("|", "-", "L", "J", "7", "F")) {
//        for (symbol in listOf("F")) {
            grid.set(start, symbol)

            println("symbol: $symbol")
            val positions = findLoop2(grid, start)
            if (positions.isNotEmpty()) {
//                println("positions: $positions")
                println(positions.size / 2)
            }
        }
    }

    private fun findLoop2(grid: MatrixString, start: Point): List<Point> {
        val path = mutableListOf<Point>()

        var location = start

        while (true) {
//            println("location: $location")
            val directions = getDirections(grid, location)
//            println("directions: $directions")
            val neighbours = directions.map {
                grid.getForward(location, it)
            }.filterNotNull()
//            println("neighbours: $neighbours")
            val options = neighbours - path - start
//            println("options: $options")
            if (options.isEmpty()) {
                // Are we back at start?
                if (neighbours.contains(start)) {
                    // And is start connected to our current location
                    if (isConnected(grid, start, location)) {
                        return path + location
                    }
                }
                return emptyList()
            }
            val next = options.first()
            if (next == start) {
                if (isConnected(grid, start, location)) {
                    return path + location
                }
                return emptyList()
            }
//            println("next: $next")
            path.add(next)
            location = next
        }
    }

    private fun printGrid(grid: MatrixString, points: List<Point>) {
        val copy = grid.copy()
        for (point in points) {
            copy.set(point, " ")
        }
        println(copy)
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
        return when (val value = grid.get(location)) {
            "|" -> listOf(Direction.NORTH, Direction.SOUTH)
            "-" -> listOf(Direction.EAST, Direction.WEST)
            "L" -> listOf(Direction.NORTH, Direction.EAST)
            "J" -> listOf(Direction.NORTH, Direction.WEST)
            "7" -> listOf(Direction.SOUTH, Direction.WEST)
            "F" -> listOf(Direction.SOUTH, Direction.EAST)
            "." -> listOf()
            else -> throw RuntimeException("Unknown value: $value")
        }
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()

        // We don't know which way to go, so try all
//        for (symbol in listOf("|", "-", "L", "J", "7", "F")) {
        for (symbol in listOf("|")) {
//        for (symbol in listOf("F")) {
            grid.set(start, symbol)

            val positions = findLoop2(grid, start) + start
            for (point in grid.allPoints()) {
                if (!positions.contains(point)) {
                    grid.set(point, " ")
                }
            }

            grid.replace(
                mapOf(
                    "L" to "└",
                    "7" to "┐",
                    "J" to "┘",
                    "F" to "┌",
                )
            )
//            println(grid)

            // Blow up grid
            val largeGrid = MatrixString.buildDefault(grid.width() * 3, grid.height() * 3, " ")
            for (point in grid.allPoints()) {
                val patch = blowUp(grid, point)
//                println(patch)
                for (patchPoint in patch.allPoints()) {
                    largeGrid.set(
                        point.x * 3 + patchPoint.x,
                        point.y * 3 + patchPoint.y,
                        patch.get(patchPoint)
                    )
                }
            }
//            println(largeGrid)

            val outside = Point(0, 0)
            var count = 0
            val outsideLocations = mutableSetOf<Point>()
//            val locations = mutableListOf<Point>()
            for (y in 0 until largeGrid.height() step 3) {
                for (x in 0 until largeGrid.width() step 3) {
                    val point = Point(x + 1, y + 1)
                    val value = largeGrid.get(point)
                    if (point != outside && value == " ") {
//                        val copy = largeGrid.copy()
//                        copy.set(point, "#")
                        if (!outsideLocations.contains(point)) {
//                        println(copy)
                            val path = largeGrid.findPathByValue(point, outside, setOf("|", "-", "└", "┘", "┐", "┌"))
//                        println(path.size)
                            if (path.isEmpty()) {
//                            locations.add(point)
                                count++
                            } else {
                                outsideLocations.addAll(path)
                            }
                        }
                    }
                }
            }
            println(count)
            // 669 too high
        }
    }

    private fun blowUp(grid: MatrixString, point: Point): MatrixString {
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
