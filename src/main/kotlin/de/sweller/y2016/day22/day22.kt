package de.sweller.y2016.day22

import Point
import resourceLines
import subListsOfSize
import java.util.*

fun main() {
    val nodeLines = resourceLines(2016, 22).drop(2)
    println(part1(nodeLines))
    println(part2(nodeLines))
}

private fun part2(nodeLines: List<String>): Int {
    val nodeMap = createNodeMap(nodeLines)
    val maxX = nodeMap.keys.map { it.x }.max() ?: error("no max for x found")
    val queue = PriorityQueue<GameState>(GameStateComparator())
    queue.add(GameState(nodeMap, 0, Point(maxX, 0)))
    while (queue.peek().dataLocation != Point(0, 0)) {
        queue.addAll(getNextStates(queue.remove()))
    }
    return queue.peek().moves
}

private fun part1(nodeLines: List<String>) =
    subListsOfSize(nodeLines.map {Node(it)}, 2).count { it[0].used != 0 && it[1].available >= it[0].used }

private fun getNextStates(gameState: GameState): List<GameState> {
    val nodeMap = gameState.nodeMap
    return findMoves(nodeMap).map {move ->
        val dest = nodeMap[move.to] ?: error("destination does not exist")
        val source = nodeMap[move.from] ?: error("source does not exist")
        val newDest = Node(dest.used + source.used, dest.available - source.used)
        val newSource = Node(0, source.available + source.used)
        val dataLocation = if (move.from == gameState.dataLocation) move.to else gameState.dataLocation
        GameState(nodeMap.plus(Pair(move.to, newDest)).plus(Pair(move.from, newSource)), gameState.moves + 1, dataLocation)
    }
}

private fun createNodeMap(nodeLines: List<String>): Map<Point, Node> =
    nodeLines.fold(mapOf()) { map, nodeLine ->
        val point = Point(
            nodeLine.substringAfter("x").substringBefore("-").toInt(),
            nodeLine.substringAfter("y").substringBefore(" ").toInt()
        )
        map.plus(Pair(point, Node(nodeLine)))
}

private fun findMoves(nodeMap: Map<Point, Node>): List<Move> =
    nodeMap.entries.flatMap { (point, node) ->
        point.neighbors
            .map { neighbour -> Move(point, neighbour) }
            .filter { (nodeMap[it.to] ?: return@filter false).available > node.used }
    }

private data class Node(val used: Int, val available: Int) {
    constructor(node: String): this(
        node.substring(28).substringBefore("T").trim().toInt(),
        node.substring(35).substringBefore("T").trim().toInt()
    )
}

private data class Move(val from: Point, val to: Point)
private data class GameState(val nodeMap: Map<Point, Node>, val moves: Int, val dataLocation: Point)

private class GameStateComparator: Comparator<GameState> {
    override fun compare(o1: GameState, o2: GameState): Int =
        (o1.moves + o1.dataLocation.manhattan()) - (o2.moves + o2.dataLocation.manhattan())
}