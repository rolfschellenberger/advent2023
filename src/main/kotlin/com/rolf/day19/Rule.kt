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
}
