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

        val visited = visit(grid, start)
        val evenPoints = visited.filter {
            it.value % 2 == 0 && it.value <= 64
        }
        println(evenPoints.size)
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()
        grid.set(start, ".")
        val size = grid.width() - 1
        val maxSteps = 26501365
        val n = (maxSteps - grid.width() / 2) / grid.width() // 202_300

        // So 26501365 - 65 is leading to the borders
        // 26501300/131 (side lengths) = 202.300
        // So we go 202.300 spaces to the left, right, top and bottom
        // So this generates a space of (2*202.300+1) by (2*202.300+1) tiles
        // The tiles are either completely filled or they are partly filled
        //           ╎   ╎
        //          ╌╆━━━╅╌
        //           ┃/ \┃
        //           ┃ O ┃
        //       ╎  /┃   ┃\  ╎
        //      ╌╆━━━╋━━━╋━━━╅╌
        //       ┃/  ┃   ┃  \┃
        //       ┃ O ┃ E ┃ O ┃
        //   ╎  /┃   ┃   ┃   ┃\  ╎
        //  ╌╆━━━╋━━━╋━━━╋━━━╋━━━╅╌
        //   ┃/  ┃   ┃   ┃   ┃  \┃
        //   ┃ O ┃ E ┃ S ┃ E ┃ O ┃
        //   ┃\  ┃   ┃ O ┃   ┃  /┃
        //  ╌╄━━━╋━━━╋━━━╋━━━╋━━━╃╌
        //   ╎  \┃   ┃   ┃   ┃/  ╎
        //       ┃ O ┃ E ┃ O ┃
        //       ┃\  ┃   ┃  /┃
        //      ╌╄━━━╋━━━╋━━━╃╌
        //       ╎  \┃   ┃/  ╎
        //           ┃ O ┃
        //           ┃\ /┃
        //          ╌╄━━━╃╌
        //           ╎   ╎
        // We can count the tiles and add/subtract the partial filled tiles

        // For n=202300, which is even, we find out that there are (n + 1)^2 odd input-squares and n^2 even input-squares.
        val oddTileCount = (n + 1L) * (n + 1L)
        val evenTileCount = n * n.toLong()

        // Some corners (odd) have to be cut out of our square, the other corners (even) have to be added.
        // For each of the 4 corners, there are (n + 1) odd ones, and n even ones.
        val oddCornerCount = n + 1
        val evenCornerCount = n

        // Visit a tile to measure the steps
        val visited = visit(grid, start)

        val evenPoints = visited.filter {
            it.value % 2 == 0
        }
        val oddPoints = visited.filter {
            it.value % 2 == 1
        }
        val evenCornerPoints = visited.filter {
            it.value % 2 == 0 && it.value > size / 2
        }
        val oddCornerPoints = visited.filter {
            it.value % 2 == 1 && it.value > size / 2
        }

        // Total =
        // (n+1)*(n+1) * odd tile
        // + n*n * even tile
        // - (n-1) * odd corner
        // + n * even corner
        val total = (oddTileCount * oddPoints.size) +
                (evenTileCount * evenPoints.size) -
                (oddCornerCount * oddCornerPoints.size) +
                (evenCornerCount * evenCornerPoints.size) -
                n
        println(
            total
        )
    }

    private fun visit(grid: MatrixString, start: Point): Map<Point, Int> {
        val visited = mutableMapOf<Point, Int>()
        val toVisit = mutableListOf(start to 0)
        val notAllowed = grid.find("#").toSet()
        while (toVisit.isNotEmpty()) {
            val (location, steps) = toVisit.removeFirst()
            if (visited.containsKey(location)) continue

            visited[location] = steps
            for (neighbour in grid.getNeighbours(location, diagonal = false)) {
                if (!notAllowed.contains(neighbour)) {
                    toVisit.add(neighbour to steps + 1)
                }
            }
        }
        return visited
    }
}
