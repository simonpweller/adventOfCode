package y2015.day11

import resourceText

fun main() {
    val input = resourceText(2015, 11)
    println(nextValid(input))
    println(nextValid(nextValid(input)))
}

fun nextValid(password: String): String {
    var nextPassword = next(password)
    while (!isValid(nextPassword)) {
        nextPassword = next(nextPassword)
    }
    return nextPassword
}

fun next(password: String): String {
    var flippedZs = 0
    for(index in password.length -1 downTo 0) {
        if (password[index] != 'z') {
            return password.substring(0, index) + password[index].inc() + "a".repeat(flippedZs)
        } else {
            flippedZs++
        }
    }
    throw IllegalArgumentException("passed in password that can't be incremented")
}

fun isValid(password: String): Boolean {
    return containsIncreasingStraight(password)
            && !containsConfusingLetters(password)
            && containsTwoDoubleLetters(password)
}

private fun containsIncreasingStraight(password: String): Boolean {
    var straightLength = 1
    var lastChar: Char? = null
    password.forEach {
        if (it.dec() == lastChar) {
            straightLength++
            if (straightLength == 3) return true
        } else {
            straightLength = 1
        }
        lastChar = it
    }
    return false
}

private fun containsConfusingLetters(password: String) = password.contains("[iol]".toRegex())

private fun containsTwoDoubleLetters(password: String): Boolean {
    var pairs = 0
    var lastChar: Char? = null
    password.forEach {
        if (it == lastChar) {
            pairs++
            lastChar = null // avoid overlap
            if (pairs >= 2) return true
        } else {
            lastChar = it
        }
    }
    return false
}
