package y2017.day6

import MutableInfiniteList
import resourceText

fun main() {
    val bank = MutableInfiniteList(resourceText(2017, 6).split("\\s+".toRegex())
        .map { num -> num.toInt()}.toMutableList())
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


