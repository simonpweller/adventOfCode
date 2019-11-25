package day20

import kotlin.math.sqrt

fun main() {
    var num = 1
    while (numberOfPresentsPart1(num) < 36000000) num++
    println(num)

    num = 1
    while (numberOfPresentsPart2(num) < 36000000) num++
    println(num)
}

fun numberOfPresentsPart1(num: Int): Int = evenDivisors(num).map { it * 10 }.sum()
fun numberOfPresentsPart2(num: Int): Int = evenDivisors(num).filter { it * 50 >= num }.map { it * 11 }.sum()

fun evenDivisors(num: Int): List<Int> {
    val lowDivisors = (1..sqrt(num.toDouble()).toInt()).filter {num % it == 0}
    val highDivisors = lowDivisors.map { num / it }
    return (lowDivisors + highDivisors).toSet().toList()
}

