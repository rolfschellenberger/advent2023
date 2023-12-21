package com.rolf.day21

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day21().run()
}

class Day21 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()
        grid.set(start, ".")
        val filled = grid.waterFill(start, setOf("#"), diagonal = false, steps = 64)
        println(filled.size)
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()
        grid.set(start, ".")
        val size = grid.width() - 1
        println("size: $size")

        // So 26501365 - 65 is leading to the borders
        // 26501300/131 (side lengths) = 202.300
        // So we go 202.300 spaces to the left, right, top and bottom
        // So this generates a space of (2*202.300+1) by (2*202.300+1) tiles
        // The tiles are either completely filled or they are partly filled in different shapes:
        // - 4 x to 1 edge (most top, left, right and bottom one)
        // - all the other edges will have a small triangle or a big triangle
//        val full = draw(grid, start, Int.MAX_VALUE)
//        println(full.first.count("O"))
//        println(full.second.count("O"))

//        val right = draw(grid, Point(0, size / 2), size)
//        val left = draw(grid, Point(size, size / 2), size)
//        val top = draw(grid, Point(size / 2, size), size)
//        val bottom = draw(grid, Point(size / 2, 0), size)
//        val rightBottomSmall = draw(grid, Point(0, 0), size / 2)
//        val rightBottomBig = draw(grid, Point(0, 0), size + size / 2)
//        val leftBottomSmall = draw(grid, Point(size, 0), size / 2)
//        val leftBottomBig = draw(grid, Point(size, 0), size + size / 2)
        val rightTopSmall = draw(grid, Point(0, size), size / 2)
        val rightTopBig = draw(grid, Point(0, size), size + size / 2)
        val leftTopSmall = draw(grid, Point(size, size), size / 2)
        val leftTopBig = draw(grid, Point(size, size), size + size / 2)
        println(rightTopSmall.second)
        println(rightTopBig.first)

        /**
         * count the full tiles in original or inverse
         * count the edges in small or large angle and in original or inverse
         * add 1 top, bottom, right, left
         */
        val maxSteps = 50
        var sideSteps = maxSteps - size / 2 // 202_300
        println(sideSteps)
        for (row in 0 until 2 /*202_300*/) {

            sideSteps -= 2
        }

        val side = 202_300L * 2 + 1
    }

    private fun draw(grid: MatrixString, start: Point, totalSteps: Int): Pair<MatrixString, MatrixString> {
        val visited = mutableMapOf<Point, Int>()
        val toVisit = mutableListOf(start to 0)
        val notAllowed = grid.find("#").toSet()
        while (toVisit.isNotEmpty()) {
            val (location, steps) = toVisit.removeFirst()
            if (visited.containsKey(location)) continue
            if (steps > totalSteps) break

            visited[location] = steps
            for (neighbour in grid.getNeighbours(location, diagonal = false)) {
                if (!notAllowed.contains(neighbour)) {
                    toVisit.add(neighbour to steps + 1)
                }
            }
        }

        val newGrid1 = grid.copy()
        for (v in visited) {
            if (v.value % 2 == 0) {
                newGrid1.set(v.key, "O")
            }
        }
        val newGrid2 = grid.copy()
        for (v in visited) {
            if (v.value % 2 == 1) {
                newGrid2.set(v.key, "O")
            }
        }
        return newGrid1 to newGrid2
    }

    fun inverse(grid: MatrixString): MatrixString {
        val copy = grid.copy()
        copy.replace(
            mapOf(
                "O" to ".",
                "." to "O"
            )
        )
        return copy
    }
}
