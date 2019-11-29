package y2016.day20

import java.io.File

fun main() {
    val blockedRanges = File("src/y2016/day20/input.txt").readLines()
        .map { range -> range.split("-").map { it.toLong() } }
        .map { LongRange(it.first(), it.last()) }

    println(generateSequence(0) { it + 1}.first {blockedRanges.none { range -> range.contains(it) }})
    println((0 .. 4294967295).count {blockedRanges.none { range -> range.contains(it) }})
}