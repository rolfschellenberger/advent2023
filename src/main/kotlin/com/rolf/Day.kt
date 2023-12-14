package com.rolf

import com.rolf.util.readLines
import com.rolf.util.removeLastEmptyLine
import kotlin.time.measureTime

abstract class Day {

    open fun getDay(): Int {
        return javaClass.simpleName.replace("Day", "").toInt()
    }

    fun run() {
        val day = getDay().toString().padStart(2, '0')

        println("+--------+")
        println("| Day $day |")
        println("+--------+")

        println("-- Part 1 --")
        val time1 = measureTime {
            solve1(removeLastEmptyLine(readLines("/$day.txt")))
        }.inWholeMilliseconds
        println("-- ${time1}ms --")

        println()

        println("-- Part 2 --")
        val time2 = measureTime {
            solve2(removeLastEmptyLine(readLines("/$day.txt")))
        }.inWholeMilliseconds
        println("-- ${time2}ms --")
    }

    abstract fun solve1(lines: List<String>)
    abstract fun solve2(lines: List<String>)
}
