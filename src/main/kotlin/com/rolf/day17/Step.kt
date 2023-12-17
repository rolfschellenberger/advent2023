package com.rolf.day17

import com.rolf.util.Direction
import com.rolf.util.Point

data class Step(
    val location: Point,
    val direction: Direction,
    val steps: Int
)
