package com.rolf.day20

import com.rolf.Day
import com.rolf.util.leastCommonMultiple
import com.rolf.util.splitLine
import java.util.*

fun main() {
    Day20().run()
}

class Day20 : Day() {
    override fun solve1(lines: List<String>) {
        val modulesMap = map(lines)
        var highCount = 0
        var lowCount = 0
        for (i in 0 until 1000) {
            val (high, low) = pressButton(modulesMap)
            highCount += high
            lowCount += low
        }
        println(highCount * lowCount)
    }

    private fun map(lines: List<String>): Map<String, Module> {
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

        return modules.associateBy { it.name }
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

    private fun pressButton(modulesMap: Map<String, Module>, moduleOn: String = ""): Pair<Int, Int> {
        val stack = LinkedList<Signal>()
        stack.add(Signal(false, "button", "broadcaster"))
        return processSignals(modulesMap, stack, moduleOn)
    }

    private fun processSignals(
        modulesMap: Map<String, Module>,
        stack: LinkedList<Signal>,
        moduleOn: String = "",
    ): Pair<Int, Int> {
        var lowCount = 0
        var highCount = 0
        while (stack.isNotEmpty()) {
            val signal = stack.removeFirst()
            // module on
            if (moduleOn == "" || signal.from == moduleOn) {
                if (signal.level) {
                    highCount++
                } else {
                    lowCount++
                }
            }

            val module = modulesMap[signal.to]
            if (module != null) {
                val output = module.send(signal)
                stack.addAll(output)
            }
        }
        return highCount to lowCount
    }

    override fun solve2(lines: List<String>) {
        val modulesMap = map(lines)
        // rx is a conjunction of mr, kk, gl and bb
        val conjunction: Conjunction = findFirstConjunction(modulesMap, listOf("rx"))
        val inputs = conjunction.inputSignals.keys
        val steps = inputs.map {
            getRepeat(modulesMap, it)
        }
        println(leastCommonMultiple(steps))
    }

    private fun findFirstConjunction(modulesMap: Map<String, Module>, outputs: List<String>): Conjunction {
        // Find the modules feeding into output
        val modules = modulesMap.filter {
            it.value.outputs.containsAll(outputs)
        }.map {
            it.value
        }
        // Is this 1 module and a conjunction, return it
        if (modules.size == 1 && modules.first() is Conjunction) {
            return modules.first() as Conjunction
        }
        // Otherwise find the next modules
        return findFirstConjunction(modulesMap, modules.map { it.name })
    }

    private fun getRepeat(modulesMap: Map<String, Module>, name: String): Long {
        var first = 0L
        for (i in 0 until 10000L) {
            val (high, _) = pressButton(modulesMap, name)
            if (high > 0) {
                if (first == 0L) {
                    first = i
                } else {
                    return i - first
                }
            }
        }
        throw RuntimeException("No module state found with high signal for $name")
    }
}
