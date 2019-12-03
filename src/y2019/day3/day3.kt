package y2019.day3

import java.io.File

fun main() {
    val lines = File("src/y2019/day3/input.txt").readLines()
    val firstWirePoints = visitedPoints(lines[0])
    val secondWirePoints = visitedPoints(lines[1])
    println(firstWirePoints.intersect(secondWirePoints))
}

private fun visitedPoints(wire: String): Set<Pair<Int, Int>> {
    var x = 0
    var y = 0
    return wire.split(",").fold(setOf()) { acc, curr ->
        val steps = curr.drop(1).toInt()
        when (curr[0]) {
            'R' -> {
                val newAcc = acc.plus((1..steps).map { Pair(x + it, y) })
                x += steps
                newAcc
            }
            'D' -> {
                val newAcc = acc.plus((1..steps).map { Pair(x, y - it) })
                y -= steps
                newAcc
            }
            'L' -> {
                val newAcc = acc.plus((1..steps).map { Pair(x - it, y) })
                x -= steps
                newAcc
            }
            else -> {
                val newAcc = acc.plus((1..steps).map { Pair(x, y + it) })
                y += steps
                newAcc
            }
        }
    }
}