package de.sweller.y2019.day12

import resourceLines
import subListsOfSize
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println(part1(resourceLines(2019, 12).map(::createMoon)))
    println(part2())
}

private fun part1(moons: List<Moon>): Int {
    val moonPairs = subListsOfSize(moons, 2).map { Pair(it.first(), it.last()) }
    repeat(1000) {
        moonPairs.forEach {
            it.first.pullTowards(it.second)
        }
        moons.forEach { it.move() }
    }
    return moons.sumBy { it.energy }
}

private fun part2(): BigInteger {
    val xCycleLength = findCycleLength(resourceLines(2019, 12).map(::createMoon)) { v3 -> v3.x }.toBigInteger()
    val yCycleLength = findCycleLength(resourceLines(2019, 12).map(::createMoon)) { v3 -> v3.y }.toBigInteger()
    val zCycleLength = findCycleLength(resourceLines(2019, 12).map(::createMoon)) { v3 -> v3.z }.toBigInteger()
    return xCycleLength.lcm(yCycleLength).lcm(zCycleLength)
}

private fun findCycleLength(moons: List<Moon>, dim: (V3) -> Int): Int {
    val moonPairs = subListsOfSize(moons, 2).map { Pair(it.first(), it.last()) }
    var steps = 0
    val initial = moons.map { dim(it.position) }
    do {
        moonPairs.forEach {
            it.first.pullTowards(it.second)
        }
        moons.forEach { it.move() }
        steps++
    } while (moons.map {dim(it.position) } != initial)
    return steps + 1
}

private fun createMoon(string: String): Moon {
    val map = string.drop(1).dropLast(1).split(", ")
            .map { Pair(it.substringBefore("="), it.substringAfter("=").toInt()) }
            .toMap()
    return Moon(V3(map["x"] ?: error("no x value"), map["y"] ?: error("no y value"), map["z"] ?: error("no z value")), V3())
}

private data class Moon(var position: V3, var velocity: V3) {
    val energy: Int
        get() = position.energy * velocity.energy

    fun pullTowards(other: Moon) {
        velocity += position.pullTowards(other.position)
    }
    fun move() {
        position += velocity
    }
}

private data class V3(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
    val energy: Int
        get() = abs(x) + abs(y) + abs(z)

    fun pullTowards(other: V3): V3 {
        return V3((other.x - this.x).sign, (other.y - this.y).sign, (other.z - this.z).sign)
    }

    operator fun plus(velocity: V3) = V3(x + velocity.x, y + velocity.y, z + velocity.z)
}

private fun BigInteger.lcm(other: BigInteger): BigInteger = this * other / this.gcd(other)