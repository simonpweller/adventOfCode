package de.sweller.y2017.day13

import de.sweller.resourceLines

fun main() {
    val scannerRanges = resourceLines(2017, 13)
            .map { Pair(it.substringBefore(":").toInt(), it.substringAfter(": ").toInt()) }
            .toMap()

    println(getSeverity(scannerRanges.mapValues { Scanner(it.value) }))
    println(getOptimalDelay(scannerRanges))
}

class ScannerInterval(val depth: Int, val interval: Int)

fun getOptimalDelay(scannerRanges: Map<Int, Int>): Int {
    val scannerIntervals = scannerRanges.map { ScannerInterval(depth = it.key, interval = it.value * 2 - 2) }
    val sequence = generateSequence(0) { it + 1 }
    return sequence.filter { delay ->
        scannerIntervals.all { scanner -> (delay + scanner.depth) % scanner.interval != 0 }
    }.first()
}

fun getSeverity(scanners: Map<Int, Scanner>): Int {
    var severity = 0
    val maxDepth = scanners.keys.max()!!

    (0..maxDepth).forEach { depth ->
        val scanner = scanners[depth]
        if (scanner != null && scanner.currentPosition == 0) severity += depth * scanner.range
        scanners.forEach { it.value.move() }
    }

    return severity
}

class Scanner(val range: Int) {
    var currentPosition = 0
    var momentum = 1

    fun move() {
        currentPosition += momentum
        if (currentPosition == 0 || currentPosition == range - 1) momentum *= -1
    }
}