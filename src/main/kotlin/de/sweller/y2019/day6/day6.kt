package de.sweller.y2019.day6

import resourceLines

fun main() {
    val map = resourceLines(2019, 6).fold<String, Map<String, List<String>>>(mapOf()) { map, line ->
        val (planet, moon) = line.split(")")
        map.plus(Pair(planet, map.getOrDefault(planet, listOf()).plus(moon)))
    }
    val orbitMap = OrbitMap(map)
    println(orbitMap.objectsInOrbit())
    println(orbitMap.orbitalJumpsBetween("YOU", "SAN") - 2)
}

class OrbitMap(private val map: Map<String, List<String>>) {
    fun objectsInOrbit(): Int = map.keys.sumBy(::objectsInOrbit)

    fun orbitalJumpsBetween(obj1: String, obj2: String): Int {
        val route1 = routeToCOM(obj1)
        val route2 = routeToCOM(obj2)
        val firstSharedCenter = route1.first { route2.contains(it) }
        return route1.indexOf(firstSharedCenter) + route2.indexOf(firstSharedCenter)
    }

    private fun objectsInOrbit(obj: String): Int {
        val directlyInOrbit = map.getOrDefault(obj, listOf())
        return directlyInOrbit.size + directlyInOrbit.map(::objectsInOrbit).sum()
    }

    private fun routeToCOM(obj: String): List<String> {
        if (obj == "COM") return listOf()
        return listOf(obj).plus(routeToCOM(centerOf(obj) ?: error("No route found")))
    }

    private fun centerOf(obj: String): String? = map.entries.find { it.value.contains(obj) }?.key
}
