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

        val lights = mutableSetOf<Light>()
        lights.add(Light(start, direction))

        val path2 = followLight2(grid, Light(start, direction))
        val uniqueLocations = visited.map {
            it.location
        }.toSet()
        println(uniqueLocations.size)
    }

    private val visited = mutableSetOf<Light>()

    private fun followLight2(grid: MatrixString, light: Light): List<Light> {
        visited.add(light)
        val next = getNext(grid, light)
        return next.filter {
            visited.add(it)
        }.map {
            followLight2(grid, it)
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

    private fun followLight(grid: MatrixString, light: Light): Pair<List<Light>, List<Light>> {
        val next = getNext(grid, light)

        var current = light
        val path = mutableListOf<Light>()
        val split = mutableListOf<Light>()
        while (current != null) {
            val next = getNext(grid, current)

        }
        return path to split
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.topLeft()
        val direction = Direction.EAST

        val rows = setOf(0, grid.height() - 1)
        val columns = setOf(0, grid.width() - 1)
        var maxValue = 0

        val topEdge = grid.allPoints().filter { it.y == 0 }
        val bottomEdge = grid.allPoints().filter { it.y == grid.height() - 1 }
        val leftEdge = grid.allPoints().filter { it.x == 0 }
        val rightEdge = grid.allPoints().filter { it.x == grid.width() - 1 }

        for (point in topEdge) {
            val direction = Direction.SOUTH
            visited.clear()
            followLight2(grid, Light(point, direction))
            val uniqueLocations = visited.map {
                it.location
            }.toSet()
            val value = uniqueLocations.size
            maxValue = maxOf(maxValue, value)
        }
        for (point in bottomEdge) {
            val direction = Direction.NORTH
            visited.clear()
            followLight2(grid, Light(point, direction))
            val uniqueLocations = visited.map {
                it.location
            }.toSet()
            val value = uniqueLocations.size
            maxValue = maxOf(maxValue, value)
        }
        for (point in leftEdge) {
            val direction = Direction.EAST
            visited.clear()
            followLight2(grid, Light(point, direction))
            val uniqueLocations = visited.map {
                it.location
            }.toSet()
            val value = uniqueLocations.size
            maxValue = maxOf(maxValue, value)
        }
        for (point in rightEdge) {
            val direction = Direction.WEST
            visited.clear()
            followLight2(grid, Light(point, direction))
            val uniqueLocations = visited.map {
                it.location
            }.toSet()
            val value = uniqueLocations.size
            maxValue = maxOf(maxValue, value)
        }


        println(maxValue)
    }
}
