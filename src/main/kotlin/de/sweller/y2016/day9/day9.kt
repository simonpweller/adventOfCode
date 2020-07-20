package de.sweller.y2016.day9

import de.sweller.resourceText

fun main() {
    val input = resourceText(2016, 9)
    println(decompress(input))
    println(decompress(input, true))
}

fun decompress(input: String, isVersionTwo: Boolean = false): Long {
    if (!input.contains("(")) {
        return input.length.toLong()
    }
    val marker = input.substringAfter("(").substringBefore(")")
    val (characters, repetitions) = marker.split("x").map { it.toInt() }

    val sizeOfMarkerData = if (isVersionTwo) {
        decompress(input.substringAfter(")").substring(0, characters), isVersionTwo)
    } else {
        characters.toLong()
    }

    return input.indexOfFirst { it == '(' } +
            (sizeOfMarkerData * repetitions) +
            decompress(input.substringAfter(")").substring(characters), isVersionTwo)
}
