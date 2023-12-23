package com.rolf.day23

import com.rolf.util.Point

data class Path(
    val locations: List<Point>,
    val seen: MutableSet<Point> = mutableSetOf(),
) {
    override fun toString(): String {
        return "Path(locations=$locations)"
    }
}
