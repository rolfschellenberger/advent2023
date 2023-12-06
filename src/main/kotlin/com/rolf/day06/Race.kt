package com.rolf.day06

data class Race(val time: Long, val distance: Long) {
    fun wins(): List<Pair<Long, Long>> {
        val wins = mutableListOf<Pair<Long, Long>>()
        for (t in 0..time) {
            val remainder = time - t
            val d = t * remainder
            if (d > distance) {
                wins.add(t to d)
            }
        }
        return wins
    }
}
