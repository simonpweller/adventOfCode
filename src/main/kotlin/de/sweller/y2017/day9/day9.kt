package de.sweller.y2017.day9

import de.sweller.resourceText

fun main() {
    val garbageRegex = "<[^>]*>".toRegex()
    val escapedInput = resourceText(2017, 9).replace("!.".toRegex(), "")
    println(groupCount(escapedInput.replace(garbageRegex, "").replace(",", "")))
    println(garbageRegex.findAll(escapedInput).map { it.value.length - 2 }.sum())
}

fun groupCount(group: String, outerValue: Int = 1): Int =
    outerValue + innerGroups(group).map { groupCount(it, outerValue = outerValue + 1) }.sum()

fun innerGroups(group: String): List<String> {
    val groups = mutableListOf<String>()
    var currentGroup = ""
    var openBrackets = 0
    group.subSequence(1, group.lastIndex).forEach {
        currentGroup += it
        openBrackets += if (it == '{') 1 else -1
        if (openBrackets == 0) {
            groups.add(currentGroup)
            currentGroup = ""
        }
    }
    return groups
}