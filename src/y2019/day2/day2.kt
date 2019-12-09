package y2019.day2

import resourceText
import y2019.IntComputer

fun main() {
    val program = resourceText(2019, 2)
    println(part1(program))
    println(part2(program))
}

private fun part1(program: String) = IntComputer(program).setNoun(12).setVerb(2).run().readMemory0()

private fun part2(program: String): Int {
    (0 .. 99).forEach { noun ->
        (0 .. 99).forEach { verb ->
                if (IntComputer(program).setNoun(noun).setVerb(verb).run().readMemory0() == 19690720) {
                    return 100 * noun + verb
                }
        }
    }
    throw RuntimeException("No combination of noun and verb found that returns the correct value")
}
