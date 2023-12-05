package com.rolf.day05

import com.rolf.Day
import com.rolf.util.groupLines
import com.rolf.util.splitLine

fun main() {
    Day05().run()
}

class Day05 : Day() {
    override fun solve1(lines: List<String>) {
        val groups = groupLines(lines, "")
        val seedsLine = groups[0].first()
        val maps = groups.subList(1, groups.size)
        val (_, seedNumbers) = splitLine(seedsLine, ": ")
        val seeds = splitLine(seedNumbers, " ").map { it.toLong() }

        val mappings = maps.map {
            toMap(it)
        }

        var minLocation = Long.MAX_VALUE
        for (seed in seeds) {
            var value = seed
            for (mapping in mappings) {
                value = mapping.map(value)
            }
            minLocation = minOf(minLocation, value)
        }
        println(minLocation)
    }

    override fun solve2(lines: List<String>) {
        val groups = groupLines(lines, "")
        val seedsLine = groups[0].first()
        val maps = groups.subList(1, groups.size)
        val (_, seedNumbers) = splitLine(seedsLine, ": ")
        val seeds = splitLine(seedNumbers, " ").map { it.toLong() }

        val newSeeds = mutableListOf<LongRange>()
        for (i in seeds.indices step 2) {
            val start = seeds[i]
            val length = seeds[i + 1]
            val range = start until (start + length)
            newSeeds.add(range)
        }

        val mappings = maps.map {
            toMap(it)
        }

        // TODO: Optimize, since it runs for ~3 minutes
        var minLocation = Long.MAX_VALUE
        for (range in newSeeds) {
            println(range)
            for (seed in range) {
                var value = seed
                for (mapping in mappings) {
                    value = mapping.map(value)
                }
                minLocation = minOf(minLocation, value)
            }
        }
        println(minLocation)
    }

    private fun toMap(mapLines: List<String>): Mapping {
        val name = mapLines[0]
        val conversions = mutableListOf<Conversion>()
        for (i in 1 until mapLines.size) {
            val (destinationRangeStart, sourceRangeStart, rangeLength) =
                mapLines[i].split(" ")
                    .map { it.toLong() }
            val conversion = Conversion(
                sourceRangeStart until (sourceRangeStart + rangeLength),
                destinationRangeStart - sourceRangeStart
            )
            conversions.add(conversion)
        }
        return Mapping(name, conversions)
    }
}
