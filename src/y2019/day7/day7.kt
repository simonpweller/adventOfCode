package y2019.day7

import permutations
import resourceText
import y2019.IntComputer

fun main() {
    val program = resourceText(2019, 7).split(",").map { it.toInt() }.toIntArray()
    println(permutations((0 .. 4).toSet()).map { getThrust(it, program) }.max())
}

private fun getThrust(config: List<Int>, program: IntArray): Int =
    config.fold(0) { acc, curr ->
        IntComputer(program).apply { inputs.add(curr) }.apply { inputs.add(acc) }.apply { run() }.outputs.first()
    }