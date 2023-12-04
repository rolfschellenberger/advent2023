package com.rolf.day04

class Card(
    val id: Int,
    private val numbers: Set<Int>,
    private val have: Set<Int>,
    var count: Int = 1
) {
    fun intersect(): Int {
        return numbers.intersect(have).size
    }

    fun win(times: Int) {
        count += times
    }

    override fun toString(): String {
        return "Card(id=$id, numbers=$numbers, have=$have, count=$count)"
    }
}
