package day1

import java.io.File

fun main() {
    val input = File("src/y2015/day1/input.txt").readText()
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
