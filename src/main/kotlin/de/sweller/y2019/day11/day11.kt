package de.sweller.y2019.day11

import Point
import RelativeDirection
import de.sweller.resourceText
import de.sweller.y2019.IntComputer

const val FULL_BLOCK = "\u2588"

fun main() {
    println(paint(IntComputer(resourceText(2019, 11))).count())
    println(output(paint(IntComputer(resourceText(2019, 11)), mapOf(Point(0, 0) to Color.W))))
}

private fun paint(intComp: IntComputer, initial: Map<Point, Color> = mapOf()): Map<Point, Color> {
    val painted = initial.toMutableMap()
    var location = Point(0, 0)
    var direction = RelativeDirection.U
    while (!intComp.isDone) {
        intComp.addInput(if (painted.getOrDefault(location, Color.B) == Color.W) 1L else 0L)
        intComp.run()
        val (color, turn) = intComp.takeOutputs()
        painted[location] = if (color == 1L) Color.W else Color.B
        direction += if (turn == 1L) RelativeDirection.R else RelativeDirection.L
        location += direction
    }
    return painted.toMap()
}

private fun output(map: Map<Point, Color>): String {
    val xValues = map.keys.map { it.x }
    val yValues = map.keys.map { it.y }
    return (yValues.max()!! downTo yValues.min()!!).joinToString("\n") { y ->
        (xValues.min()!! .. xValues.max()!!).joinToString("") { x ->
            if (map.getOrDefault(Point(x, y), Color.B) == Color.W ) FULL_BLOCK else " "
        }
    }
}

private enum class Color {
    W,
    B
}
