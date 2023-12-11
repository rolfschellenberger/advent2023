package com.rolf.day11

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.splitLines

fun main() {
    Day11().run()
}

class Day11 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val expandedGrid = expandGrid(grid)

        // Sum the pairs
        val galaxies = expandedGrid.find("#")
        val sum = galaxies.sumOf { galaxy ->
            galaxies.sumOf { other ->
                galaxy.distance(other)
            }
        }
        println(sum / 2)
    }

    private fun expandGrid(originalGrid: MatrixString): MatrixString {
        val grid = originalGrid.copy()

        val rowIndexes = getEmptyRowIndexes(grid)
        val columnIndexes = getEmptyColumnIndexes(grid)

        for (rowIndex in rowIndexes.sortedDescending()) {
            grid.insertRow(rowIndex, ".")
        }
        for (columnIndex in columnIndexes.sortedDescending()) {
            grid.insertColumn(columnIndex, ".")
        }
        return grid
    }

    private fun getEmptyRowIndexes(grid: MatrixString): Set<Int> {
        return grid.getRows().withIndex().filter {
            it.value.all { it == "." }
        }.map {
            it.index
        }.toSet()
    }

    private fun getEmptyColumnIndexes(grid: MatrixString): Set<Int> {
        return grid.getColumns().withIndex().filter {
            it.value.all { it == "." }
        }.map {
            it.index
        }.toSet()
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))

        val rowIndexes = getEmptyRowIndexes(grid)
        val columnIndexes = getEmptyColumnIndexes(grid)

        val expansionSize = 1_000_000L - 1

        // Sum the pairs
        val galaxies = grid.find("#")
        val sum = galaxies.sumOf { galaxy ->
            galaxies.sumOf { other ->
                val rowsBetween = getDistanceBetween(galaxy.y, other.y, rowIndexes)
                val columnsBetween = getDistanceBetween(galaxy.x, other.x, columnIndexes)
                val distance = galaxy.distance(other)
                distance + (rowsBetween * expansionSize) + (columnsBetween * expansionSize)
            }
        }
        println(sum / 2)
    }

    private fun getDistanceBetween(v1: Int, v2: Int, indexes: Set<Int>): Int {
        val list = listOf(v1, v2).sorted()
        var count = 0
        for (a in list[0] until list[1]) {
            if (indexes.contains(a)) {
                count++
            }
        }
        return count
    }
}
