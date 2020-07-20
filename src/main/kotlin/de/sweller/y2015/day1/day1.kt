package de.sweller.y2015.day1

import de.sweller.resourceText

fun main() {
    val input = resourceText(2015, 1)
    println(part1(input))
    println(part2(input))
}

fun part1(input: String): Int {
    var floor = 0
    input.forEach { if (it == '(') floor++ else floor-- }
    return floor
}

fun part2(input: String): Int? {
    var floor = 0
    for((index, char) in input.withIndex()) {
        if (char == '(') floor++ else floor--
        if (floor == -1) return index + 1
    }
    return null
}
