package de.sweller.y2019.day9

import resourceText
import de.sweller.y2019.IntComputer

fun main() {
    println(IntComputer(resourceText(2019, 9)).addInput(1).run().takeOutput())
    println(IntComputer(resourceText(2019, 9)).addInput(2).run().takeOutput())
}