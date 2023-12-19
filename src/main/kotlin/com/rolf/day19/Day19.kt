package com.rolf.day19

import com.rolf.Day
import com.rolf.util.groupLines
import com.rolf.util.splitLine

fun main() {
    Day19().run()
}

class Day19 : Day() {
    override fun solve1(lines: List<String>) {
        val (workflows, parts) = groupLines(lines, "")
        val workflows2 = workflows.map { parseWorkflow(it) }
        val workflowMap = workflows2.associateBy { it.name }
        val parts2 = parts.map { parsePart(it) }
        println(
            parts2.filter {
                isAccepted(it, workflowMap)
            }.sumOf {
                it.sum()
            }
        )
    }

    private fun isAccepted(part: Part, workflowMap: Map<String, Workflow>): Boolean {
        var workflow = workflowMap.getValue("in")
        while (true) {
            val next = workflow.getNext(part)
            if (next == "A") return true
            if (next == "R") return false
            workflow = workflowMap.getValue(next)
        }
    }

    private fun parsePart(input: String): Part {
        val (x1, m1, a1, s1) = splitLine(
            input.replace("{", "").replace("}", ""),
            ","
        )
        return Part(
            splitLine(x1, "=")[1].toLong(),
            splitLine(m1, "=")[1].toLong(),
            splitLine(a1, "=")[1].toLong(),
            splitLine(s1, "=")[1].toLong(),
        )
    }

    private fun parseWorkflow(input: String): Workflow {
        val (name, rules) = splitLine(input, "{")
        return Workflow(
            name,
            parseRules(rules.replace("}", ""))
        )
    }

    private fun parseRules(input: String): List<Rule> {
        val parts = splitLine(input, ",")
        return parts.map {
            parseRule(it)
        }
    }

    private fun parseRule(input: String): Rule {
        if (!input.contains(":")) {
            return Rule("", "", 0, input)
        }

        val variableName = input.substring(0, 1)
        val operator = input.substring(1, 2)
        val (number, next) = splitLine(input.substring(2), ":")
        return Rule(
            variableName,
            operator,
            number.toLong(),
            next
        )
    }

    override fun solve2(lines: List<String>) {
    }
}
