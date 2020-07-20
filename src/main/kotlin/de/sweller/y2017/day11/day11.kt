package de.sweller.y2017.day11

import de.sweller.resourceText
import kotlin.math.abs
import kotlin.math.max

fun main() {
    val directions = resourceText(2017, 11).toUpperCase().split(",").map { Direction.valueOf(it) }
    println(directions.fold(Coordinate(0, 0, 0)) { c: Coordinate, d: Direction -> c + d }.distanceFromOrigin)
    println(directions.fold(Result(Coordinate(0, 0, 0), 0)) { r: Result, d: Direction ->
        val nextCoordinate = r.coordinate + d
        Result(nextCoordinate, max(r.distanceFromOrigin, nextCoordinate.distanceFromOrigin))
    }.distanceFromOrigin)
}

class Result(val coordinate: Coordinate, val distanceFromOrigin: Int)

class Coordinate(private val x: Int, private val y: Int, private val z: Int) {
    val distanceFromOrigin: Int
        get() = listOf(x, y, z).map(::abs).max()!!

    operator fun plus(direction: Direction): Coordinate {
        return when (direction) {
            Direction.N -> Coordinate(x, y + 1, z - 1)
            Direction.NE -> Coordinate(x + 1, y, z - 1)
            Direction.SE -> Coordinate(x + 1, y - 1, z)
            Direction.S -> Coordinate(x, y - 1, z + 1)
            Direction.SW -> Coordinate(x - 1, y, z + 1)
            Direction.NW -> Coordinate(x - 1, y + 1, z)
        }
    }
}

enum class Direction {
    N, NE, SE, S, SW, NW
}