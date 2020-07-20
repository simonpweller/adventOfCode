package de.sweller.y2016.day1

import de.sweller.resourceText
import kotlin.math.abs

fun main() {
    val instructions = resourceText(2016, 1).split(", ").map{ Instruction(it) }
    println(part1(instructions))
    println(part2(instructions))
}

private fun part2(instructions: List<Instruction>): Int {
    val player = Player()
    val visitedLocations = mutableSetOf<Pair<Int, Int>>()
    instructions.forEach {
        player.turn(it.direction)
        repeat(it.distance) {
            val beenHere = !visitedLocations.add(Pair(player.north, player.east))
            if (beenHere) return player.totalDistance
            player.step()
        }
    }
    throw IllegalArgumentException("Never visited the same place twice")
}

private fun part1(instructions: List<Instruction>): Int {
    val player = Player()
    instructions.forEach {
        player.turn(it.direction)
        repeat(it.distance) { player.step() }
    }
    return player.totalDistance
}

data class Player(var east: Int = 0, var north: Int = 0, var facing: CardinalDirection = CardinalDirection.N) {
    val totalDistance: Int
    get() = abs(north) + abs(east)

    fun turn(relativeDirection: RelativeDirection) {
        facing = facing.turn(relativeDirection)
    }

    fun step() {
        when(facing) {
            CardinalDirection.N -> north++
            CardinalDirection.E -> east++
            CardinalDirection.S -> north--
            CardinalDirection.W -> east--
        }
    }
}

class Instruction(string: String) {
    val direction: RelativeDirection = if (string[0] == 'L') RelativeDirection.L else RelativeDirection.R
    val distance: Int = string.substring(1).toInt()
}

enum class RelativeDirection {
    L,
    R
}

enum class CardinalDirection {
    N,
    E,
    S,
    W;

    fun turn(relativeDirection: RelativeDirection): CardinalDirection =
        if (relativeDirection == RelativeDirection.L) turnLeft() else turnRight()

    private fun turnRight(): CardinalDirection = when(this) {
        N -> E
        E -> S
        S -> W
        W -> N
    }

    private fun turnLeft(): CardinalDirection = when(this) {
        N -> W
        E -> N
        S -> E
        W -> S
    }
}

