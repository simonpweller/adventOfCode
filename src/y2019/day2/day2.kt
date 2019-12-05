package y2019.day2

import resourceText
import y2019.IntComputer

fun main() {
    val program = resourceText(2019, 2).split(",").map { it.toInt() }.toIntArray()
    println(IntComputer(program.clone().apply { set(1, 12) }.apply { set(2, 2) }).run())
    println(part2(program))
}

fun part2(program: IntArray): Int {
    (0 .. 99).forEach { noun ->
        (0 .. 99).forEach { verb ->
                if (IntComputer(program.clone().apply { set(1, noun) }.apply { set(2, verb) }).run() == 19690720) {
                    return 100 * noun + verb
                }
        }
    }
    throw RuntimeException("No combination of noun and verb found that returns the correct value")
}
