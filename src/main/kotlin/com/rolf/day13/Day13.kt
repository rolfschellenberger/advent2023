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
        var sum = 0L
        val patterns = groupLines(lines, "")
        for (pattern in patterns) {
            val grid = MatrixString.build(splitLines(pattern))
//            println(grid)
//            println()

            val (row, column) = findMirror(grid)
            if (row == -1 && column == -1) {
                println(grid)
                throw RuntimeException("No mirrors found!")
            }
//            println("row: $row")
//            println("column: $column")
//            println()

            if (row >= 0) {
                sum += (row + 1) * 100

            } else {
                if (column >= 0) {
                    sum += column + 1
                }
            }
//            println()
        }
        println(sum)
    }

    private fun findMirror(grid: MatrixString, excludeRow: Int = -1, excludeColumn: Int = -1): Pair<Int, Int> {
        val row = findMirrorRow(grid, excludeRow)
        val column = findMirrorColumn(grid, excludeColumn)
        return row to column
    }

    private fun findMirrorRow(grid: MatrixString, excludeRow: Int): Int {
        val mirrorOptions = mutableSetOf<Int>()
        var first = true
        val columns = grid.getColumns()
        for (column in columns) {
            val columMirrors = findMirrorsColumn(column) - excludeRow
            if (first) {
                mirrorOptions.addAll(columMirrors)
                first = false
            } else {
                val intersection = mirrorOptions.intersect(columMirrors)
                mirrorOptions.clear()
                mirrorOptions.addAll(intersection)
            }
        }
//        println("column options: $mirrorOptions")
        return if (mirrorOptions.size != 1) -1 else mirrorOptions.first()
    }

    private fun findMirrorsColumn(column: List<String>): Set<Int> {
        val mirrors = mutableSetOf<Int>()
//        println(column)
        for (i in 0 until column.size - 1) {
            val left = column.subList(0, i + 1).asReversed()
            val right = column.subList(i + 1, column.size)
//            println(left)
//            println(right)
            var mirror = true
            for (j in 0 until minOf(left.size, right.size)) {
                if (left[j] != right[j]) {
                    mirror = false
                    break
                }
            }
            if (mirror) {
//                println("mirror: $left, $right")
                mirrors.add(i)
            }
        }
//        println(mirrors)
//        println()
        return mirrors
    }

    private fun findMirrorColumn(grid: MatrixString, excludeColumn: Int): Int {
        val mirrorOptions = mutableSetOf<Int>()
        var first = true
        val rows = grid.getRows()
        for (row in rows) {
            val columMirrors = findMirrorsRow(row) - excludeColumn
            if (first) {
                mirrorOptions.addAll(columMirrors)
                first = false
            } else {
                val intersection = mirrorOptions.intersect(columMirrors)
                mirrorOptions.clear()
                mirrorOptions.addAll(intersection)
            }
        }
//        println("row options: $mirrorOptions")
        return if (mirrorOptions.size != 1) -1 else mirrorOptions.first()
    }

    private fun findMirrorsRow(row: List<String>): Set<Int> {
        val mirrors = mutableSetOf<Int>()
//        println(row)
        for (i in 0 until row.size - 1) {
            val top = row.subList(0, i + 1).asReversed()
            val bottom = row.subList(i + 1, row.size)
//            println(top)
//            println(bottom)
            var mirror = true
            for (j in 0 until minOf(top.size, bottom.size)) {
                if (top[j] != bottom[j]) {
                    mirror = false
                    break
                }
            }
            if (mirror) {
//                println("mirror: $top, $bottom")
                mirrors.add(i)
            }
        }
//        println(mirrors)
//        println()
        return mirrors
    }

    override fun solve2(lines: List<String>) {
        var sum = 0L
        val patterns = groupLines(lines, "")
        for (pattern in patterns) {
            val grid = MatrixString.build(splitLines(pattern))
//            println(grid)
//            println()

            val (row, column) = findMirror(grid)
            if (row == -1 && column == -1) {
                println(grid)
                throw RuntimeException("No mirrors found!")
            }
//            println("row: $row")
//            println("column: $column")

            // Now change 1 location until we have a different row or column
            for (point in grid.allPoints()) {
                val copy = grid.copy()
                copy.set(point, opposite(grid.get(point)))
//                    println(copy)
                val (newRow, newColumn) = findMirror(copy, row, column)
//                println(newRow)
//                println(newColumn)
                if ((newRow >= 0 || newColumn >= 0)
                    && (newRow != row || newColumn != column)
                ) {

//                        println(copy)
//                        println("#################")
//                    println("newRow: $newRow")
//                    println("newColumn: $newColumn")

                    if (newRow >= 0) {
                        sum += (newRow + 1) * 100

                    } else {
                        sum += newColumn + 1
                    }
                    break
                }
            }
//            println()
        }
        println(sum)
        // 38160 too high
    }

    private fun opposite(value: String): String {
        return if (value == "#") "." else "#"
    }
}
