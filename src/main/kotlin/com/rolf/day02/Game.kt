package com.rolf.day02

data class Game(
    val id: Int,
    val rounds: List<Round>
) {
    fun hasMax(red: Int, green: Int, blue: Int): Boolean {
        for (round in rounds) {
            if (round.red > red) return false
            if (round.green > green) return false
            if (round.blue > blue) return false
        }
        return true
    }

    fun power(): Int {
        val minRed = rounds.maxOf {
            it.red
        }
        val minGreen = rounds.maxOf {
            it.green
        }
        val minBlue = rounds.maxOf {
            it.blue
        }
        return minRed * minGreen * minBlue
    }
}
