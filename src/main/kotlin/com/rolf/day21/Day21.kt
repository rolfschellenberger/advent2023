package com.rolf.day21

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines
import kotlin.math.absoluteValue
import kotlin.math.pow

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
//        var s = 1375
//        var count = 1661857L
//        var step = 572668L + 120528
//
//        while (s < 26501365) {
//            s += 262
//            count += step
//            step += 120528
//        }
//
//        println(step)
//        println(count)

    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find("S").first()
        grid.set(start, ".")
        val size = grid.width() - 1

        val maxSteps = 26501365
        var sideSteps = (maxSteps - size / 2) / (size + 1) // 202_300

        // So 26501365 - 65 is leading to the borders
        // 26501300/131 (side lengths) = 202.300
        // So we go 202.300 spaces to the left, right, top and bottom
        // So this generates a space of (2*202.300+1) by (2*202.300+1) tiles
        // The tiles are either completely filled or they are partly filled in different shapes:
        // - 4 x to 1 edge (most top, left, right and bottom one)
        // - all the other edges will have a small triangle or a big triangle
        println(629720570456311L)

        val full = draw(grid, start, Int.MAX_VALUE)
        val right = draw(grid, Point(0, size / 2), size)
        val left = draw(grid, Point(size, size / 2), size)
        val top = draw(grid, Point(size / 2, size), size)
        val bottom = draw(grid, Point(size / 2, 0), size)
        val rightBottomSmall = draw(grid, Point(0, 0), size / 2)
        val rightBottomBig = draw(grid, Point(0, 0), size + size / 2)
        val leftBottomSmall = draw(grid, Point(size, 0), size / 2)
        val leftBottomBig = draw(grid, Point(size, 0), size + size / 2)
        val rightTopSmall = draw(grid, Point(0, size), size / 2)
        val rightTopBig = draw(grid, Point(0, size), size + size / 2)
        val leftTopSmall = draw(grid, Point(size, size), size / 2)
        val leftTopBig = draw(grid, Point(size, size), size + size / 2)

        // What tiles can we reach?
        var reachableTilesEven = 4L * ((sideSteps / 2.0).pow(2).toLong())
        var reachableTilesOdd = 4L * (sideSteps / 2) * (sideSteps / 2 + 1) + 1

        // Now we need to remove some parts where the tile wasn't full
        var sideCount = 0L

        // Replace 1 odd and 1 even full tile with 1 odd big side and 1 even small side
        val sideLength = sideSteps - 2
        reachableTilesEven -= 2 * sideLength
        reachableTilesOdd -= 2 * sideLength
        sideCount += getCount(leftTopBig.second) * sideLength
        sideCount += getCount(leftTopSmall.first) * sideLength
        sideCount += getCount(rightTopBig.second) * sideLength
        sideCount += getCount(rightTopSmall.first) * sideLength
        sideCount += getCount(leftBottomBig.second) * sideLength
        sideCount += getCount(leftBottomSmall.first) * sideLength
        sideCount += getCount(rightBottomBig.second) * sideLength
        sideCount += getCount(rightBottomSmall.first) * sideLength

        // Replace 4 small even tiles
        reachableTilesEven -= 4
        sideCount += getCount(leftTopSmall.first)
        sideCount += getCount(rightTopSmall.first)
        sideCount += getCount(leftBottomSmall.first)
        sideCount += getCount(rightBottomSmall.first)

        // Replace odd tiles by the top, bottom, left and right
        reachableTilesOdd -= 4
        sideCount += getCount(top.second)
        sideCount += getCount(bottom.second)
        sideCount += getCount(left.second)
        sideCount += getCount(right.second)

        println("reachableTilesOdd: $reachableTilesOdd")
        println("reachableTilesEven: $reachableTilesEven")
        println()

        val count = reachableTilesEven * getCount(full.first) +
                reachableTilesOdd * getCount(full.second) +
                sideCount
        println(count)
        return

        // Determine the inner part of the square
        val corners = listOf(
            Point(sideSteps, 0),
            Point(0, sideSteps),
            Point(0, sideSteps + 1),
            Point(-sideSteps - 1, 0)
        )

        fun calculateShoelaceArea(vertices: List<Point>): Long {
            // Use the shoelace formula to calculate the area of the polygon
            var area = 0L

            for (i in 0 until vertices.size - 1) {
                val m = vertices[i]
                val n = vertices[i + 1]
                area += m.x * n.y - n.x * m.y
            }

            return area.absoluteValue / 2
        }

        val innerTiles = calculateShoelaceArea(corners)
        println("innerTiles: $innerTiles")
        println(getCount(full.first) * innerTiles / 2)
        println(getCount(full.second) * innerTiles / 2)
        var distance = 0L
        for (i in 0 until corners.size - 1) {
            val j = (i + 1) % corners.size
            val m = corners[i]
            val n = corners[j]
            distance += m.distance(n)
        }
        println("distance: $distance")
        println(getCount(full.first) * distance)

        println(getCount(full.first))
        println(629720570456311 / 7712) // 81_654_638_285
        println(sideSteps.toLong() * (sideSteps / 2))

        println(
            calculateShoelaceArea(
                listOf(
                    Point(0, -4),
                    Point(-4, 0),
                    Point(0, 4),
                    Point(4, 0),
                )
            )
        )
        println(
            calculateShoelaceArea(
                listOf(
                    Point(4, 0),
                    Point(8, 4),
                    Point(4, 8),
                    Point(0, 4),
                )
            )
        )

        println(
            (getCount(full.first) * innerTiles / 2) +
                    (getCount(full.second) * innerTiles / 2) +
                    getCount(full.first) * distance
        )


        val rowCount = sideSteps // Rows into 1 direction: top or bottom (and multiplied by 2, since it is mirrored)
        val counts = mutableMapOf<String, Long>()
        var bigTile = true

        var tiles = 0L
        for (row in 0..rowCount) {
            for (column in 0..sideSteps) {
                if (row == 0) {
                    if (column == 0) tiles++
                    else tiles += 2
                } else {
                    if (column == 0) tiles += 2
                    else tiles += 4
                }
                sideSteps -= 2
            }
        }
        println(tiles)
        println(629720570456311 / tiles) // 1_556_398_947
        println(getCount(full.first).toLong() * tiles)



        return

        for (row in 0..rowCount) {
            println("Row: $row")
            var inverse = row % 2 == 1
            // Top row
            if (row == rowCount) {
                increment(counts, "top-$inverse")
                increment(counts, "bottom-$inverse")
            }
            for (column in 0..sideSteps) {
                when (column) {
                    // When column == 0, only count 1 tile
                    0 -> {
                        increment(counts, "full-$inverse")
                    }

                    // The last tile on each side
                    sideSteps -> {
                        // On the start row we have the left and right tiles
                        if (row == 0) {
                            increment(counts, "left-$inverse")
                            increment(counts, "right-$inverse")
                        }
                        // On the other rows, we alter between big and small tiles
                        else {
                            increment(counts, "leftTop-$bigTile-$inverse")
                            increment(counts, "rightTop-$bigTile-$inverse")
                            increment(counts, "leftBottom-$bigTile-$inverse")
                            increment(counts, "rightBottom-$bigTile-$inverse")
                            bigTile = !bigTile
                        }
                    }

                    else -> {
                        increment(counts, "full-$inverse")
                        increment(counts, "full-$inverse")
                    }
                }
                // Alter every step
                inverse = !inverse
            }
            // Every row, we have 2 less fields to visit
            sideSteps -= 2
        }

        counts.forEach {
            println(it)
        }

        val countsManual = mapOf(
            "full-false" to 10231373076,
            "full-true" to 10231373075,
            "left-false" to 1,
            "right-false" to 1,
            "leftTop-true-true" to 50575,
            "rightTop-true-true" to 50575,
            "leftBottom-true-true" to 50575,
            "rightBottom-true-true" to 50575,
            "leftTop-false-false" to 50574,
            "rightTop-false-false" to 50574,
            "leftBottom-false-false" to 50574,
            "rightBottom-false-false" to 50574,
            "top-false" to 1
        )

        var sum = 0L
        for ((key, value) in counts) {
            sum += when (key) {
                "full-false" -> getCount(full.first)
                "full-true" -> getCount(full.second)
                "left-false" -> getCount(left.first)
                "left-true" -> getCount(left.second)
                "right-false" -> getCount(right.first)
                "right-true" -> getCount(right.second)
                "leftTop-false-false" -> getCount(leftTopSmall.first)
                "leftTop-true-false" -> getCount(leftTopBig.first)
                "leftTop-false-true" -> getCount(leftTopSmall.second)
                "leftTop-true-true" -> getCount(leftTopBig.second)
                "rightTop-false-false" -> getCount(rightTopSmall.first)
                "rightTop-true-false" -> getCount(rightTopBig.first)
                "rightTop-false-true" -> getCount(rightTopSmall.second)
                "rightTop-true-true" -> getCount(rightTopBig.second)
                "leftBottom-false-false" -> getCount(leftBottomSmall.first)
                "leftBottom-true-false" -> getCount(leftBottomBig.first)
                "leftBottom-false-true" -> getCount(leftBottomSmall.second)
                "leftBottom-true-true" -> getCount(leftBottomBig.second)
                "rightBottom-false-false" -> getCount(rightBottomSmall.first)
                "rightBottom-true-false" -> getCount(rightBottomBig.first)
                "rightBottom-false-true" -> getCount(rightBottomSmall.second)
                "rightBottom-true-true" -> getCount(rightBottomBig.second)
                "top-false" -> getCount(top.first)
                "top-true" -> getCount(top.second)
                "bottom-false" -> getCount(bottom.first)
                "bottom-true" -> getCount(bottom.second)
                else -> throw RuntimeException("Ohoh! $key")
            } * value
        }
        println(sum)
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

    private fun increment(counts: MutableMap<String, Long>, key: String) {
        val value = counts.getOrDefault(key, 0L)
        counts[key] = value + 1
    }

    private fun getCount(grid: MatrixString): Int {
        return grid.count("O")
    }
}
