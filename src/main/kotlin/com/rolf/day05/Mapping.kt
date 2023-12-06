package com.rolf.day05

import com.rolf.util.add
import com.rolf.util.hasOverlap
import com.rolf.util.split

data class Mapping(
    private val name: String,
    private val conversions: List<Conversion>
) {
    fun map(ranges: List<LongRange>): List<LongRange> {
        return ranges.map {
            map(it)
        }.flatten()
    }

    fun map(range: LongRange): List<LongRange> {
        // Split the range into all ranges possible before shifting
        var ranges = setOf(range)
        for (conversion in conversions) {
            ranges = ranges.map {
                it.split(conversion.range)
            }
                .flatten()
                .toSet()
        }

        // Now shift the ranges that match
        val shiftedRanges = mutableListOf<LongRange>()
        for (r in ranges) {
            var shifted = false
            for (conversion in conversions) {
                if (r.hasOverlap(conversion.range)) {
                    val shiftedRange = r.add(conversion.delta)
                    shiftedRanges.add(shiftedRange)
                    shifted = true
                }
            }
            if (!shifted) {
                shiftedRanges.add(r)
            }
        }
        return shiftedRanges
    }
}
