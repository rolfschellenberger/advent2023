package com.rolf.day20

data class Conjunction(
    override val name: String,
    override val outputs: List<String>,
    var inputSignals: MutableMap<String, Boolean> = mutableMapOf(),
) : Module(name, outputs) {
    override fun send(signal: Signal): List<Signal> {
        // When a pulse is received, the conjunction module first updates its memory for that input. Then, if it
        // remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
        inputSignals[signal.from] = signal.level
        val outputLevel = !inputSignals.values.all { it }
        return sendToAll(outputLevel)
    }

    fun addInput(name: String) {
        // Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their
        // connected input modules; they initially default to remembering a low pulse for each input.
        inputSignals[name] = false
    }

    override fun toString(): String {
        return "Conjunction(name='$name', outputs=$outputs, inputSignals=$inputSignals)"
    }
}
