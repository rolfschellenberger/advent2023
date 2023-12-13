package com.rolf.day13

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.groupLines
import com.rolf.util.splitLines

fun main() {
    Day13().run()
}

class Day13 : Day() {
    override fun solve1(lines: List<String>) {
        println(
            groupLines(lines, "").map {
                MatrixString.build(splitLines(it))
            }.flatMap {
                listOf(
                    (findMirrorInLists(it.getColumns()) + 1) * 100,
                    findMirrorInLists(it.getRows()) + 1
                )
            }.sum()
        )
    }

    override fun solve2(lines: List<String>) {
        println(
            groupLines(lines, "").asSequence()
                .map {
                    MatrixString.build(splitLines(it))
                }.map {
                    // Find the 'default' mirror row or column
                    Triple(it, findMirrorInLists(it.getColumns()), findMirrorInLists(it.getRows()))
                }.flatMap { triple ->
                    val (grid, row, column) = triple

                    // Create all grid options where 1 location is changed to its opposite value
                    grid.allPoints().map { point ->
                        val copy = grid.copy()
                        copy.set(point, opposite(grid.get(point)))
                        Triple(copy, row, column)
                    }
                }.flatMap { triple ->
                    val (grid, row, column) = triple
                    // Find the mirror row or column while ignoring the 'default' mirror row or column
                    listOf(
                        (findMirrorInLists(grid.getColumns(), row) + 1) * 100,
                        findMirrorInLists(grid.getRows(), column) + 1
                    )
                }.sum() / 2
        )
    }

    private fun findMirrorInLists(lists: List<List<String>>, exclude: Int = -1): Int {
        val mirrorOptions = mutableSetOf<Int>()
        var first = true
        for (list in lists) {
            val columMirrors = findMirrorsInList(list) - exclude
            if (first) {
                mirrorOptions.addAll(columMirrors)
                first = false
            } else {
                val intersection = mirrorOptions.intersect(columMirrors)
                mirrorOptions.clear()
                mirrorOptions.addAll(intersection)
            }
        }
        return if (mirrorOptions.size != 1) -1 else mirrorOptions.first()
    }


    private fun findMirrorsInList(list: List<String>): Set<Int> {
        val mirrors = mutableSetOf<Int>()
        for (i in 0 until list.size - 1) {
            val left = list.subList(0, i + 1).asReversed()
            val right = list.subList(i + 1, list.size)
            var mirror = true
            for (j in 0 until minOf(left.size, right.size)) {
                if (left[j] != right[j]) {
                    mirror = false
                    break
                }
            }
            if (mirror) {
                mirrors.add(i)
            }
        }
        return mirrors
    }

    private fun opposite(value: String): String {
        return if (value == "#") "." else "#"
    }
}
