package com.rolf.day22

import com.rolf.util.Location

data class Brick(
    val from: Location,
    val to: Location,
    var id: Int = 0,
) {
    override fun toString(): String {
        return "Brick(id=$id, from=$from, to=$to)"
    }

    fun moveDown(delta:Int = 1): Brick {
        return Brick(
            Location(from.x, from.y, from.z - delta),
            Location(to.x, to.y, to.z - delta)
        )
    }

    val xRange: IntRange
        get() {
            return minX..maxX
        }

    val yRange: IntRange
        get() {
            return minY..maxY
        }

    val zRange: IntRange
        get() {
            return minZ..maxZ
        }

    val maxX: Int
        get() {
            return maxOf(from.x, to.x)
        }

    val minX: Int
        get() {
            return minOf(from.x, to.x)
        }

    val maxY: Int
        get() {
            return maxOf(from.y, to.y)
        }

    val minY: Int
        get() {
            return minOf(from.y, to.y)
        }

    val maxZ: Int
        get() {
            return maxOf(from.z, to.z)
        }

    val minZ: Int
        get() {
            return minOf(from.z, to.z)
        }
}
