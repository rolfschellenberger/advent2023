package com.rolf.day16

import com.rolf.Day
import com.rolf.util.Direction
import com.rolf.util.MatrixString
import com.rolf.util.splitLines

fun main() {
    Day16().run()
}

class Day16 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.topLeft()
        val direction = Direction.EAST
        val light = Light(start, direction)
        println(getEnergized(grid, light))
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val topEdge = grid.allPoints().filter { it.y == 0 }
            .map { Light(it, Direction.SOUTH) }
        val bottomEdge = grid.allPoints().filter { it.y == grid.height() - 1 }
            .map { Light(it, Direction.NORTH) }
        val leftEdge = grid.allPoints().filter { it.x == 0 }
            .map { Light(it, Direction.EAST) }
        val rightEdge = grid.allPoints().filter { it.x == grid.width() - 1 }
            .map { Light(it, Direction.WEST) }
        val startingPoints = topEdge + bottomEdge + leftEdge + rightEdge

        println(
            startingPoints.maxOfOrNull {
                getEnergized(grid, it)
            }
        )
    }

    private val visited = mutableSetOf<Light>()

    private fun getEnergized(grid: MatrixString, light: Light): Int {
        visited.clear()
        followLight(grid, light)
        return visited.map { it.location }.toSet().size
    }

    private fun followLight(grid: MatrixString, light: Light): List<Light> {
        visited.add(light)
        val next = getNext(grid, light)
        return next.filter {
            visited.add(it)
        }.map {
            followLight(grid, it)
        }.flatten()
    }

    private fun getNext(grid: MatrixString, light: Light): List<Light> {
        val value = grid.get(light.location)
        val next = when (value) {
            "." -> {
                listOf(
                    grid.getForward(light.location, light.direction) to light.direction
                )
            }

            "\\" -> {
                val direction = when (light.direction) {
                    Direction.NORTH -> Direction.WEST
                    Direction.EAST -> Direction.SOUTH
                    Direction.SOUTH -> Direction.EAST
                    Direction.WEST -> Direction.NORTH
                }
                listOf(
                    grid.getForward(light.location, direction) to direction
                )
            }

            "/" -> {
                val direction = when (light.direction) {
                    Direction.NORTH -> Direction.EAST
                    Direction.EAST -> Direction.NORTH
                    Direction.SOUTH -> Direction.WEST
                    Direction.WEST -> Direction.SOUTH
                }
                listOf(
                    grid.getForward(light.location, direction) to direction
                )
            }

            "|" -> {
                val directions = when (light.direction) {
                    Direction.NORTH -> listOf(Direction.NORTH)
                    Direction.EAST -> listOf(Direction.NORTH, Direction.SOUTH)
                    Direction.SOUTH -> listOf(Direction.SOUTH)
                    Direction.WEST -> listOf(Direction.NORTH, Direction.SOUTH)
                }
                directions.map { direction ->
                    grid.getForward(light.location, direction) to direction
                }
            }

            "-" -> {
                val directions = when (light.direction) {
                    Direction.NORTH -> listOf(Direction.EAST, Direction.WEST)
                    Direction.EAST -> listOf(Direction.EAST)
                    Direction.SOUTH -> listOf(Direction.EAST, Direction.WEST)
                    Direction.WEST -> listOf(Direction.WEST)
                }
                directions.map { direction ->
                    grid.getForward(light.location, direction) to direction
                }
            }

            else -> throw RuntimeException("Invalid value: $value")
        }.filter {
            it.first != null
        }.map {
            Light(it.first!!, it.second)
        }
        return next
    }
}
