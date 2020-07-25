package de.sweller.y2019.day15

import de.sweller.CardinalDirection
import de.sweller.Point
import de.sweller.resourceText
import de.sweller.y2019.IntComputer
import java.util.*

fun main() {
    val droid = Droid(resourceText(2019, 15))
    droid.explore()

    droid.goTo(Point(0, 0))
    val locationOfOxygenSystem = droid.map.filterValues { it == Tile.OxygenSystem }.keys.first()
    println(droid.routeTo(locationOfOxygenSystem).size)

    droid.goTo(locationOfOxygenSystem)
    val furthestFromOxygenSystem = droid.map.keys.maxBy { droid.routeTo(it).size }!!
    println(droid.routeTo(furthestFromOxygenSystem).size)
}

private class Droid(val program: String) {
    private val intComp = IntComputer(program)
    val map = mutableMapOf<Point, Tile>()
    var robot = Point(0, 0)
    var unexplored = CardinalDirection.values().map { robot + it }.toMutableSet()

    fun explore() {
        while (unexplored.isNotEmpty()) {
            goTo(nextPointToExplore())
        }
    }

    fun goTo(point: Point) {
        routeTo(point).forEach { direction ->
            move(direction)
            processOutput(direction)
        }
    }

    private fun processOutput(direction: CardinalDirection) {
        when (intComp.takeOutput()) {
            0L -> map[robot + direction] = Tile.Wall
            1L -> {
                robot += direction
                map[robot] = Tile.Hallway
                unexplored.addAll(CardinalDirection.values().map { robot + it }.filter { !map.containsKey(it) })
            }
            2L -> {
                robot += direction
                map[robot] = Tile.OxygenSystem
                unexplored.addAll(CardinalDirection.values().map { robot + it }.filter { !map.containsKey(it) })
            }
        }
    }

    private fun move(direction: CardinalDirection) {
        intComp.addInput(
            when (direction) {
                CardinalDirection.N -> 1L
                CardinalDirection.S -> 2L
                CardinalDirection.W -> 3L
                CardinalDirection.E -> 4L
            }
        ).run()
    }

    private fun nextPointToExplore(): Point {
        val pointToExplore = unexplored.minBy { it.manhattan(robot) }
        unexplored.remove(pointToExplore)
        return pointToExplore ?: error("where do we go from here?")
    }

    fun routeTo(target: Point): List<CardinalDirection> {
        val visited = mutableSetOf<Point>()
        val position = robot.copy()
        val possibleRoutes = ArrayDeque(validMoves(Route(position, listOf()), target))
        var route = possibleRoutes.remove()
        while (route.endPoint != target) {
            visited.add(route.endPoint)
            possibleRoutes.addAll(validMoves(route, target).filter { !visited.contains(it.endPoint) })
            route = possibleRoutes.remove()
        }
        return route.route
    }

    private fun validMoves(route: Route, finalDestination: Point): List<Route> =
        CardinalDirection.values().map { Route(route.endPoint + it, route.route.plus(it)) }
            .filter { map.getOrDefault(it.endPoint, Tile.Wall) != Tile.Wall || it.endPoint == finalDestination }
}


enum class Tile {
    Wall,
    Hallway,
    OxygenSystem,
}

data class Route(val endPoint: Point, val route: List<CardinalDirection>)