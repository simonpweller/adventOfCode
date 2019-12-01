package y2019.day1

import java.io.File

fun main() {
    val moduleWeights = File("src/y2019/day1/input.txt").readLines().map { it.toInt() }
    println(moduleWeights.sumBy { naiveFuelRequired(it) })
    println(moduleWeights.sumBy { fuelRequired(it) })
}

private fun fuelRequired(mass: Int): Int =
    generateSequence(naiveFuelRequired(mass)) { naiveFuelRequired(it)}.takeWhile { it > 0 }.sum()

private fun naiveFuelRequired(mass: Int) = mass / 3 - 2