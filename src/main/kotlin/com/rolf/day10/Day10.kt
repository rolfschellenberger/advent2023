package com.rolf.day10

import com.rolf.Day
import com.rolf.util.Direction
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day10().run()
}

class Day10 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()

        // We don't know which way to go, so try all
        for (symbol in listOf("|", "-", "L", "J", "7", "F")) {
            grid.set(start, symbol)

            val positions = findLoop2(grid, start)
            if (positions.isNotEmpty()) {
                println("symbol: $symbol")
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
            val options = neighbours - path
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
        for (symbol in listOf("|")) {
            grid.set(start, symbol)

            val positions = findLoop2(grid, start)

        }
    }
}
