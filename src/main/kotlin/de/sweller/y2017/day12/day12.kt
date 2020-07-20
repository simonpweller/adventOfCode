package de.sweller.y2017.day12

import resourceLines

fun main() {
    val pipes = resourceLines(2017, 12).fold(mapOf<Int, List<Int>>()) { map, line ->
        map.plus(Pair(
            line.substringBefore(" ").toInt(),
            line.substringAfter(" <-> ").split(", ").map { it.toInt() }
        ))
    }
    println(idsInGroup(pipes, 0).size)
    println(numberOfGroups(pipes))
}

fun numberOfGroups(pipes: Map<Int, List<Int>>): Int {
    var groups = 0
    val idsToCheck = pipes.keys.toMutableSet()
    while (idsToCheck.isNotEmpty()) {
        val nextId = idsToCheck.first()
        val connectedIds = idsInGroup(pipes, nextId)
        idsToCheck.removeAll(connectedIds)
        groups++
    }
    return groups
}

fun idsInGroup(pipes: Map<Int, List<Int>>, id: Int): List<Int> {
    val idsToCheck = mutableSetOf(id)
    val reachedIds = mutableSetOf<Int>()
    while (idsToCheck.isNotEmpty()) {
        val nextId = idsToCheck.first()
        val connectedIds = pipes.getOrDefault(nextId, listOf()).filter { !reachedIds.contains(it) }
        idsToCheck.remove(nextId)
        idsToCheck.addAll(connectedIds)
        reachedIds.add(nextId)
    }
    return reachedIds.toList()
}
