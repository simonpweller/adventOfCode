package y2019.day1

import java.io.File

fun main() {
    val moduleWeights = File("src/y2019/day1/input.txt").readLines().map { it.toInt() }
    println(moduleWeights.sumBy(::fuel))
    println(moduleWeights.sumBy(::totalFuel))
}

private fun fuel(mass: Int) = mass / 3 - 2
private fun totalFuel(mass: Int): Int = generateSequence(fuel(mass), ::fuel).takeWhile { it > 0 }.sum()
