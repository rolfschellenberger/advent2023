package com.rolf.day15

data class Box(
    val lenses: MutableList<Lens> = mutableListOf(),
) {
    fun remove(other: Lens) {
        lenses.remove(other)
    }

    fun add(other: Lens) {
        val index = lenses.indexOf(other)
        // Replace
        if (index >= 0) {
            lenses[index] = other
        } else {
            lenses.add(other)
        }
    }

    fun focusingPower(boxIndex: Int): Int {
        return lenses.withIndex().sumOf { (index, lens) ->
            (boxIndex + 1) * (index + 1) * lens.focalLength
        }
    }
}
