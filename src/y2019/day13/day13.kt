package y2019.day13

import resourceText
import y2019.IntComputer

fun main() {
    println(part1(resourceText(2019, 13)))
}

private fun part1(program: String): Int {
    val intComp = IntComputer(program)
    val screen = mutableMapOf<Pair<Long, Long>, Long>()
    intComp.run().takeOutputs().chunked(3).forEach { screen[Pair(it[0], it[1])] = it[2] }
    return screen.values.count { it == 2L }
}