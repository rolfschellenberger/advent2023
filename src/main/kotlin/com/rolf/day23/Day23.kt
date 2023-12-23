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
        val paths = grid.findAllPaths(
            start, end,
            grid.find("#").toSet(),
            diagonal = false,
            customAllowedFunction = this::isMoveAllowed
        )
        println(
            paths.maxOf { it.size }
        )
    }

    private fun isMoveAllowed(grid: Matrix<String>, from: Point, to: Point): Boolean {
        val arrows = setOf(">", "<", "^", "v")

        val fromValue = grid.get(from)
        // We can move to any value when the fromValue is not an arrow
        if (!arrows.contains(fromValue)) {
            return true
        }

        // When the fromValue is an arrow, the to location should be in the right direction
        return when (fromValue) {
            ">" -> grid.getRight(from) == to
            "<" -> grid.getLeft(from) == to
            "^" -> grid.getUp(from) == to
            "v" -> grid.getDown(from) == to
            else -> throw RuntimeException("Incorrect fromValue $fromValue")
        }
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val start = grid.find(".").first()
        val end = grid.find(".").last()
        val notAllowed = grid.find("#").toSet()

        val crossPoints = mutableListOf(start, end)
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
            graph.addEdge(
                distance.first.toString(),
                distance.second.toString(),
                weight = distance.third.toDouble(),
                edgeType = EdgeType.UNDIRECTED
            )
        }

        // Now travel the graph from start till end in all possible ways and look for the path with the highest weight
        val paths = graph.getPaths(start.toString(), end.toString())

        println(
            paths.maxOf { path ->
                path.zipWithNext().sumOf { pair ->
                    val edge = graph.edge(pair.first, pair.second)!!
                    edge.weight.toInt()
                }
            }
        )
    }
}
