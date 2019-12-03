package y2019.day3

import java.io.File
import kotlin.math.abs

fun main() {
    val lines = File("src/y2019/day3/input.txt").readLines()
    val firstWirePoints = visitedPoints(lines[0])
    val secondWirePoints = visitedPoints(lines[1])
    println(firstWirePoints.intersect(secondWirePoints).map { abs(it.first) + abs(it.second) }.min())
}

private fun visitedPoints(wire: String): Set<Pair<Int, Int>> {
    var x = 0
    var y = 0
    return wire.split(",").fold(setOf()) { acc, curr ->
        val steps = curr.drop(1).toInt()
        when (curr[0]) {
            'R' -> acc.plus((1..steps).map { Pair(++x, y) })
            'D' -> acc.plus((1..steps).map { Pair(x, --y) })
            'L' -> acc.plus((1..steps).map { Pair(--x, y) })
            else -> acc.plus((1..steps).map { Pair(x, ++y) })
        }
    }
}