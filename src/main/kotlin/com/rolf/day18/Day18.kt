package com.rolf.day18

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLine
import kotlin.math.absoluteValue


fun main() {
    Day18().run()
}

class Day18 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.buildDefault(500, 500, ".")
        val location = grid.center()
        val instructions = lines.map { parse(it) }
        val edges = getEdges(location, instructions)
        edges.forEach {
            grid.set(it, "#")
        }
        val point = Point(grid.center().x + 1, grid.center().y + 1)
        val filled = grid.waterFill(point, setOf("#"), diagonal = false)
        println(grid.count("#") + filled.size)
    }

    private fun parse(line: String): Instruction {
        val (direction, steps, color) = splitLine(line, " ")
        return Instruction(
            direction,
            steps.toInt(),
            color
        )
    }

    private fun getEdges(start: Point, instructions: List<Instruction>): List<Point> {
        var location = start
        val edges = mutableListOf<Point>()
        for (instruction in instructions) {
            for (step in 0 until instruction.steps) {
                val next = when (instruction.direction) {
                    "R" -> location.copy(x = location.x + 1)
                    "L" -> location.copy(x = location.x - 1)
                    "U" -> location.copy(y = location.y - 1)
                    "D" -> location.copy(y = location.y + 1)
                    else -> throw RuntimeException("Incorrect instruction $instruction")
                }
                edges.add(next)
                location = next
            }
        }
        return edges
    }

    override fun solve2(lines: List<String>) {
        val location = Point(0, 0)
        val instructions = lines.map { parse2(it) }
        val edges = getEdges(location, instructions)
        println(calculateShoelaceArea(edges) - (edges.size / 2L) + 1 + edges.size)
    }

    private fun parse2(line: String): Instruction {
        val (_, _, color2) = splitLine(line, " ")
        val color = color2.replace("(", "").replace(")", "")
        val direction = when (color.last()) {
            '0' -> "R"
            '1' -> "D"
            '2' -> "L"
            '3' -> "U"
            else -> throw RuntimeException("Incorrect direction ${color.last()}")
        }
        val steps = color.substring(1, color.length - 1).toInt(radix = 16)
        return Instruction(
            direction,
            steps,
            color
        )
    }

    private fun calculateShoelaceArea(vertices: List<Point>): Long {
        // Use the shoelace formula to calculate the area of the polygon
        var area = 0L

        for (i in 0 until vertices.size - 1) {
            val m = vertices[i]
            val n = vertices[i + 1]
            area += m.x * n.y - n.x * m.y
        }

        return area.absoluteValue / 2
    }
}
