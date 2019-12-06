package y2018.day1

import resourceLines

fun main() {
    val ints = resourceLines(2018, 1).map { it.toInt() }
    println(ints.sum())
    println(part2(ints))
}

fun part2(ints: List<Int>): Int {
    val set = mutableSetOf<Int>()
    var current = 0
    while (true) {
        ints.forEach {
            if (set.contains(current)) return current
            set.add(current)
            current += it
        }
    }
}