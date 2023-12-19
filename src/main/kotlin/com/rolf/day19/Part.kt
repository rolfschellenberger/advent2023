package com.rolf.day19

data class Part(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int,
) {
    fun sum(): Int {
        return x + m + a + s
    }
}
