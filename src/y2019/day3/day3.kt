package y2019.day3

import Point
import RelativeDirection
import resourceLines

fun main() {
    val (wire1, wire2) = resourceLines(2019, 3).map(::wirePositions)
    val intersections = wire1.map { it.point }.intersect(wire2.map { it.point })
    println(intersections.map { it.manhattan() }.min())
    println(intersections.map { intersection ->
        wire1.find { it.point == intersection }!!.steps + wire2.find { it.point == intersection }!!.steps
    }.min())
}

private fun wirePositions(wire: String): Set<WirePosition> {
    var point = Point(0, 0)
    var totalSteps = 0
    return wire.split(",").fold(setOf()) { acc, curr ->
        val steps = curr.drop(1).toInt()
        val direction = RelativeDirection.valueOf(curr.take(1))
        return@fold acc.plus((1 .. steps).map {
            point += direction
            totalSteps++
            return@map WirePosition(point, totalSteps)
        })
    }
}

class WirePosition(val point: Point, val steps: Int)
