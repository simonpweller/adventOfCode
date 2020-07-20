package de.sweller.y2017.day4

import charCounts
import resourceLines

fun main() {
    val passphrases = resourceLines(2017, 4).map { it.split(" ") }
    println(passphrases.filter { it.size == it.toSet().size }.size)
    println(passphrases.map { it.map(::charCounts) }.filter { it.size == it.toSet().size }.size)
}