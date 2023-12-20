package com.rolf.day20

data class Broadcaster(
    override val name: String,
    override val outputs: List<String>,
) : Module(name, outputs) {
    override fun send(signal: Signal): List<Signal> {
        return sendToAll(signal.level)
    }
}
