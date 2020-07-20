package de.sweller.y2019.day5

import resourceText
import de.sweller.y2019.IntComputer

fun main() {
    val program = resourceText(2019, 5)
    println(IntComputer(program).apply { inputs.add(1) }.run().takeOutputs().last())
    println(IntComputer(program).apply { inputs.add(5) }.run().takeOutputs().last())
}
