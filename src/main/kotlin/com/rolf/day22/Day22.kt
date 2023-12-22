package com.rolf.day22

import com.rolf.Day
import com.rolf.util.Location
import com.rolf.util.splitLine

fun main() {
    Day22().run()
}

class Day22 : Day() {
    override fun solve1(lines: List<String>) {
        val bricks = lines.map { parseBrick(it) }

        var lastBricks = bricks.toList()
        while (true) {
            val movedBricks = moveDown(lastBricks)
            if (movedBricks != lastBricks) {
                lastBricks = movedBricks
            } else {
                break
            }
        }
//        lastBricks.forEach {
//            println(it)
//        }

//        println()

        // Check if a brick is supported by multiple bricks, so each of them can be removed
//        val bricksByMaxZ = lastBricks.groupBy { it.maxZ }
//        val bricksByMinZ = lastBricks.groupBy { it.minZ }
        val toRemove = mutableSetOf<Brick>()
        for (brick in lastBricks) {
            val supporting = isSupporting(brick, lastBricks)
            val supportedBy = isSupportedBy(brick, lastBricks)
//            println("$brick is supporting $supporting")
//            println("$brick is supported by $supportedBy")

//            if (brick.minZ == 1) continue
//            val belowZ = brick.minZ - 1
//            val belowBricks = bricksByMaxZ[belowZ] ?: emptyList()
//            // If there are bricks, see if they are overlapping in both x and y
//            val supportingBricks = belowBricks.filter {
//                it.xRange.intersect(brick.xRange).isNotEmpty()
//                        && it.yRange.intersect(brick.yRange).isNotEmpty()
//            }
            // You can remove a brick when it isn't supporting anything
            if (supporting.isEmpty()) {
//                println("$brick can be removed, since it doesn't support anything")
                toRemove.add(brick)
            }
            if (supportedBy.size > 1) {
//                println("$supportedBy can be removed, since they all support $brick")
                toRemove.addAll(supportedBy)
            }
        }

//        println()
//        toRemove.forEach {
//            println(it)
//        }
        println(toRemove.size)
        // 1199 too high

//        // View from y
//        for (brick in bricks) {
//            // Find bricks with z+1
//            val aboveZ = brick.maxZ + 1
//            val aboveBricks = bricksByMinZ[aboveZ] ?: emptyList()
//            // Make sure their x overlaps
//            val supportsBricks = aboveBricks.filter {
//                brick.yRange.intersect(it.yRange).isNotEmpty()
//            }
//            println("$brick supports $supportsBricks")
//        }
    }

    private fun isSupportedBy(brick: Brick, bricks: List<Brick>): List<Brick> {
        val bricksByMaxZ = bricks.groupBy { it.maxZ }
        val belowZ = brick.minZ - 1

        val belowBricks = bricksByMaxZ[belowZ] ?: emptyList()
        // If there are bricks, see if they are overlapping in x or y
        return belowBricks.filter {
            it.xRange.intersect(brick.xRange).isNotEmpty()
                    || it.yRange.intersect(brick.yRange).isNotEmpty()
        }

    }

    private fun isSupporting(brick: Brick, bricks: List<Brick>): List<Brick> {
        val bricksByMinZ = bricks.groupBy { it.minZ }
        val aboveZ = brick.maxZ + 1

        val aboveBricks = bricksByMinZ[aboveZ] ?: emptyList()
        // If there are bricks, see if they are overlapping in x or y
        return aboveBricks.filter {
            it.xRange.intersect(brick.xRange).isNotEmpty()
                    || it.yRange.intersect(brick.yRange).isNotEmpty()
        }

    }

    private fun moveDown(bricks: List<Brick>): List<Brick> {
        val result = mutableListOf<Brick>()
        val bricksByMaxZ = bricks.groupBy { it.maxZ }
//        val bricksByMinZ = bricks.groupBy { it.minZ }

        // Let the bricks fall down
        // To do so, we need to know how many spaces a block can fall down from x or y view
        // Find the blocks below each block
        for (brick in bricks) {
            if (brick.minZ == 1) {
                result.add(brick)
                continue
            }

            val belowZ = brick.minZ - 1
            val belowBricks = bricksByMaxZ[belowZ] ?: emptyList()
            // If there are bricks, see if they are overlapping in both x and y
            val supportingBricks = belowBricks.filter {
                it.xRange.intersect(brick.xRange).isNotEmpty()
                        && it.yRange.intersect(brick.yRange).isNotEmpty()
            }
            if (supportingBricks.isEmpty()) {
//                println("$brick can move 1 down, because no support")
                result.add(brick.moveDown())
            } else {
                result.add(brick)
            }
        }
        return result
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

    override fun solve2(lines: List<String>) {
    }
}
