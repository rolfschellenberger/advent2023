package com.rolf.day19

import com.rolf.util.size

data class Range(
    val x: IntRange,
    val m: IntRange,
    val a: IntRange,
    val s: IntRange,
) {
    fun sum(): Long {
        return x.size().toLong() * m.size() * a.size() * s.size()
    }
}
