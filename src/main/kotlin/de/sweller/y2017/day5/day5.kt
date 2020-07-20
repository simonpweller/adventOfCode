package de.sweller.y2017.day5

import de.sweller.resourceLines

fun main() {
    val offsets = resourceLines(2017, 5).map { it.toInt() }.toMutableList()
    println(countSteps(offsets))
    println(countSteps(offsets, isPart2 = true))
}

fun countSteps(offsets: List<Int>, isPart2: Boolean = false): Int {
    val mutableOffsets = offsets.toMutableList()
    var index = 0
    var steps = 0
    while (index < mutableOffsets.size) {
        val offset = mutableOffsets[index]
        mutableOffsets[index] += if (isPart2 && offset >= 3) -1 else 1
        index += offset
        steps++
    }
    return steps
}