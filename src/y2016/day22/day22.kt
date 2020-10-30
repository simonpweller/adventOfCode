package y2016.day22

import resourceLines
import subListsOfSize

fun main() {
    val nodeLines = resourceLines(2016, 22).drop(2)
    println(part1(nodeLines))
}

private fun part1(nodeLines: List<String>) =
    subListsOfSize(nodeLines.map {Node(it)}, 2).count { it[0].used != 0 && it[1].available >= it[0].used }

private data class Node(val used: Int, val available: Int) {
    constructor(node: String): this(
        node.substring(28).substringBefore("T").trim().toInt(),
        node.substring(35).substringBefore("T").trim().toInt()
    )
}
