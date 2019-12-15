package y2019.day15

import CardinalDirection
import Point
import resourceText
import y2019.IntComputer
import java.util.*

fun main() {
    val exploration = Exploration(resourceText(2019, 15))
    while (exploration.unexplored.isNotEmpty()) {
        exploration.explore()
    }

    exploration.goTo(Point(0, 0))
    val locationOfOxygenSystem = exploration.map.filterValues { it == Tile.OxygenSystem }.keys.first()
    println(exploration.navigate(locationOfOxygenSystem).size)
    exploration.goTo(locationOfOxygenSystem)
    val furthestFromOxygenSystem = exploration.map.keys.maxBy { exploration.navigate(it).size }!!
    println(exploration.navigate(furthestFromOxygenSystem).size)
}

private class Exploration(val program: String) {
    private val intComp = IntComputer(program)
    val map = mutableMapOf<Point, Tile>()
    var robot = Point(0, 0)
    var unexplored = CardinalDirection.values().map { robot + it }.toMutableSet()

    fun explore() {
        goTo(nextPointToExplore())
    }

    fun goTo(point: Point) {
        navigate(point).forEach { direction ->
            intComp.addInput(
                when (direction) {
                    CardinalDirection.N -> 1L
                    CardinalDirection.S -> 2L
                    CardinalDirection.W -> 3L
                    CardinalDirection.E -> 4L
                }
            ).run()
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
    }

    private fun nextPointToExplore(): Point {
        val pointToExplore = unexplored.minBy { it.manhattan(robot) }
        unexplored.remove(pointToExplore)
        return pointToExplore ?: error("where do we go from here?")
    }

    fun navigate(target: Point): List<CardinalDirection> {
        val visited = mutableSetOf<Point>()
        val position = robot.copy()
        val possibleRoutes = ArrayDeque(moves(Route(position, listOf()), target))
        var route = possibleRoutes.remove()
        while (route.endPoint != target) {
            visited.add(route.endPoint)
            possibleRoutes.addAll(moves(route, target).filter { !visited.contains(it.endPoint) })
            route = possibleRoutes.remove()
        }
        return route.route
    }

    private fun moves(route: Route, finalDestination: Point): List<Route> = CardinalDirection.values().map { Route(route.endPoint + it, route.route.plus(it)) }
        .filter { map.getOrDefault(it.endPoint, Tile.Wall) != Tile.Wall || it.endPoint == finalDestination }
}


enum class Tile {
    Wall,
    Hallway,
    OxygenSystem,
}

data class Route(val endPoint: Point, val route: List<CardinalDirection>)