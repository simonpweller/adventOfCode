package de.sweller.y2019.day10

import Point
import Vector
import resourceLines
import kotlin.math.sign

fun main() {
    val asteroids = resourceLines(2019, 10).mapIndexed { y, line ->
        line.mapIndexed { x, ch -> if (ch == '#') Point(x, y) else null }
    }.flatten().filterNotNull()

    val (placement, reachableAsteroids) = bestPlacement(asteroids) ?: error("no optimal placement found")
    println(reachableAsteroids)
    println(asteroid200(asteroids, placement).run { x * 100 + y })
}

private fun asteroid200(asteroids: List<Point>, placement: Point): Point {
    val destructionVectors = destructionVectors(asteroids, placement).toMutableMap()
    var destroyed = 0
    while (true) {
        destructionVectors.entries.filter { it.value.isNotEmpty() }.forEach { entry ->
            val asteroidToDestroy = entry.value.minBy { it.manhattan(placement) } ?: error("nothing to destroy!")
            destructionVectors[entry.key] = entry.value.minus(asteroidToDestroy)
            destroyed++
            if (destroyed == 200) return asteroidToDestroy
        }
    }
}

private fun bestPlacement(asteroids: List<Point>) = asteroids.map { location ->
    Pair(
        location,
        asteroids
            .filter { it != location }
            .map { location - it }
            .map { it.reduce() }
            .toSet().size
    )}.maxBy { it.second }

private fun destructionVectors(asteroids: List<Point>, location: Point): Map<Vector, List<Point>> {
    return asteroids.filter { it != location }.groupBy { (it - location).reduce() }.toSortedMap(object: Comparator<Vector> {
        override fun compare(o1: Vector?, o2: Vector?): Int {
            if(o1 == null || o2 == null) throw IllegalArgumentException("cannot compare null vector")
            if (quadrant(o1) != quadrant(o2)) return (quadrant(o1) - quadrant(o2)).sign
            return ((o1.y.toDouble() / o1.x.toDouble()) - (o2.y.toDouble() / o2.x.toDouble())).sign.toInt()
        }

        private fun quadrant(vector: Vector): Int = when {
            vector.x == 0 && vector.y < 0 -> 1
            vector.x > 0 && vector.y < 0 -> 2
            vector.x > 0 && vector.y == 0 -> 3
            vector.x > 0 && vector.y > 0 -> 4
            vector.x == 0 && vector.y > 0 -> 5
            vector.x < 0 && vector.y > 0 -> 6
            vector.x < 0 && vector.y == 0 -> 7
            vector.x < 0 && vector.y < 0 -> 8
            else -> throw IllegalStateException("missed an option")
        }
    })
}