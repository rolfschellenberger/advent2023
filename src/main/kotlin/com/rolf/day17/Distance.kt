package com.rolf.day17

data class Distance(
    val step: Step,
    val distance: Int,
) : Comparable<Distance> {
    override fun compareTo(other: Distance): Int {
        return distance.compareTo(other.distance)
    }
}
