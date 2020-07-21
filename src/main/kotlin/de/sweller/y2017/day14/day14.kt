package de.sweller.y2017.day14

import de.sweller.resourceText
import de.sweller.y2017.day10.knotHash

fun main() {
    val disk = Disk(resourceText(2017, 14))
    println(disk.occupiedBytes)
}

class Disk(key: String) {
    private val byteStrings = (0 until 128)
            .map { row ->
                knotHash("$key-$row")
                        .map { it.toString().toInt(16).toString(2).padStart(4, '0') }
                        .joinToString("")
            }

    val occupiedBytes: Int
        get() = byteStrings.fold(0) { acc: Int, row: String ->
            acc + row.filter { it == '1' }.length
        }
}