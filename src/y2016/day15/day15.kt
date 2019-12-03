package y2016.day15

import resourceLines

fun main() {
    val discs = resourceLines(2016, 15).map { Disc(
        it.substringAfter("#").substringBefore(" ").toInt(),
        it.substringAfter("has ").substringBefore(" positions").toInt(),
        it.substringAfter("position ").substringBefore(".").toInt()
    ) }
    println(bestTimeToDrop(discs))
    println(bestTimeToDrop(discs.plus(Disc(discs.last().num + 1, 11, 0))))
}

private fun bestTimeToDrop(discs: List<Disc>) =
    generateSequence(0) { it + 1 }.filter { time -> discs.all { disc -> disc.openForStartingTime(time) } }.first()

class Disc(val num: Int, private val positions: Int, private val startingPosition: Int) {
    fun openForStartingTime(time: Int): Boolean = (startingPosition + num + time) % positions == 0
}