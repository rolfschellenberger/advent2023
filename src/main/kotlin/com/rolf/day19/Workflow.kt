package com.rolf.day19

data class Workflow(
    val name: String,
    val rules: List<Rule>
) {
    fun getNext(part: Part): String {
        for (rule in rules) {
            if (rule.applies(part)) {
                return rule.next
            }
        }
        return rules.last().next
    }
}
