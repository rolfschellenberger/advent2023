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
//        println(grid)
//        println()
        val tilted = tilNorth(grid)
//        println(tilted)

        val sum = getLoad(tilted)
        println(sum)
    }

    fun getLoad(grid: MatrixString): Long {
        val height = grid.height()
        var sum = 0L
        for ((index, row) in grid.getRows().withIndex()) {
            val score = height - index
            val count = row.count { it == "O" }
//            println(score)
//            println(count)
            sum += (score * count)
        }
        return sum
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
        val grid = MatrixString.build(splitLines(lines))

        var i = 0L
        val cache = mutableMapOf<String, Pair<Long, MatrixString>>()
        while (true) {
            val key = grid.toString()
            if (cache.contains(key)) {
                val (lastI, matrix) = cache.getValue(key)
                println("Found the same in $i and $lastI")
                // Found the same in 175 and 103 = 72 diff
                val diff = i - lastI
                val remainder = ((1000000000L-lastI) % diff) + lastI -1
//                val remainder = 4L
                println(remainder)
                val result = cache.filter {
                    it.value.first == remainder
                }.map {
                    it.value.second
                }.first()
//                println(result)
                println(getLoad(result))
                break
            }

            // Cycle
            cycle(grid)
//            println(getLoad(grid))
            cache[key] = i to grid.copy()
//            println("after cycle $i")
//            println(grid)
//            println()

            i++
//            if (i > 4) break
        }

        // 84077 wrong
        // 86271 wrong
        // 83795 wrong
        // 83790
    }

    private fun cycle(grid: MatrixString) {
        // North
        tilNorth2(grid)
//        println("north")
//        println(grid)

        // West
        grid.rotateRight()
        tilNorth2(grid)
//        println("west")
//        println(grid)

        // South
        grid.rotateRight()
        tilNorth2(grid)
//        println("south")
//        println(grid)

        // East
        grid.rotateRight()
        tilNorth2(grid)
//        println("east")
//        println(grid)

        // Restore
        grid.rotateRight()
    }

    private fun tilNorth2(grid: MatrixString) {
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
}
