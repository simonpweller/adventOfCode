package y2019.day11

import Point
import RelativeDirection
import resourceText
import y2019.IntComputer

fun main() {
    val intComp = IntComputer(resourceText(2019, 11))
    val paintedWhite = mutableSetOf<Point>()
    val painted = mutableSetOf<Point>()
    var location = Point(0, 0)
    var direction = RelativeDirection.U
    while (!intComp.isDone) {
        intComp.addInput(if (paintedWhite.contains(location)) 1L else 0L)
        intComp.run()
        val (color, turn) = intComp.takeOutputs()
        if (color == 1L) paintedWhite.add(location) else paintedWhite.remove(location)
        painted.add(location)
        direction += if (turn == 1L) RelativeDirection.R else RelativeDirection.L
        location += direction
    }
    println(painted.size)
}