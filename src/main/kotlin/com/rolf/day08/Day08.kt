package com.rolf.day08

import com.rolf.Day
import com.rolf.util.greatestCommonDivisor
import com.rolf.util.groupLines
import com.rolf.util.splitLine

fun main() {
    Day08().run()
}

class Day08 : Day() {
    override fun solve1(lines: List<String>) {
        val groups = groupLines(lines, "")
        val instructions = splitLine(groups[0].first())
        val nodeLines = groups[1]
//        println(instructions.size)
        val nodes = nodeLines.map {
            parse(it)
        }
//        println(nodes)
        val groupedNodes = nodes.groupBy {
            it.from
        }

        var position = groupedNodes.getValue("AAA").first()
        var steps = 0
        while (true) {
            if (position.from == "ZZZ") break
            for (instruction in instructions) {
                when (instruction) {
                    "L" -> position = groupedNodes.getValue(position.left).first()
                    "R" -> position = groupedNodes.getValue(position.right).first()
                    else -> throw RuntimeException("Unknown instruction $instruction")
                }
                steps++
            }
        }
        println(steps)
    }

    private fun parse(it: String): Node {
        val (from, other) = splitLine(it, " = ")
        val (left, right) = splitLine(other, ", ").map {
            it.replace("(", "")
                .replace(")", "")
        }
        return Node(from, left, right)
    }

    override fun solve2(lines: List<String>) {
        val groups = groupLines(lines, "")
        val instructions = splitLine(groups[0].first())
        val nodeLines = groups[1]
//        println(instructions.size)
        val nodes = nodeLines.map {
            parse(it)
        }
        val groupedNodes = nodes.groupBy {
            it.from
        }

        val positions = nodes.filter {
            it.from.endsWith("A")
        }.toMutableList()
//        println(positions)

        val allSteps = mutableListOf<Long>()
        for (position in positions) {
            val steps = getSteps(position, instructions, groupedNodes)
//            println(position)
//            println(steps)
            allSteps.add(steps.toLong())
        }
        println(allSteps)
        println(greatestCommonDivisor(23147, 20803))
        println(allSteps.reduce { a, b -> a * b })
    }

    fun getSteps(start: Node, instructions: List<String>, groupedNodes: Map<String, List<Node>>): Int {
        var position = start
        var steps = 0
        while (true) {
            if (position.from.endsWith("Z")) break
            for (instruction in instructions) {
                when (instruction) {
                    "L" -> position = groupedNodes.getValue(position.left).first()
                    "R" -> position = groupedNodes.getValue(position.right).first()
                    else -> throw RuntimeException("Unknown instruction $instruction")
                }
                steps++
            }
        }
        return steps
    }
}
