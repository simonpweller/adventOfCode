package y2017.day1

import java.io.File

fun main() {
    val input = File("src/y2017/day1/input.txt").readText()
    println(input
        .filterIndexed { index, c -> c == input[(index + 1) % input.length]}
        .sumBy { it.toString().toInt() }
    )
    println(input
        .filterIndexed { index, c -> c == input[(index + input.length / 2) % input.length] }
        .sumBy { it.toString().toInt() }
    )
}