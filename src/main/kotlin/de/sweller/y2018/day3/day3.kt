package de.sweller.y2018.day3

import de.sweller.Point
import de.sweller.resourceLines

fun main() {
    val claims = resourceLines(2018, 3).map(::Claim)
    println(claims.flatMap { it.squares() }.groupBy { it }.values.count { it.size >= 2 })
    println(claims.first { claim -> claims.none { claim != it && claim.overlaps(it)} }.id)
}

private class Claim(claim: String) {
    val id = claim.substringAfter("#").substringBefore(" ")
    private val left = claim.substringAfter("@ ").substringBefore(",").toInt()
    private val top = claim.substringAfter(",").substringBefore(":").toInt()
    private val right = left + claim.substringAfter(": ").substringBefore("x").toInt()
    private val bottom = top + claim.substringAfter("x").toInt()

    fun squares(): List<Point> = (left until right).map { x -> (top until bottom).map { y -> Point(x, y) } }.flatten()
    fun overlaps(other: Claim) = this.squares().intersect(other.squares()).isNotEmpty()
}
