package y2019.day7

import permutations
import resourceText
import y2019.IntComputer

fun main() {
    val program = resourceText(2019, 7).split(",").map { it.toInt() }.toIntArray()
    println(permutations((0 .. 4).toSet()).map { part1(it, program) }.max())
    println(permutations((5 .. 9).toSet()).map { part2(it, program) }.max())
}

private fun part1(config: List<Int>, program: IntArray): Int {
    val amplifiers = config.map { IntComputer(program).addInput(it) }
    return amplifiers.fold(0) { acc, curr ->
        curr.addInput(acc).run().outputs.first()
    }
}

private fun part2(config: List<Int>, program: IntArray): Int {
    val amplifiers = config.map { IntComputer(program).addInput(it) }
    var output = listOf(0)
    while (!amplifiers.last().isDone) {
        output = amplifiers.fold(output) { acc, curr ->
            curr.addInput(acc).run().outputs.also { curr.outputs = mutableListOf() }
        }
    }
    return output.first()
}