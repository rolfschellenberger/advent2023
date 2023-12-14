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
        println(grid)
        println()
        val tilted = tilNorth(grid)
        println(tilted)
        val height = tilted.height()

        var sum = 0L
        for ((index, row) in tilted.getRows().withIndex()) {
            val score = height - index
            val count = row.count { it == "O" }
//            println(score)
//            println(count)
            sum += (score * count)
        }
        println(sum)
    }

    private fun tilNorth(grid: MatrixString): MatrixString {
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
        return grid
    }

    override fun solve2(lines: List<String>) {
    }
}
