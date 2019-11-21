package y2017.day4

import charCounts
import java.io.File

fun main() {
    val passphrases = File("src/y2017/day4/input.txt").readLines().map { it.split(" ") }
    println(passphrases.filter { it.size == it.toSet().size }.size)
    println(passphrases.map { it.map(::charCounts) }.filter { it.size == it.toSet().size }.size)
}