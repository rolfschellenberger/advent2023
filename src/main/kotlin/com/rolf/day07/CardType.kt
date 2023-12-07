package com.rolf.day07

enum class CardType(val value: Char) {
    A('A'),
    K('K'),
    Q('Q'),
    T('T'),
    C9('9'),
    C8('8'),
    C7('7'),
    C6('6'),
    C5('5'),
    C4('4'),
    C3('3'),
    C2('2'),
    J('J');

    companion object {
        fun fromValue(char: Char): CardType {
            for (value in entries) {
                if (value.value == char) {
                    return value
                }
            }
            throw RuntimeException("Unknown value $char")
        }
    }
}
