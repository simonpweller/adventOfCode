package day17

import utils.subLists
import java.io.File

fun main() {
    val lines: List<String> = File("src/y2015/day17/input.txt").readLines()
    println(combinationsOfSize150(lines).size)
    println(combinationsOfSize150WithMinimumNumberOfContainers(lines).size)
}

private fun combinationsOfSize150(lines: List<String>): List<List<Int>> {
    return subLists(lines.map { it.toInt() })
        .filter { it.sum() == 150 }
}

fun combinationsOfSize150WithMinimumNumberOfContainers(lines: List<String>): List<List<Int>> {
    val combinationsOfSize150 = combinationsOfSize150(lines)
    val minimumNumberOfContainers = combinationsOfSize150
        .map { it.size }
        .min()
    return combinationsOfSize150.filter { it.size == minimumNumberOfContainers }
}
