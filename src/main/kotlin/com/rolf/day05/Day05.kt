package com.rolf.day05

import com.rolf.Day
import com.rolf.util.groupLines
import com.rolf.util.splitLine

fun main() {
    Day05().run()
}

class Day05 : Day() {
    override fun solve1(lines: List<String>) {
        val (seeds, mappings) = parse(lines)
        var seedRanges = seeds.map {
            it..it
        }

        for (mapping in mappings) {
            seedRanges = mapping.map(seedRanges)
        }
        println(
            seedRanges.minOfOrNull {
                it.first
            }
        )
    }

    override fun solve2(lines: List<String>) {
        val (seeds, mappings) = parse(lines)
        var seedRanges = (seeds.indices step 2).map {
            val start = seeds[it]
            val length = seeds[it + 1]
            start until (start + length)
        }

        for (mapping in mappings) {
            seedRanges = mapping.map(seedRanges)
        }
        println(
            seedRanges.minOfOrNull {
                it.first
            }
        )
    }

    private fun parse(lines: List<String>): Pair<List<Long>, List<Mapping>> {
        val groups = groupLines(lines, "")
        val seedsLine = groups[0].first()
        val maps = groups.subList(1, groups.size)
        val (_, seedNumbers) = splitLine(seedsLine, ": ")
        val seeds = splitLine(seedNumbers, " ").map { it.toLong() }
        val mappings = maps.map {
            toMap(it)
        }
        return Pair(seeds, mappings)
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
