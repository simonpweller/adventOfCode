package y2017.day2

import subListsOfSize
import java.io.File

fun main() {
    val rows = File("src/y2017/day2/input.txt").readLines()
        .map { it.split("\\s+".toRegex()).map {num -> num.toInt()} }
    println(rows.sumBy { it.max()!! - it.min()!! })
    println(rows
        .map { subListsOfSize(it, 2)}
        .map { combination -> combination.first { it[0] % it[1] == 0 } }
        .map { it[0] / it[1] }
        .sum()
    )
}