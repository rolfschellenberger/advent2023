package com.rolf.day05

class Conversion(
    val range: LongRange,
    val delta: Long,
) {
    override fun toString(): String {
        return "Conversion(range=$range, delta=$delta)"
    }
}
