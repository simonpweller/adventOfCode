package y2019.day9

import resourceText
import y2019.IntComputer

fun main() {
    println(IntComputer(resourceText(2019, 9)).addInput(1).run().outputs.first())
    println(IntComputer(resourceText(2019, 9)).addInput(2).run().outputs.first())
}