package com.rolf.day17

import com.rolf.Day
import com.rolf.util.*

fun main() {
    Day17().run()
}

class Day17 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixInt.build(splitLines(lines))
        println(grid)
//        val graph = Graph<Int>()
//        graph.shortestPathAndWeight()
//        val path = findLowestSumPath(grid.input)
//        println(path)

//        val grid2 = listOf(
//            listOf(1, 2, 3, 4),
//            listOf(5, 6, 7, 8),
//            listOf(9, 10, 11, 12),
//            listOf(13, 14, 15, 16)
//        )
//
//        val path2 = findLowestSumPath(grid2)
//        println("Lowest sum path: $path2")


        val distances = mutableMapOf<Point, Int>()
        val start = grid.topLeft()
        val end = grid.bottomRight()
        val visited = mutableSetOf<Point>()
        distances[start] = 0
        val toInspect = mutableListOf(start)
        val direction = Direction.EAST
        while (toInspect.isNotEmpty()) {
            val current = toInspect.removeFirst()
            val destinations = getDestinations(grid, current, direction)
            toInspect.addAll(destinations - visited)
            println("destinations: $destinations")
            // Now take the current location score + the score for each destination and update the distance grid
            val currentScore = distances[current]!!
            println("currentScore: $currentScore")
            for (destination in destinations) {
                val score = getScore(grid, current, destination) + currentScore
                println("to: $destination, $score")
                if (distances[destination] == null) {
                    distances[destination] = score
                } else {
                    distances[destination] = minOf(distances[destination]!!, score)
                }
            }
            visited.add(current)
        }
        println(distances)
    }

    private fun getScore(grid: MatrixInt, from: Point, to: Point): Int {
        val path = grid.findPath(from, to)
        return path.sumOf {
            grid.get(it)
        }
    }

    private fun getDestinations(grid: MatrixInt, current: Point, direction: Direction): List<Point> {
        // So we have a few options to go from each location:
        // 1. Straight into 1, 2 or 3 steps
        val next1 = grid.getForward(current, direction)
        val next2 = if (next1 != null) grid.getForward(next1, direction) else null
        val next3 = if (next2 != null) grid.getForward(next2, direction) else null
        // 2. Left
        val left1 = grid.getForward(current, direction.left())
        val left2 = if (left1 != null) grid.getForward(left1, direction.left()) else null
        val left3 = if (left2 != null) grid.getForward(left2, direction.left()) else null
        // 3. Right
        val right1 = grid.getForward(current, direction.right())
        val right2 = if (right1 != null) grid.getForward(right1, direction.right()) else null
        val right3 = if (right2 != null) grid.getForward(right2, direction.right()) else null

        return listOfNotNull(
            next1,
            next2,
            next3,
            left1,
            left2,
            left3,
            right1,
            right2,
            right3
        )
    }

    override fun solve2(lines: List<String>) {
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
