package de.sweller.y2017.day1

import resourceText

fun main() {
    val input = resourceText(2017, 1)
    println(input
        .filterIndexed { index, c -> c == input[(index + 1) % input.length]}
        .sumBy { it.toString().toInt() }
    )
    println(input
        .filterIndexed { index, c -> c == input[(index + input.length / 2) % input.length] }
        .sumBy { it.toString().toInt() }
    )
}