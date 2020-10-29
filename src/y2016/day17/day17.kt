package y2016.day17

import resourceText
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.math.max

fun main() {
    val input = resourceText(2016, 17)
    val initialState = State(input, Position(0, 0))

    println(part1(initialState, input))
    println(part2(initialState, input))
}

fun part2(initialState: State, input: String): Int {
    val comparator: Comparator<State> = compareBy { it.passcode.length }
    val queue = PriorityQueue<State>(comparator)
    queue.add(initialState)

    var longest = 0

    while (queue.isNotEmpty()) {
        val state = queue.poll()
        if (state.position == Position(3, 3)) {
            longest = max(longest, state.passcode.length - input.length)
        } else {
            queue.addAll(state.nextStates)
        }
    }

    return longest
}

private fun part1(initialState: State, input: String): String {
    val comparator: Comparator<State> = compareBy { it.passcode.length }
    val queue = PriorityQueue<State>(comparator)
    queue.add(initialState)

    while (queue.isNotEmpty() && queue.peek().position != Position(3, 3)) {
        val state = queue.poll()
        queue.addAll(state.nextStates)
    }

    return queue.poll().passcode.substring(input.length)
}

fun openDoors(input: String): List<Direction> {
    val hash = md5(input)
    return Direction.values().mapIndexedNotNull { index, direction ->
        if (listOf('b', 'c', 'd', 'e', 'f').contains(hash[index])) direction else null
    }
}

data class State(val passcode: String, val position: Position) {
    val nextStates: List<State>
        get() = openDoors(passcode)
            .filter { direction ->
                when (direction) {
                    Direction.D -> position.y < 3
                    Direction.U -> position.y > 0
                    Direction.L -> position.x > 0
                    Direction.R -> position.x < 3
                }
            }.map { direction ->
                State(passcode + direction, position + direction)
            }
}

data class Position(val x: Int, val y: Int) {
    operator fun plus(direction: Direction): Position = when (direction) {
        Direction.U -> copy(y = y - 1)
        Direction.D -> copy(y = y + 1)
        Direction.L -> copy(x = x - 1)
        Direction.R -> copy(x = x + 1)
    }
}

enum class Direction {
    U,
    D,
    L,
    R
}

fun md5(input: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    return String.format("%032x", BigInteger(1, md5.digest(input.toByteArray())))
}

