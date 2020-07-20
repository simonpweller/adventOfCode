package de.sweller.y2015.day24

import de.sweller.resourceLines

fun main() {
    val packages = resourceLines(2015, 24).map { it.toInt() }.reversed()
    println(part1(packages))
    println(part2(packages))
}

fun part1(packages: List<Int>): List<Int>? {
    val targetWeight = packages.sum() / 3
    var w1r: List<Int>
    var w2r: List<Int>
    var w3r: List<Int>
    var w4r: List<Int>
    var w5r: List<Int>
    val possibleSolutions = mutableListOf<List<Int>>()
    for (w1 in packages) {
        w1r = packages.minus(w1)
        for (w2 in w1r) {
            w2r = w1r.minus(w2)
            for (w3 in w2r) {
                w3r = w2r.minus(w3)
                for (w4 in w3r) {
                    w4r = w3r.minus(w4)
                    for (w5 in w4r) {
                        w5r = w4r.minus(w5)
                        for (w6 in w5r) {
                            if (w1 + w2 + w3 + w4 + w5 + w6 == targetWeight) {
                                possibleSolutions.add(listOf(w1, w2, w3, w4, w5, w6))
                            }
                        }
                    }
                }
            }
        }
    }
    return possibleSolutions.minBy {
        it.map { int -> int.toDouble() }.reduce { acc, i -> acc * i }
    }
}

fun part2(packages: List<Int>): List<Int>? {
    val targetWeight = packages.sum() / 4
    var w1r: List<Int>
    var w2r: List<Int>
    var w3r: List<Int>
    val possibleSolutions = mutableListOf<List<Int>>()
    for (w1 in packages) {
        w1r = packages.minus(w1)
        for (w2 in w1r) {
            w2r = w1r.minus(w2)
            for (w3 in w2r) {
                w3r = w2r.minus(w3)
                for (w4 in w3r) {
                    if (w1 + w2 + w3 + w4 == targetWeight) {
                        possibleSolutions.add(listOf(w1, w2, w3, w4))
                    }
                }
            }
        }
    }
    return possibleSolutions.minBy {
        it.map { int -> int.toDouble() }.reduce { acc, i -> acc * i }
    }
}

