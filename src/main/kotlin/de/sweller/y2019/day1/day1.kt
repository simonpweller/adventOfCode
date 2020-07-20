package de.sweller.y2019.day1

import resourceLines

fun main() {
    val moduleWeights = resourceLines(2019, 1).map { it.toInt() }
    println(moduleWeights.sumBy(::fuel))
    println(moduleWeights.sumBy(::totalFuel))
}

private fun fuel(mass: Int) = mass / 3 - 2
private fun totalFuel(mass: Int): Int = generateSequence(fuel(mass), ::fuel).takeWhile { it > 0 }.sum()
