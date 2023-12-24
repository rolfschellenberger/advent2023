package com.rolf.day24

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day24().run()
}

class Day24 : Day() {
    private fun parse(line: String): Hailstone {
        val parts = splitLine(line.replace(",", "").replace("  ", " "), " ")
        return Hailstone(
            parts[0].toLong(),
            parts[1].toLong(),
            parts[2].toLong(),
            parts[4].toLong(),
            parts[5].toLong(),
            parts[6].toLong(),
        )
    }

    override fun solve1(lines: List<String>) {
        val hailstones = lines.map { parse(it) }

        var count = 0
        val range = 200000000000000..400000000000000L
        val visited = mutableSetOf<Pair<Hailstone, Hailstone>>()
        for (a in hailstones) {
            for (b in hailstones) {
                val pair1 = a to b
                val pair2 = b to a
                if (visited.add(pair1) && visited.add(pair2)) {
                    val intersectionA = calculateIntersection(a, b)
                    val intersectionB = calculateIntersection(b, a)
                    if (intersectionA != null && intersectionB != null) {
                        if (isInside(intersectionA, range, range)
                            && isInside(intersectionB, range, range)
                        ) {
                            count++
                        }
                    }
                }
            }
        }
        println(count)
    }

    private fun calculateIntersection(v1: Hailstone, v2: Hailstone): Triple<Double, Double, Double>? {
        // Check if the vectors are parallel (no intersection)
        val determinant = v1.deltaX * v2.deltaY - v2.deltaX * v1.deltaY.toDouble()
        if (determinant == 0.0) {
            return null
        }

        // Solve for time at which x and y coordinates are equal
        val t = ((v2.x - v1.x) * v2.deltaY - (v2.y - v1.y) * v2.deltaX) / determinant
        // We don't want to know the past
        if (t < 0) {
            return null
        }

        // Calculate the intersection point
        return Triple(v1.x + v1.deltaX * t, v1.y + v1.deltaY * t, 0.0)
    }

    private fun isInside(location: Triple<Double, Double, Double>, xRange: LongRange, yRange: LongRange): Boolean {
        return location.first >= xRange.first && location.first <= xRange.last
                && location.second >= yRange.first && location.second <= yRange.last
    }

    override fun solve2(lines: List<String>) {
        val hailstones = lines.map { parse(it) }


    }
}
