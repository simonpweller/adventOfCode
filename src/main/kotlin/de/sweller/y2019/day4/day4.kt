package de.sweller.y2019.day4

import digits
import resourceText

fun main() {
    val (lowerBound, upperBound) = resourceText(2019, 4).split("-").map {it.toInt()}
    val incrementing = (lowerBound..upperBound).map { it.digits() }.filter { it == it.sorted() }
    println(incrementing.count { digits -> digits.groupBy { it }.values.any { it.size >= 2 } })
    println(incrementing.count { digits -> digits.groupBy { it }.values.any { it.size == 2 } })
}
