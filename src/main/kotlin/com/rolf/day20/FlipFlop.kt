package com.rolf.day20

data class FlipFlop(
    override val name: String,
    override val outputs: List<String>,
    var on: Boolean = false,
) : Module(name, outputs) {
    override fun send(signal: Signal): List<Signal> {
        // If a flip-flop module receives a high pulse, it is ignored and nothing happens.
        if (signal.level) return emptyList()

        // However, if a flip-flop module receives a low pulse, it flips between on and off.
        // If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
        on = !on
        return sendToAll(on)
    }
}
