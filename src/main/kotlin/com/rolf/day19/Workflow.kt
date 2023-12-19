package com.rolf.day19

data class Workflow(
    val name: String,
    val rules: List<Rule>,
) {
    fun getNext(part: Part): String {
        for (rule in rules) {
            if (rule.applies(part)) {
                return rule.next
            }
        }
        return rules.last().next
    }

    fun getNext2(range: Range): List<Pair<String, Range>> {
        val result = mutableListOf<Pair<String, Range>>()

        var current = range
        for (rule in rules) {
            val (match, rest) = rule.applies2(current)
            result.add(rule.next to match)
            if (rest == null) break
            current = rest
        }

        return result
    }
}
