package de.sweller.y2017.day15

import de.sweller.resourceLines

fun main() {
    val (aStart, bStart) = resourceLines(2017,15)
            .map { it.substringAfterLast(" ").toLong() }

    println(part1(aStart, bStart))
    println(part2(aStart, bStart))
}

private fun part1(aStart: Long, bStart: Long): Int {
    var matches = 0
    val a = Generator(aStart, 16807)
    val b = Generator(bStart, 48271)
    repeat(40_000_000) {
        a.next()
        b.next()
        if (a.checksum == b.checksum) matches++
    }
    return matches
}

private fun part2(aStart: Long, bStart: Long): Int {
    var matches = 0
    val a = Generator(aStart, 16807, 4)
    val b = Generator(bStart, 48271, 8)
    repeat(5_000_000) {
        a.nextValid()
        b.nextValid()
        if (a.checksum == b.checksum) matches++
    }
    return matches
}

class Generator(var value: Long, private val factor: Long, private val criterion: Long = 1) {
    val checksum: String
        get() = value.toString(2).takeLast(16)

    fun next() {
        value = value * factor % 2147483647
    }

    fun nextValid() {
        do {
            next()
        } while (value % criterion != 0L)
    }
}