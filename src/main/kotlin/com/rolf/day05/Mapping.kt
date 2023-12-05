package com.rolf.day05

class Mapping(
    private val name: String,
    private val conversions: List<Conversion>
) {

    fun map(value: Long): Long {
        for (conversion in conversions) {
            if (value in conversion.range) {
                return value + conversion.delta
            }
        }
        return value
    }

    override fun toString(): String {
        return "Mapping(name='$name', conversions=$conversions)"
    }
}
