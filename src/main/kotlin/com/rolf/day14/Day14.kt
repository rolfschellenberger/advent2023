package com.rolf.day14

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day14().run()
}

class Day14 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        tiltNorth(grid)
        val sum = getLoad(grid)
        println(sum)
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))

        var i = 0L
        val cache = mutableMapOf<String, Pair<Long, MatrixString>>()
        while (true) {
            val key = grid.toString()
            if (cache.contains(key)) {
                val (lastI, _) = cache.getValue(key)
                val diff = i - lastI
                val remainder = ((1000000000L - lastI) % diff) + lastI - 1
                val result = cache.filter {
                    it.value.first == remainder
                }.map {
                    it.value.second
                }.first()
                println(getLoad(result))
                break
            }

            // Cycle
            cycle(grid)
            cache[key] = i to grid.copy()
            i++
        }
    }

    private fun cycle(grid: MatrixString) {
        // North
        tiltNorth(grid)

        // West
        grid.rotateRight()
        tiltNorth(grid)

        // South
        grid.rotateRight()
        tiltNorth(grid)

        // East
        grid.rotateRight()
        tiltNorth(grid)

        // Restore
        grid.rotateRight()
    }

    private fun tiltNorth(grid: MatrixString) {
        val rocks = grid.find("O")
        for (rock in rocks) {
            var location: Point? = rock
            while (location != null) {
                val north = grid.getUp(location)
                if (north == null) {
                    // Move rock
                    grid.set(rock, ".")
                    grid.set(location, "O")
                    location = null
                } else {
                    val value = grid.get(north)
                    // Not empty location, so stop
                    if (value != ".") {
                        grid.set(rock, ".")
                        grid.set(location, "O")
                        location = null
                    } else {
                        // Continue moving
                        location = north
                    }
                }
            }
        }
    }

    private fun getLoad(grid: MatrixString): Long {
        val height = grid.height()
        var sum = 0L
        for ((index, row) in grid.getRows().withIndex()) {
            val score = height - index
            val count = row.count { it == "O" }
            sum += (score * count)
        }
        return sum
    }
}
