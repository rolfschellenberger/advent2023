package com.rolf.day19

data class Part(
    val x: Long,
    val m: Long,
    val a: Long,
    val s: Long,
) {
    fun sum(): Long {
        return x + m + a + s
    }
}
