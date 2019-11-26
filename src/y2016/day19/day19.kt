package y2016.day19

import InfiniteList
import java.io.File

fun main() {
    val numberOfElves = File("src/y2016/day19/input.txt").readText().toInt()
    println(Circle(numberOfElves).getWinningElf())
}

class Circle(private val numberOfElves: Int) {
    private val presentCounts = InfiniteList((1 .. numberOfElves).map { Elf(it) }.toMutableList())

    fun getWinningElf(): Int {
        var index = 0
        do {
            if (presentCounts[index].presents != 0) {
                val indexToLeft = getIndexToLeft(index)
                val presentsToSteal = presentCounts[indexToLeft].presents
                presentCounts[index].presents += presentsToSteal
                presentCounts[indexToLeft].presents = 0
            }
            index++
        } while (presentCounts[index].presents < numberOfElves)
        return presentCounts[index].number
    }

    private fun getIndexToLeft(index: Int): Int =
        generateSequence(index + 1) { it + 1}.filter { ix -> presentCounts[ix].presents != 0 }.first()
}

class Elf(val number: Int, var presents: Int = 1)