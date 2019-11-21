package y2017.day5

import java.io.File

fun main() {
    val offsets = File("src/y2017/day5/input.txt").readLines().map { it.toInt() }.toMutableList()
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