package y2019.day3

import java.io.File
import kotlin.math.abs

fun main() {
    val lines = File("src/y2019/day3/input.txt").readLines()
    val firstWirePositions = wirePositions(lines[0])
    val secondWirePositions = wirePositions(lines[1])
    val visitedPositionsFirstWire = firstWirePositions.map { Pair(it.x, it.y) }.toSet()
    val visitedPositionsSecondWire = secondWirePositions.map { Pair(it.x, it.y) }.toSet()
    val intersections = visitedPositionsFirstWire.intersect(visitedPositionsSecondWire)
    println(intersections.map { abs(it.first) + abs(it.second) }.min())
    println(intersections.map { intersection ->
        firstWirePositions.find { it.x == intersection.first && it.y == intersection.second }!!.steps +
        secondWirePositions.find { it.x == intersection.first && it.y == intersection.second }!!.steps
    }.min())
}

private fun wirePositions(wire: String): Set<WirePosition> {
    var x = 0
    var y = 0
    var totalSteps = 0
    return wire.split(",").fold(setOf()) { acc, curr ->
        val steps = curr.drop(1).toInt()
        when (curr[0]) {
            'R' -> acc.plus((1..steps).map { WirePosition(++x, y, ++totalSteps) })
            'D' -> acc.plus((1..steps).map { WirePosition(x, --y, ++totalSteps) })
            'L' -> acc.plus((1..steps).map { WirePosition(--x, y, ++totalSteps) })
            else -> acc.plus((1..steps).map { WirePosition(x, ++y, ++totalSteps) })
        }
    }
}

class WirePosition(val x: Int, val y: Int, val steps: Int)