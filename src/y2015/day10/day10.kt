package y2015.day10

import resourceText

fun main() {
    val input = resourceText(2015, 10)
    println(lookAndSayNTimes(input, 40).length)
    println(lookAndSayNTimes(input, 50).length)
}

fun lookAndSayNTimes(input: String, times: Int): String {
    var currentString = input
    for (i in 0 until times) {
        currentString = lookAndSay(currentString)
    }
    return currentString
}

fun lookAndSay(input: String): String {
    val results = mutableListOf<String>()
    var currentDigit = input[0]
    var currentCount = 0

    input.forEach {
        if (it == currentDigit) {
            currentCount++
        } else {
            results.add("$currentCount$currentDigit")
            currentDigit = it
            currentCount = 1
        }
    }
    results.add("$currentCount$currentDigit")
    return results.joinToString("")
}