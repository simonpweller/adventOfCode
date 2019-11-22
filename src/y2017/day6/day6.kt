package y2017.day6

import MutableInfiniteList
import java.io.File

fun main() {
    val bank = MutableInfiniteList(
        File("src/y2017/day6/input.txt").readText().split("\\s+".toRegex()).map { num -> num.toInt()}.toMutableList()
    )
    val seenBanks = mutableMapOf<List<Int>, Int>()
    var redistributions = 0
    while (!seenBanks.containsKey(bank.toList())) {
        seenBanks[bank.toList()] = redistributions
        bank.redistribute()
        redistributions++
    }
    println(redistributions)
    println(redistributions - seenBanks.getOrDefault(bank.toList(), 0))
}

fun MutableInfiniteList<Int>.redistribute() {
    var pointsToRedistribute = this.max() ?: 0
    var index = this.indexOfFirst { it == pointsToRedistribute }
    this[index] = 0
    while (pointsToRedistribute > 0) {
        index++
        this[index]++
        pointsToRedistribute--
    }
}


