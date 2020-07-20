package de.sweller.y2015.day6

import de.sweller.resourceLines

fun main() {
    val lines = resourceLines(2015, 6)
    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val matrix: Array<IntArray> = Array(1000) { IntArray(1000) }
    lines.forEach {
        val instruction = parseLine(it)
        for (row in instruction.y0..instruction.y1) {
            for (col in instruction.x0..instruction.x1) {
                when {
                    instruction.action == Action.TURN_ON -> matrix[row][col] = 1
                    instruction.action == Action.TURN_OFF -> matrix[row][col] = 0
                    else -> matrix[row][col] = if (matrix[row][col] == 1) 0 else 1
                }
            }
        }
    }
    return matrix.map { it.sum() }.sum()
}

private fun part2(lines: List<String>): Int {
    val matrix: Array<IntArray> = Array(1000) { IntArray(1000) }
    lines.forEach {
        val instruction = parseLine(it)
        for (row in instruction.y0..instruction.y1) {
            for (col in instruction.x0..instruction.x1) {
                when {
                    instruction.action == Action.TURN_ON -> matrix[row][col]++
                    instruction.action == Action.TURN_OFF -> if (matrix[row][col] > 0) matrix[row][col]--
                    else -> matrix[row][col] += 2
                }
            }
        }
    }
    return matrix.map { it.sum() }.sum()
}

fun parseLine(line: String): Instruction {
    val action = when {
        line.startsWith("turn on") -> Action.TURN_ON
        line.startsWith("turn off") -> Action.TURN_OFF
        else -> Action.TOGGLE
    }
    val from = line.split(" ").first {it.contains(",")}
    val to = line.split(" ").last {it.contains(",")}

    val x0 = from.split(",")[0].toInt()
    val y0 = from.split(",")[1].toInt()
    val x1 = to.split(",")[0].toInt()
    val y1 = to.split(",")[1].toInt()
    return Instruction(action, x0, x1, y0, y1)
}

enum class Action {
    TOGGLE,
    TURN_ON,
    TURN_OFF,
}

data class Instruction(val action: Action, val x0: Int, val x1: Int, val y0: Int, val y1: Int)

