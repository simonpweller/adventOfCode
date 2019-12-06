package y2019.day6

import resourceLines

fun main() {
    val map = resourceLines(2019, 6).fold(mapOf<String, List<String>>()) { map, line ->
        val (planet, moon) = line.split(")")
        map.plus(Pair(planet, map.getOrDefault(planet, listOf()).plus(moon)))
    }
    println(map.keys.sumBy { objectsInOrbit(map, it) })
    println(objectsBetweenCOMAnd(map, "YOU"))
}

fun objectsInOrbit(map: Map<String, List<String>>, center: String): Int {
    val directlyInOrbit = map.getOrDefault(center, listOf())
    return directlyInOrbit.size + directlyInOrbit.map { objectsInOrbit(map, it) }.sum()
}

fun objectsBetweenCOMAnd(map: Map<String, List<String>>, center: String): List<String> {
    if (center == "COM") return listOf()
    return listOf(center).plus(objectsBetweenCOMAnd(map, map.filterValues { it.contains(center) }.keys.first()))
}
