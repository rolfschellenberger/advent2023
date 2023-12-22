package com.rolf.day22

import com.rolf.Day
import com.rolf.util.Location
import com.rolf.util.Point
import com.rolf.util.splitLine

fun main() {
    Day22().run()
}

class Day22 : Day() {

    override fun solve1(lines: List<String>) {
        val bricks = parseBricks(lines)
        val (_, fallen) = drop(bricks)
        var sum = 0
        for (i in fallen.indices) {
            // Remove 1 brick from the tower and remove it to see if this causes the tower to fall again
            val removedTower = fallen.toMutableList()
            removedTower.removeAt(i)
            val (falls, _) = drop(removedTower)
            // When nothing fell down, the brick could be removed
            if (falls == 0) {
                sum++
            }
        }
        println(sum)
    }

    override fun solve2(lines: List<String>) {
        val bricks = parseBricks(lines)
        val (_, fallen) = drop(bricks)
        var sum = 0
        for (i in fallen.indices) {
            // Remove 1 brick from the tower and remove it to see if this causes the tower to fall again
            val removedTower = fallen.toMutableList()
            removedTower.removeAt(i)
            val (falls, _) = drop(removedTower)
            // Count how many falls the removal caused
            sum += falls
        }
        println(sum)
    }

    private fun parseBricks(lines: List<String>): List<Brick> {
        val bricks = lines.map { parseBrick(it) }.sortedBy {
            it.minZ
        }
        for (index in bricks.indices) {
            bricks[index].id = index
        }
        return bricks
    }

    private fun parseBrick(line: String): Brick {
        val (from, to) = splitLine(line, "~")
        return Brick(
            parseLocation(from),
            parseLocation(to)
        )
    }

    private fun parseLocation(input: String): Location {
        val (x, y, z) = splitLine(input, ",")
        return Location(x.toInt(), y.toInt(), z.toInt())
    }

    private fun drop(tower: List<Brick>): Pair<Int, List<Brick>> {
        // Keep track of the tallest Z position to know when to stop dropping for each brick
        // (we know the input of the bricks is sorted by the minimum Z position ASC)
        val highPoints = mutableMapOf<Point, Int>()

        val newTower = mutableListOf<Brick>()
        var fallCount = 0
        for (brick in tower) {
            val newBrick = dropBrick(highPoints, brick)
            if (newBrick.minZ != brick.minZ) {
                fallCount++
            }
            newTower.add(newBrick)
            for (x in brick.xRange) {
                for (y in brick.yRange) {
                    highPoints[Point(x, y)] = newBrick.maxZ
                }
            }
        }
        return fallCount to newTower
    }

    private fun dropBrick(highPoints: MutableMap<Point, Int>, brick: Brick): Brick {
        // Look up the highest peaks that this brick could hit
        val peaks = mutableListOf<Int>()
        for (x in brick.xRange) {
            for (y in brick.yRange) {
                val point = Point(x, y)
                if (highPoints.containsKey(point)) {
                    peaks.add(highPoints.getValue(point))
                }
            }
        }
        val peak = if (peaks.isEmpty()) 0 else peaks.max()
        val dz = maxOf(0, brick.from.z - peak - 1)
        return brick.moveDown(dz)
    }
}
