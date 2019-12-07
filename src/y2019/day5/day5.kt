package y2019.day5

import resourceText
import y2019.IntComputer

fun main() {
    val program = resourceText(2019, 4).split(",").map { it.toInt() }.toIntArray()
    println(IntComputer(program).apply { inputs.add(1) }.apply { run() }.outputs)
    println(IntComputer(program).apply { inputs.add(5) }.apply { run() }.outputs)
}
