package y2015.day9

import permutations
import resourceLines

fun main() {
    val lines = resourceLines(2015, 9)
    val distances = parseDistances(lines)
    val routes = permutations(distances.keys.toSet())
    val routeDistances = routes.map { route ->
        var routeDistance = 0
        for (locationIndex in 1 until route.size) {
            routeDistance += distances[route[locationIndex - 1]]!![route[locationIndex]]!!
        }
        return@map routeDistance
    }
    println(routeDistances.min())
    println(routeDistances.max())
}

private fun parseDistances(lines: List<String>): MutableMap<String, MutableMap<String, Int>> {
    val distances = mutableMapOf<String, MutableMap<String, Int>>()
    lines.forEach {
        val (route, distanceString) = it.split(" = ")
        val distance = distanceString.toInt()
        val (to, from) = route.split(" to ")
        distances[to] = distances.getOrDefault(to, mutableMapOf())
        distances[to]!![from] = distance
        distances[from] = distances.getOrDefault(from, mutableMapOf())
        distances[from]!![to] = distance
    }
    return distances
}


