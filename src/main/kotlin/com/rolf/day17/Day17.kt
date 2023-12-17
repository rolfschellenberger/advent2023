package com.rolf.day17

import com.rolf.Day
import com.rolf.util.Direction
import com.rolf.util.MatrixInt
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day17().run()
}

class Day17 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixInt.build(splitLines(lines))
//        println(grid)

        val distances = mutableMapOf<Step, Int>()
        val start = grid.topLeft()
        val end = grid.bottomRight()

        for (i in 0 until 100) {
            println(i)
            val visited = mutableSetOf<Step>()
            val toInspect = mutableListOf(
                Step(start, Direction.SOUTH, 0),
                Step(start, Direction.EAST, 0)
            )
//        distances[start] = 0
            for (i in toInspect) {
                distances[i] = 0
            }
            while (toInspect.isNotEmpty()) {
                // FIXME: Get the location with the lowest distance
//                val current = getCurrent(toInspect, distances)
                val current = toInspect.removeFirst()
                if (visited.add(current)) {
                    val destinations = getDestinations(grid, current)
                    toInspect.addAll(destinations - visited)
//                println("toInspect: ${toInspect.size}")
//                println("visited: ${visited.size}")
//                println("destinations: $destinations")
                    // Now take the current location score + the score for each destination and update the distance grid
                    val currentScore = distances[current]!!
//                println("currentScore: $currentScore")
                    for (destination in destinations) {
                        val score = getScore(grid, current.location, destination.location) + currentScore
//                        if (destination.location == Point(11, 5) &&
//                            destination.direction == Direction.SOUTH
//                        ) {
//                            println(destination)
//                        }
//                    println("to: $destination, $score")
                        if (distances[destination] == null) {
                            distances[destination] = score
                        } else {
                            distances[destination] = minOf(distances[destination]!!, score)
                        }
                    }
//                visited.add(current)
                }
            }
//        println(distances)
//        distances.forEach {
//            println(it)
//        }
        }

        val values = grid.copy()
        for (point in grid.allPoints()) {
            values.set(point, Int.MAX_VALUE)
        }
        for (distance in distances) {
            val v = distance.value
            if (v < values.get(distance.key.location)) {
                values.set(distance.key.location, distance.value)
            }
        }
//        println(values.toString(",", "\n"))
//        println(distances.filter {
//            it.key.location == end
//        })
        println(distances.filter {
            it.key.location == end
        }.minOf { it.value })


//        println("Trace")
//        println(distances[Step(Point(0, 0), Direction.EAST, 0)])
//        println(distances[Step(Point(1, 0), Direction.EAST, 1)])
//        println(distances[Step(Point(2, 0), Direction.EAST, 2)])
//        println(distances[Step(Point(2, 1), Direction.SOUTH, 0)])
//        println(distances[Step(Point(3, 1), Direction.EAST, 0)])
//        println(distances[Step(Point(4, 1), Direction.EAST, 1)])
//        println(distances[Step(Point(5, 1), Direction.EAST, 2)])
//        println(distances[Step(Point(5, 0), Direction.NORTH, 0)])
//        println(distances[Step(Point(6, 0), Direction.EAST, 0)])
//        println(distances[Step(Point(7, 0), Direction.EAST, 1)])
//
//        println(distances[Step(Point(8, 0), Direction.EAST, 2)])
//        println(distances[Step(Point(8, 1), Direction.SOUTH, 0)])
//        println(distances[Step(Point(8, 2), Direction.SOUTH, 1)])
//        println(distances[Step(Point(9, 2), Direction.EAST, 0)])
//        println(distances[Step(Point(10, 2), Direction.EAST, 1)])
//        println(distances[Step(Point(10, 3), Direction.SOUTH, 0)])
//        println(distances[Step(Point(10, 4), Direction.SOUTH, 1)])
//        println(distances[Step(Point(11, 4), Direction.EAST, 0)])
//
//        println(distances[Step(Point(11, 5), Direction.SOUTH, 0)])

//        println(distances[Step(Point(11, 6), Direction.SOUTH, 1)])
//        println(distances[Step(Point(11, 7), Direction.SOUTH, 2)])
//        println(distances[Step(Point(12, 7), Direction.EAST, 0)])
//        println(distances[Step(Point(12, 8), Direction.SOUTH, 0)])
//        println(distances[Step(Point(12, 9), Direction.SOUTH, 1)])
//        println(distances[Step(Point(12, 10), Direction.SOUTH, 2)])
//        println(distances[Step(Point(11, 10), Direction.WEST, 0)])
//        println(distances[Step(Point(11, 11), Direction.SOUTH, 0)])
//        println(distances[Step(Point(11, 12), Direction.SOUTH, 1)])
//        println(distances[Step(Point(12, 12), Direction.EAST, 0)])

        // 1132 too high
        // 1133 too high
        // 1136 wrong
    }

    private fun getScore(grid: MatrixInt, from: Point, to: Point): Int {
        val path = grid.findPath(from, to)
        return path.sumOf {
            grid.get(it)
        }
    }

    private fun getDestinations(grid: MatrixInt, current: Step): List<Step> {
        val result = mutableListOf<Step>()
//        if (current.location == Point(8, 0) &&
//            current.direction == Direction.EAST &&
//            current.steps == 2) {
//            println(current)
//        }
        // So we have a few options to go from each location:
        // 1. Straight when steps < 3
        if (current.steps < 2) {
            val next = grid.getForward(current.location, current.direction)
            if (next != null) {
                result.add(Step(next, current.direction, current.steps + 1))
            }
        }
        // 2. Left
        val leftDirection = current.direction.left()
        val left = grid.getForward(current.location, leftDirection)
        if (left != null) {
            result.add(Step(left, leftDirection, 0))
        }
        // 3. Right
        val rightDirection = current.direction.right()
        val right = grid.getForward(current.location, rightDirection)
        if (right != null) {
            result.add(Step(right, rightDirection, 0))
        }
        return result
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixInt.build(splitLines(lines))
//        println(grid)

        val distances = mutableMapOf<Step, Int>()
        val start = grid.topLeft()
        val end = grid.bottomRight()

        for (i in 0 until 100) {
            println(i)
            val visited = mutableSetOf<Step>()
            val toInspect = mutableListOf(
                Step(start, Direction.SOUTH, 0),
                Step(start, Direction.EAST, 0)
            )
//        distances[start] = 0
            for (i in toInspect) {
                distances[i] = 0
            }
            while (toInspect.isNotEmpty()) {
                val current = toInspect.removeFirst()
                if (visited.add(current)) {
                    val destinations = getDestinations2(grid, current)
                    toInspect.addAll(destinations - visited)
//                println("toInspect: ${toInspect.size}")
//                println("visited: ${visited.size}")
//                println("destinations: $destinations")
                    // Now take the current location score + the score for each destination and update the distance grid
                    val currentScore = distances[current]!!
//                println("currentScore: $currentScore")
                    for (destination in destinations) {
                        val score = getScore(grid, current.location, destination.location) + currentScore
//                        if (destination.location == Point(11, 5) &&
//                            destination.direction == Direction.SOUTH
//                        ) {
//                            println(destination)
//                        }
//                    println("to: $destination, $score")
                        if (distances[destination] == null) {
                            distances[destination] = score
                        } else {
                            distances[destination] = minOf(distances[destination]!!, score)
                        }
                    }
                }
            }
            println(distances.filter {
                it.key.location == end
            }.minOf { it.value })
        }
        println(distances.filter {
            it.key.location == end
        }.minOf { it.value })
    }

    private fun getDestinations2(grid: MatrixInt, current: Step): List<Step> {
        val result = mutableListOf<Step>()
//        if (current.location == Point(8, 0) &&
//            current.direction == Direction.EAST &&
//            current.steps == 2) {
//            println(current)
//        }
        // So we have a few options to go from each location:
        // 1. Straight when steps < 10
        if (current.steps < 9) {
            val next = grid.getForward(current.location, current.direction)
            if (next != null) {
                result.add(Step(next, current.direction, current.steps + 1))
            }
        }
        // 2. Left
        if (current.steps >= 3) {
            val leftDirection = current.direction.left()
            val left = grid.getForward(current.location, leftDirection)
            if (left != null) {
                result.add(Step(left, leftDirection, 0))
            }
            // 3. Right
            val rightDirection = current.direction.right()
            val right = grid.getForward(current.location, rightDirection)
            if (right != null) {
                result.add(Step(right, rightDirection, 0))
            }
        }
        return result
    }

//    fun findLowestSumPath(grid: List<List<Int>>): List<Pair<Int, Int>> {
//        val rows = grid.size
//        val cols = grid[0].size
//
//        // Create a 2D array to store the minimum sum values
//        val dp = Array(rows) { Array(cols) { Int.MAX_VALUE } }
//
//        // Initialize the starting point
//        dp[0][0] = grid[0][0]
//
//        // Update the dp array
//        for (row in 0 until rows) {
//            for (col in 0 until cols) {
//                for (i in 1..3) {
//                    if (row + i < rows) {
//                        dp[row + i][col] = minOf(dp[row + i][col], dp[row][col] + grid[row + i][col])
//                    }
//                    if (col + i < cols) {
//                        dp[row][col + i] = minOf(dp[row][col + i], dp[row][col] + grid[row][col + i])
//                    }
//                }
//            }
//        }
//
//        // Reconstruct the path
//        val path = mutableListOf<Pair<Int, Int>>()
//        var currentRow = rows - 1
//        var currentCol = cols - 1
//        println(dp.map { it.toList() }.toList())
//
//        while (currentRow >= 0 && currentCol >= 0) {
//            path.add(Pair(currentRow, currentCol))
//            val minNeighbor = minOf(
//                dp.getOrElse(currentRow - 1) { emptyArray() }.getOrElse(currentCol) { Int.MAX_VALUE },
//                dp.getOrElse(currentRow) { emptyArray() }.getOrElse(currentCol - 1) { Int.MAX_VALUE },
//                dp.getOrElse(currentRow - 2) { emptyArray() }.getOrElse(currentCol) { Int.MAX_VALUE },
//                dp.getOrElse(currentRow) { emptyArray() }.getOrElse(currentCol - 2) { Int.MAX_VALUE }
//            )
//            if (minNeighbor == Int.MAX_VALUE) {
//                break
//            } else if (currentRow > 0 && dp[currentRow - 1][currentCol] == minNeighbor) {
//                currentRow--
//            } else if (currentCol > 0 && dp[currentRow][currentCol - 1] == minNeighbor) {
//                currentCol--
//            } else if (currentRow > 1 && dp[currentRow - 2][currentCol] == minNeighbor) {
//                currentRow -= 2
//            } else if (currentCol > 1 && dp[currentRow][currentCol - 2] == minNeighbor) {
//                currentCol -= 2
//            }
//        }
//
//        return path.reversed()
//    }

    fun findLowestSumPath(grid: List<List<Int>>): List<Pair<Int, Int>> {
        val rows = grid.size
        val cols = grid[0].size

        // Create a 2D array to store the minimum sum values
        val dp = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }

        // Initialize the starting point
        dp[0][0] = grid[0][0]

        // Update the dp array
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                for (i in 1..3) {
                    if (row + i < rows) {
                        dp[row + i][col] = minOf(dp[row + i][col], dp[row][col] + grid[row + i][col])
                    }
                    if (col + i < cols) {
                        dp[row][col + i] = minOf(dp[row][col + i], dp[row][col] + grid[row][col + i])
                    }
                }
            }
        }

        // Reconstruct the path
        val path = mutableListOf<Pair<Int, Int>>()
        var currentRow = rows - 1
        var currentCol = cols - 1

        while (currentRow > 0 || currentCol > 0) {
            path.add(Pair(currentRow, currentCol))

            var minNeighbor = Int.MAX_VALUE
            var nextRow = currentRow
            var nextCol = currentCol

            for (i in 1..3) {
                if (currentRow >= i) {
                    val value = dp[currentRow - i][currentCol]
                    if (value < minNeighbor) {
                        minNeighbor = value
                        nextRow = currentRow - i
                        nextCol = currentCol
                    }
                }

                if (currentCol >= i) {
                    val value = dp[currentRow][currentCol - i]
                    if (value < minNeighbor) {
                        minNeighbor = value
                        nextRow = currentRow
                        nextCol = currentCol - i
                    }
                }
            }

            currentRow = nextRow
            currentCol = nextCol
        }

        path.add(Pair(0, 0))
        return path.reversed()
    }
}
