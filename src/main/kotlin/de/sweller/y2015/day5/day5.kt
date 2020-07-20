package de.sweller.y2015.day5

import de.sweller.resourceLines

fun main() {
    val lines = resourceLines(2015, 5)
    println(count(lines) { isNice1(it) })
    println(count(lines) { isNice2(it) })
}

fun count(lines: List<String>, niceties: (String) -> Boolean): Counts {
    val counts = Counts(0, 0)
    lines.forEach{if (niceties(it)) counts.nice++ else counts.naughty++}
    return counts
}

data class Counts (var nice: Int, var naughty: Int)

fun isNice1(line: String): Boolean {
    return hasThreeVowels(line) && hasDoubleLetter(line) && !hasForbiddenStrings(line)
}

fun isNice2(line: String): Boolean {
    return hasPairAppearingTwice(line) && hasPairWithSingleLetterInBetween(line)
}

private fun hasForbiddenStrings(line: String): Boolean {
    return line.contains("ab|cd|pq|xy".toRegex())
}

private fun hasDoubleLetter(line: String): Boolean {
    for(i in 1 until line.length) {
        if (line[i-1] == line[i]) return true
    }
    return false
}

private fun hasThreeVowels(line: String): Boolean {
    val vowelCount = line.chars()
        .filter { listOf('a', 'e', 'i', 'o', 'u').contains<Char>(it.toChar()) }
        .count()
    return vowelCount >= 3
}

private fun hasPairAppearingTwice(line: String): Boolean {
    for(i in 1 until line.length) {
        if (line.substring(i+1).contains(line.substring(i-1, i+1))) return true
    }
    return false
}

fun hasPairWithSingleLetterInBetween(line: String): Boolean {
    for(i in 2 until line.length) {
        if (line[i-2] == line[i]) return true
    }
    return false
}
