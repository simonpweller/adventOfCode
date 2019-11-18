package day13

fun main() {
    println(part1(1358))
    println(part2(1358))
}

fun part1(favoriteNumber: Int): Int? {
    val map = JITMap(favoriteNumber)
    val startingCoordinates = Coordinates(1, 1)
    var step = 0
    val distances = mutableMapOf(Pair(startingCoordinates, step))
    var activeCoordinates = listOf(startingCoordinates)
    while (!distances.containsKey(Pair(31, 39))) {
        step++
        activeCoordinates = activeCoordinates.flatMap { map.accessibleNeighbors(it) }.filter { !distances.containsKey(it) }
        distances.putAll(activeCoordinates.map { Pair(it, step) })
    }
    return distances[Coordinates(31, 39)]
}

fun part2(favoriteNumber: Int): Int? {
    val map = JITMap(favoriteNumber)
    val startingCoordinates = Coordinates(1, 1)
    val distances = mutableMapOf(Pair(startingCoordinates, 0))
    var activeCoordinates = listOf(startingCoordinates)
    for (step in 1..50) {
        activeCoordinates = activeCoordinates.flatMap { map.accessibleNeighbors(it) }.filter { !distances.containsKey(it) }
        distances.putAll(activeCoordinates.map { Pair(it, step) })
    }
    return distances.keys.size
}


typealias Coordinates = Pair<Int, Int>

class JITMap(private val favoriteNumber: Int) {
    private val map = mutableMapOf<Coordinates, Boolean>()
    private val offsets: List<Pair<Int, Int>> = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

    fun accessibleNeighbors(coordinates: Coordinates): List<Coordinates> = neighbors(coordinates).filter { !isWall(it) }

    private fun neighbors(coordinates: Coordinates): List<Coordinates> = offsets
        .map { offset -> Pair(coordinates.first + offset.first, coordinates.second + offset.second) }
        .filter { it.first >= 0 && it.second >= 0 }

    private fun isWall(coordinates: Coordinates): Boolean =
        map.getOrPut(coordinates) {isWall(coordinates.first, coordinates.second, favoriteNumber)}

    private fun isWall(x: Int, y: Int, favouriteNumber: Int): Boolean {
        val num = x*x + 3*x + 2*x*y + y + y*y + favouriteNumber
        val binaryRepresentation = num.toString(2)
        val numberOfOnes = binaryRepresentation.filter { it == '1' }.count()
        return numberOfOnes % 2 != 0
    }
}
