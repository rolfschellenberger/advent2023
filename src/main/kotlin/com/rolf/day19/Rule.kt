package com.rolf.day19

data class Rule(
    val variableName: String,
    val operator: String,
    val value: Int,
    val next: String,
) {
    fun applies(part: Part): Boolean {
        if (variableName == "") {
            return true
        }

        val partValue = when (variableName) {
            "x" -> part.x
            "m" -> part.m
            "a" -> part.a
            "s" -> part.s
            else -> throw RuntimeException("Incorrect variable $variableName")
        }

        return when (operator) {
            ">" -> partValue > value
            "<" -> partValue < value
            else -> throw RuntimeException("Incorrect operator $operator")
        }
    }

    fun applies2(range: Range): Pair<Range, Range?> {
        if (variableName == "") {
            return range to null
        }

        val intRange = when (variableName) {
            "x" -> range.x
            "m" -> range.m
            "a" -> range.a
            "s" -> range.s
            else -> throw RuntimeException("Incorrect variableName $variableName")
        }

        val (newItRange, restIntRange) = splitRange(intRange, operator)
        return when (variableName) {
            "x" -> range.copy(x = newItRange) to range.copy(x = restIntRange)
            "m" -> range.copy(m = newItRange) to range.copy(m = restIntRange)
            "a" -> range.copy(a = newItRange) to range.copy(a = restIntRange)
            "s" -> range.copy(s = newItRange) to range.copy(s = restIntRange)
            else -> throw RuntimeException("Incorrect variableName $variableName")
        }
    }

    private fun splitRange(range: IntRange, operator: String): Pair<IntRange, IntRange> {
        val newRange = when (operator) {
            ">" -> value + 1..range.last
            "<" -> range.first until value
            else -> throw RuntimeException("Incorrect operator $operator")
        }
        val restRange = when (operator) {
            ">" -> range.first..value
            "<" -> value..range.last
            else -> throw RuntimeException("Incorrect operator $operator")
        }
        return newRange to restRange
    }
}
