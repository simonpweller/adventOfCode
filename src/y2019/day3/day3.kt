package y2019.day3

import resourceLines
import kotlin.math.abs

fun main() {
    val (wire1, wire2) = resourceLines(2019, 3).map(::wirePositions)
    val intersections = wire1.map { it.toPoint() }.intersect(wire2.map { it.toPoint() })
    println(intersections.map { it.manhattan() }.min())
    println(intersections.map { intersection ->
        wire1.find { it.x == intersection.x && it.y == intersection.y }!!.steps +
        wire2.find { it.x == intersection.x && it.y == intersection.y }!!.steps
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

class WirePosition(val x: Int, val y: Int, val steps: Int) {
    fun toPoint() = Point(x, y)
}

data class Point(val x: Int, val y: Int) {
    fun manhattan() = abs(x) + abs(y)
}