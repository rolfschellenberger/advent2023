package com.rolf.day24

import com.rolf.Day
import com.rolf.util.splitLine
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix

fun main() {
    Day24().run()
}

class Day24 : Day() {
    private fun parse(line: String): Hailstone {
        val parts = splitLine(line.replace(",", "").replace("  ", " "), " ")
        return Hailstone(
            parts[0].toDouble(),
            parts[1].toDouble(),
            parts[2].toDouble(),
            parts[4].toDouble(),
            parts[5].toDouble(),
            parts[6].toDouble(),
        )
    }

    override fun solve1(lines: List<String>) {
        val hailstones = lines.map { parse(it) }

        var count = 0
        val range = 200000000000000..400000000000000L
//        val range = 7..27L
        val visited = mutableSetOf<Pair<Hailstone, Hailstone>>()
        for (a in hailstones) {
            for (b in hailstones) {
                val pair1 = a to b
                val pair2 = b to a
                if (visited.add(pair1) && visited.add(pair2)) {
                    val intersectionA = calculateIntersection(a, b)
                    val intersectionB = calculateIntersection(b, a)
                    if (intersectionA != null && intersectionB != null) {
                        println(intersectionA.first.toLong())
                        println(intersectionA.second.toLong())
                        println(intersectionB)
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
            println("0 determinant")
            return null
        }

        // Solve for time at which x and y coordinates are equal
        val t = ((v2.x - v1.x) * v2.deltaY - (v2.y - v1.y) * v2.deltaX) / determinant
        // We don't want to know the past
        if (t < 0) {
            println("past")
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
        println(781390555762385L)
        val hailstones = lines.map { parse(it) }

        println("t >= 0")
        for (hailstone in hailstones) {
            println("x + vx * t == ${hailstone.x.toLong()} + ${hailstone.deltaX.toLong()} * t")
            println("y + vy * t == ${hailstone.y.toLong()} + ${hailstone.deltaY.toLong()} * t")
            println("z + vz * t == ${hailstone.z.toLong()} + ${hailstone.deltaZ.toLong()} * t")
        }
//        val visited = mutableSetOf<Pair<Hailstone, Hailstone>>()
//        for (a in hailstones) {
//            for (b in hailstones) {
//                val pair1 = a to b
//                val pair2 = b to a
//                if (visited.add(pair1) && visited.add(pair2)) {
//                    val intersectionA = calculateIntersection(a, b)
//                    val intersectionB = calculateIntersection(b, a)
//                    if (intersectionA != null && intersectionB != null) {
//
//                    }
//                }
//            }
//        }

//        val intersectingVector = findIntersectingVector(hailstones)
//        println("Intersecting Vector: (${intersectingVector?.joinToString(", ")})")
    }

    fun findIntersectingVector(hailstones: List<Hailstone>): DoubleArray? {
        val n = hailstones.size
        val coefficients = Array(n) { DoubleArray(3) }
        val constants = DoubleArray(n)

        for (i in 0 until n) {
            val hailstone = hailstones[i]

            coefficients[i][0] = -hailstone.deltaX
            coefficients[i][1] = -hailstone.deltaY
            coefficients[i][2] = -hailstone.deltaZ

            constants[i] = hailstone.x * hailstone.deltaX +
                    hailstone.y * hailstone.deltaY +
                    hailstone.z * hailstone.deltaZ
        }

        val coefficientsMatrix: RealMatrix = MatrixUtils.createRealMatrix(coefficients)
        val constantsVector: RealMatrix = MatrixUtils.createColumnRealMatrix(constants)
        val coefficientsInverse: RealMatrix = MatrixUtils.inverse(coefficientsMatrix)

        val resultVector: RealMatrix = coefficientsInverse.multiply(constantsVector)

        return resultVector.getColumn(0)
    }
}
