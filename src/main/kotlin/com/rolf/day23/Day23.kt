package com.rolf.day23

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.splitLines

fun main() {
    Day23().run()
}

class Day23 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        println(grid)
        val start = grid.find(".").first()
        val end = grid.find(".").last()
        println(start)
        println(end)
        val notAllowed = grid.find("#").toSet()
//        val path = grid.findPathByValue(start, end, setOf("#"), false)
//        val allowedFunction = { matrix: Matrix<String>, from: Point, to: Point ->
//            val f = matrix.get(from).first().code
//            val t = matrix.get(to).first().code
//            (t - f) <= 1
//        }
        val path = grid.findLongestPath(start, end, notAllowed, false)
        path.forEach {
            grid.set(it, "O")
        }
        println(grid)
        println(path.size - 1)
//        path.forEach { println(it) }
//        grid.findLongestPath()
    }

    override fun solve2(lines: List<String>) {
    }
}
