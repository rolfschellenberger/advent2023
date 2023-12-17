package com.rolf.day17

import com.rolf.Day
import com.rolf.util.Direction
import com.rolf.util.MatrixInt
import com.rolf.util.splitLines
import java.util.*

fun main() {
    Day17().run()
}

class Day17 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            solve(lines, this::getDestinations1)
        )
    }

    override fun solve2(lines: List<String>) {
        println(
            solve(lines, this::getDestinations2)
        )
    }

    private fun solve(lines: List<String>, getDestinations: (grid: MatrixInt, current: Step) -> List<Step>): Int {
        val grid = MatrixInt.build(splitLines(lines))
        val distances = mutableMapOf<Step, Int>()
        val start = grid.topLeft()
        val end = grid.bottomRight()

        // Start top left in both directions and 0 distance
        val toInspectSorted = PriorityQueue<Distance>()
        toInspectSorted.add(Distance(Step(start, Direction.SOUTH, 0), 0))
        toInspectSorted.add(Distance(Step(start, Direction.EAST, 0), 0))
        for (i in toInspectSorted) {
            distances[i.step] = 0
        }

        // Inspect the priority queue
        while (toInspectSorted.isNotEmpty()) {
            val currentDistance = toInspectSorted.remove()
            val current = currentDistance.step
            val destinations = getDestinations(grid, current)

            // Now take the current location score + the score for each destination and update the distance grid
            val currentScore = distances[current]!!
            for (destination in destinations) {
                val oldScore = distances[destination] ?: Int.MAX_VALUE
                val newScore = grid.get(destination.location) + currentScore
                if (newScore < oldScore) {
                    distances[destination] = newScore
                    toInspectSorted.add(Distance(destination, newScore))
                }
            }
        }

        // Print the distance to the end location
        return distances.filter {
            it.key.location == end
        }.minOf { it.value }
    }

    private fun getDestinations1(grid: MatrixInt, current: Step): List<Step> {
        val result = mutableListOf<Step?>()
        // Straight when steps < 3
        if (current.steps < 3 - 1) {
            result.add(getNextDestination(grid, current))
        }
        result.add(getLeftDestination(grid, current))
        result.add(getRightDestination(grid, current))
        return result.filterNotNull()
    }

    private fun getDestinations2(grid: MatrixInt, current: Step): List<Step> {
        val result = mutableListOf<Step?>()
        // Straight when steps < 10
        if (current.steps < 10 - 1) {
            result.add(getNextDestination(grid, current))
        }
        // We must be 4 steps away
        if (current.steps >= 3) {
            result.add(getLeftDestination(grid, current))
            result.add(getRightDestination(grid, current))
        }
        return result.filterNotNull()
    }

    private fun getNextDestination(grid: MatrixInt, current: Step): Step? {
        val next = grid.getForward(current.location, current.direction) ?: return null
        return Step(next, current.direction, current.steps + 1)
    }

    private fun getLeftDestination(grid: MatrixInt, current: Step): Step? {
        val leftDirection = current.direction.left()
        val left = grid.getForward(current.location, leftDirection) ?: return null
        return Step(left, leftDirection, 0)
    }

    private fun getRightDestination(grid: MatrixInt, current: Step): Step? {
        val rightDirection = current.direction.right()
        val right = grid.getForward(current.location, rightDirection) ?: return null
        return Step(right, rightDirection, 0)
    }
}
