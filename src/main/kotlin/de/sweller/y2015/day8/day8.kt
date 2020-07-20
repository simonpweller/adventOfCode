package de.sweller.y2015.day8

import resourceLines

fun main() {
    val lines = resourceLines(2015, 8)
    println(totalCharacters(lines) - charactersInMemory(lines))
    println(encodedCharacters(lines) - totalCharacters(lines))
}

fun totalCharacters(lines: List<String>): Int {
    return lines.map{it.length}.sum()
}

fun charactersInMemory(lines: List<String>): Int {
    return lines.map{ strip(it).length}.sum()
}

fun encodedCharacters(lines: List<String>): Int {
    return lines.map{ encode(it).length}.sum()
}

fun encode(line: String): String {
    return "11${line}11"
        .replace("\\\"", "1111")
        .replace("\\\\", "1111")
        .replace("\\\\x[0-9a-f]{2}".toRegex(), "11111")
}

fun strip(line: String): String {
    return line
        .substring(1, line.length - 1)
        .replace("\\\"", "1")
        .replace("\\\\", "1")
        .replace("\\\\x[0-9a-f]{2}".toRegex(), "1")
}
