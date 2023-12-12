package com.rolf.day12

import com.rolf.Day
import com.rolf.util.splitLine
import java.util.regex.Pattern

fun main() {
    Day12().run()
}

class Day12 : Day() {
    override fun solve1(lines: List<String>) {
        println(7402)

        var sum = 0L
        for (line in lines) {
            val (pattern, expected) = splitLine(line, " ")
            val expectedSequence = splitLine(expected, ",").map { it.toInt() }

            val options = generateOptions(pattern)
            val matches = options.map {
                toSequence(it)
            }.count {
                it == expectedSequence
            }
            sum += matches

//            val options = mutableListOf<String>()
//            for (i in pattern.indices) {
//                options.add(".")
//                options.add("#")
//            }
//            val permutations = getPermutations(options, pattern.length).toSet()
//            println(permutations.size)
//
//            continue


//            val expectedSequence = splitLine(expected, ",").map { it.toInt() }
//
//            val sequenceOptions = mutableListOf<List<List<Int>>>()
//            val blocks = splitLine(pattern, pattern = Pattern.compile("\\.+"))
//            println(line)
//            println(blocks)
//            // Find all options for each block with ?
//            for (block in blocks) {
//                val blockOptions = mutableListOf<List<Int>>()
//                if (block.contains("?")) {
//                    val options = mutableListOf<String>()
//                    for (i in block.indices) {
//                        options.add(".")
//                        options.add("#")
//                    }
//                    val permutations = getPermutations(
//                        options,
//                        3
//                    ).toSet()
//
//                    for (permutation in permutations) {
////                        println(permutation)
//                        val permutationSequence = toSequence(
//                            permutation.joinToString("")
//                        )
//                        blockOptions.add(permutationSequence)
//                    }
//                } else {
//                    blockOptions.add(listOf(block.length))
//                }
////                println(blockOptions)
//                sequenceOptions.add(blockOptions)
//            }
//            println(sequenceOptions)
//
//            // Now create the possible options and count if they match the expected sequence
//            val possibleOptions = findPossibleOptions(sequenceOptions)
//            var count = 0L
//            for (possibleOption in possibleOptions) {
//                if (possibleOption == expectedSequence) {
//                    count++
//                }
//            }
//            println(count)
//            println()
        }
        println(sum)
    }

    private fun generateOptions(pattern: String): List<String> {
        val options = mutableListOf<String>()
        if (pattern.contains("?")) {
            val a = pattern.replaceFirst("?", ".")
            val b = pattern.replaceFirst("?", "#")
            if (a.contains("?")) {
                options.addAll(generateOptions(a))
            } else {
                options.add(a)
            }
            if (b.contains("?")) {
                options.addAll(generateOptions(b))
            } else {
                options.add(b)
            }
        }
        return options
    }

    private fun findPossibleOptions(sequenceOptions: List<List<List<Int>>>): List<List<Int>> {

        TODO("Not yet implemented")
    }

    private fun toSequence(line: String): List<Int> {
        val split = splitLine(line, pattern = Pattern.compile("\\.+")).filter { it.isNotEmpty() }
//        println(split)
        return split.map {
            it.length
        }
    }

    override fun solve2(lines: List<String>) {
        println(3384337640277)
    }
}
