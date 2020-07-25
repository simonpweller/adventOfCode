package de.sweller.y2017.day17

import de.sweller.resourceText

fun main() {
    val stepSize = resourceText(2017, 17).toInt()
    println(part1(stepSize))
    println(part2(stepSize))
}

fun part1(stepSize: Int): Int {
    val list = CircularLinkedList(0)
    (1 .. 2017).forEach {
        repeat(stepSize) { list.step() }
        list.insert(it)
    }
    list.step()
    return list.current.value
}

fun part2(stepSize: Int): Int {
    var currentPosition = 0
    var valueAfter0 = 0
    (1 .. 50_000_000).forEach {
        currentPosition = (currentPosition + stepSize) % it
        if (currentPosition == 0) valueAfter0 = it
        currentPosition++
    }
    return valueAfter0
}


class CircularLinkedList(initialValue: Int) {
    var start = Node(initialValue)
    var current = start
    val list = mutableListOf(start)

    fun step() {
        current = current.next
    }

    fun insert(value: Int) {
        val node = Node(value, current.link)
        current.link = node
        step()
    }

    inner class Node(val value: Int, var link: Node? = null) {
        val next: Node
            get() {
                return link ?: start
            }
    }
}