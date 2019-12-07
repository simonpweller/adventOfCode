package y2019.day5

import resourceText
import y2019.IntComputer

fun main() {
    val program = resourceText(2019, 4).split(",").map { it.toInt() }.toIntArray()
    println(IntComputer(program).apply { inputs.add(1) }.run().outputs.last())
    println(IntComputer(program).apply { inputs.add(5) }.run().outputs.last())
}
