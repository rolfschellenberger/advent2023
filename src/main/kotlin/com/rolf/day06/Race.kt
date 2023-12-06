package com.rolf.day06

data class Race(val time: Long, val distance: Long) {
    fun wins(): Long {
        var wins = 0L
        for (t in 0..time) {
            val remainder = time - t
            val d = t * remainder
            if (d > distance) {
                wins++
            }

            // Early termination when no more wins, we can stop
            if (d <= distance && wins > 0) {
                break
            }
        }
        return wins
    }
}
