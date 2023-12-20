package com.rolf.day20

open class Module(
    open val name: String,
    open val outputs: List<String>,
) {
    open fun send(signal: Signal): List<Signal> {
        throw NotImplementedError("Not implemented")
    }

    fun sendToAll(signalLevel: Boolean): List<Signal> {
        return outputs.map {
            Signal(signalLevel, name, it)
        }
    }
}
