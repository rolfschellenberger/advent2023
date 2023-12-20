package com.rolf.day20

import com.rolf.Day
import com.rolf.util.splitLine
import java.util.*

fun main() {
    Day20().run()
}

class Day20 : Day() {
    override fun solve1(lines: List<String>) {
        val modules = lines.map {
            parse(it)
        }

        // Conjunctions
        val conjunctionsByName = modules
            .filterIsInstance<Conjunction>()
            .associateBy { it.name }

        // Pointing to a conjunction
        for (module in modules) {
            for (output in module.outputs) {
                // Pointing to a conjunction
                if (conjunctionsByName.containsKey(output)) {
                    conjunctionsByName.getValue(output).addInput(module.name)
                }
            }
        }

        val modulesMap = modules.associateBy { it.name }
//        modulesMap.forEach {
//            println(it)
//        }

        var highCount = 0
        var lowCount = 0
        for (i in 0 until 1000) {
            val (high, low) = pressButton(modulesMap)
            highCount += high
            lowCount += low
        }
        println(highCount)
        println(lowCount)
        println(highCount * lowCount)
    }

    private fun pressButton(modulesMap: Map<String, Module>): Pair<Int, Int> {
        val stack = LinkedList<Signal>()
        stack.add(Signal(false, "button", "broadcaster"))
        return processSignals(modulesMap, stack)
    }

    private fun processSignals(modulesMap: Map<String, Module>, stack: LinkedList<Signal>): Pair<Int, Int> {
        var lowCount = 0
        var highCount = 0
        while (stack.isNotEmpty()) {
            val signal = stack.removeFirst()
            println(signal)
            if (signal.level) {
                highCount++
            } else {
                lowCount++
            }
            val module = modulesMap[signal.to]
            if (module != null) {
                val output = module.send(signal)
                stack.addAll(output)
            }
        }
        return highCount to lowCount
    }

    private fun parse(line: String): Module {
        val name = getName(line)
        val outputs = getOutputs(line)
        return when {
            line.startsWith("broadcast") -> {
                Broadcaster(
                    name,
                    outputs
                )
            }

            line.startsWith("%") -> {
                return FlipFlop(
                    name,
                    outputs
                )
            }

            line.startsWith("&") -> {
                return Conjunction(
                    name,
                    outputs
                )
            }

            else -> throw RuntimeException("Unknown module: $line")
        }
    }

    private fun getOutputs(line: String): List<String> {
        val (_, out) = splitLine(line, " -> ")
        return splitLine(out, ", ")
    }

    private fun getName(line: String): String {
        val (name, _) = splitLine(line, " -> ")
        return when {
            name.startsWith("%") -> name.substring(1)
            name.startsWith("&") -> name.substring(1)
            else -> name
        }
    }

    override fun solve2(lines: List<String>) {
    }
}
