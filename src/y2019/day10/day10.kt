package y2019.day10

import Point
import resourceLines

fun main() {
    val asteroids = resourceLines(2019, 10).mapIndexed { y, line ->
        line.mapIndexed { x, ch -> if (ch == '#') Point(x, y) else null }
    }.flatten().filterNotNull()

    println(part1(asteroids))
}

private fun part1(asteroids: List<Point>): Int? =
    asteroids.map { location ->
        asteroids
            .filter { it != location }
            .map { location - it }
            .map { it.reduce() }
            .toSet().size
    }.max()