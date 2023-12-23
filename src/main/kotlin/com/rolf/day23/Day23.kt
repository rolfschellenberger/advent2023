package com.rolf.day23

import com.rolf.Day
import com.rolf.util.*

fun main() {
    Day23().run()
}

class Day23 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find(".").first()
        val end = grid.find(".").last()
        val notAllowed = grid.find("#").toSet()
        val path = grid.findLongestPath(start, end, notAllowed, false)
        println(path.size - 1)
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find(".").first()
        val end = grid.find(".").last()
        val notAllowed = grid.find("#").toSet()

        val crossPoints = mutableSetOf<Point>(start, end)
        for (point in grid.allPoints()) {
            if (!notAllowed.contains(point)) {
                val neighbours = grid.getNeighbours(point, diagonal = false).filter { !notAllowed.contains(it) }
                if (neighbours.size > 2) {
                    crossPoints.add(point)
                }
            }
        }

        // Distances from each cross point to another
        val distances = mutableListOf<Triple<Point, Point, Int>>()
        for (crossPoint in crossPoints) {
            for (otherCrossPoint in crossPoints) {
                // We cannot walk on any cross point except the start and end
                val crossNotAllowed = notAllowed + crossPoints - crossPoint - otherCrossPoint
                val path = grid.findPath(crossPoint, otherCrossPoint, crossNotAllowed)
                // We find a path from a cross point to another
                if (path.isNotEmpty()) {
                    distances.add(Triple(crossPoint, otherCrossPoint, path.size))
                }
            }
        }

        // Build a graph from the directions
        val graph = Graph<Point>()
        for (distance in distances) {
            graph.addVertex(Vertex(distance.first.toString(), distance.first))
            graph.addVertex(Vertex(distance.second.toString(), distance.second))
            graph.addEdge(distance.first.toString(), distance.second.toString(), weight = distance.third.toDouble())
        }

        // Now travel the graph from start till end in all possible ways and look for the path with the highest weight
        val paths = mutableListOf(listOf(start))
        val results = mutableListOf<List<Point>>()
        while (paths.isNotEmpty()) {
            val path = paths.removeLast()
            val pathEnd = path.last()
            if (pathEnd == end) {
                results.add(path)
                continue
            }

            // Check the next options
            val neighbours = graph.neighbours(pathEnd.toString())
            for (neighbour in neighbours) {
                val point = graph.getVertex(neighbour)!!.data!!
                if (!path.contains(point)) {
                    paths.add(path + point)
                }
            }
        }

        var maxSteps = 0
        for (result in results) {
            var steps = 0
            for (i in 0 until result.size - 1) {
                val from = result[i]
                val to = result[i + 1]
                val edge = graph.edge(from.toString(), to.toString())!!
                steps += edge.weight.toInt()
            }
            maxSteps = maxOf(maxSteps, steps)
        }

        println(maxSteps)
    }

    fun bla(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        println(grid)
        val start = grid.find(".").first()
        val end = grid.find(".").last()
        println(start)
        println(end)
        val notAllowed = grid.find("#").toSet()
        val paths2 = grid.findAllPaths2(start, end, notAllowed, false)
        println(paths2.size)
        println(
            paths2.maxOf { it.size } - 1
        )
        return

        val paths = grid.findAllPaths(start, end, notAllowed, false)
        println(paths.size)
        for (path in paths) {
            val copy = grid.copy()
            path.locations.forEach {
                copy.set(it, "O")
            }
            println(copy)
            println(path.locations.size)
            println()
        }
        println(
            paths.maxOf { it.locations.size }
        )
    }
}
