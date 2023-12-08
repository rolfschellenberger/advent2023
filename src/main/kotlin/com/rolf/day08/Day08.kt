package com.rolf.day08

import com.rolf.Day
import com.rolf.util.groupLines
import com.rolf.util.leastCommonMultiple
import com.rolf.util.splitLine

fun main() {
    Day08().run()
}

class Day08 : Day() {
    override fun solve1(lines: List<String>) {
        val (instructions, groupedNodes) = parseNode(lines)

        val position = groupedNodes.getValue("AAA")
        println(
            getSteps(position, instructions, groupedNodes)
        )
    }

    override fun solve2(lines: List<String>) {
        val (instructions, groupedNodes) = parseNode(lines)

        val positions = groupedNodes.filter {
            it.key.endsWith("A")
        }.map {
            it.value
        }.toMutableList()

        val allSteps = positions.map {
            getSteps(it, instructions, groupedNodes).toLong()
        }
        println(leastCommonMultiple(allSteps))
    }

    private fun parseNode(lines: List<String>): Pair<List<String>, Map<String, Node>> {
        val groups = groupLines(lines, "")
        val instructions = splitLine(groups[0].first())
        val nodeLines = groups[1]
        val groupedNodes = nodeLines.map {
            parseNode(it)
        }.associateBy {
            it.from
        }
        return Pair(instructions, groupedNodes)
    }

    private fun parseNode(it: String): Node {
        val (from, other) = splitLine(it, " = ")
        val (left, right) = splitLine(other, ", ").map {
            it.replace("(", "")
                .replace(")", "")
        }
        return Node(from, left, right)
    }

    private fun getSteps(start: Node, instructions: List<String>, groupedNodes: Map<String, Node>): Int {
        var position = start
        var steps = 0
        while (true) {
            if (position.from.endsWith("Z")) break
            for (instruction in instructions) {
                position = when (instruction) {
                    "L" -> groupedNodes.getValue(position.left)
                    "R" -> groupedNodes.getValue(position.right)
                    else -> throw RuntimeException("Unknown instruction $instruction")
                }
                steps++
            }
        }
        return steps
    }
}
