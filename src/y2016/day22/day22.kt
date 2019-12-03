package y2016.day22

import resourceLines
import subListsOfSize

fun main() {
    val nodes = resourceLines(2016, 22).drop(2).map { Node(it) }
    println(subListsOfSize(nodes, 2).count { it[0].used != 0 && it[1].available >= it[0].used })
}

class Node(node: String) {
    val x: Int = node.substringAfter("x").substringBefore("-").toInt()
    val y: Int = node.substringAfter("y").substringBefore(" ").toInt()
    val size: Int = node.substringAfter(" ").substringBefore("T").trim().toInt()
    val used: Int = node.substring(28).substringBefore("T").trim().toInt()
    val available: Int = node.substring(35).substringBefore("T").trim().toInt()
}