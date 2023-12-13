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
        val mirrorOptions = lists.map {
            findMirrorsInList(it) - exclude
        }.reduce { a, b ->
            a.intersect(b)
        }
        return if (mirrorOptions.size != 1) -1 else mirrorOptions.first()
    }

    private fun findMirrorsInList(list: List<String>): Set<Int> {
        return (0 until list.size - 1).mapNotNull {
            // Compare equal lists and reduce to the minimum size
            val size = minOf(it + 1, list.size - it - 1)

            // Compare the (reversed) left and right side
            val left = list.subList(0, it + 1).asReversed().subList(0, size)
            val right = list.subList(it + 1, list.size).subList(0, size)
            if (left == right) it else null
        }.toSet()
    }

    private fun opposite(value: String): String {
        return if (value == "#") "." else "#"
    }
}
