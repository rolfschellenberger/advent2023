package com.rolf.day15

data class Lens(
    val value: String,
    val focalLength: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lens

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
