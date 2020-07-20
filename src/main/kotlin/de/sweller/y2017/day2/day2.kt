package de.sweller.y2017.day2

import de.sweller.resourceLines
import subListsOfSize

fun main() {
    val rows = resourceLines(2017, 2).map { it.split("\\s+".toRegex()).map { num -> num.toInt()} }
    println(rows.sumBy { it.max()!! - it.min()!! })
    println(rows
        .map { subListsOfSize(it, 2)}
        .map { combination -> combination.first { it[0] % it[1] == 0 } }
        .map { it[0] / it[1] }
        .sum()
    )
}