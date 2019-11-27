package y2016.day19

import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val numberOfElves = File("src/y2016/day19/input.txt").readText().toInt()
    println(Circle(numberOfElves).getWinningElf())
}

class Circle(numberOfElves: Int) {
    private val presentCounts = CircularLinkedList((1 .. numberOfElves).toList())

    fun getWinningElf(): Int {
        var elf = presentCounts.start
        do {
            elf.removeNext()
            elf = elf.next
        } while (elf.next != elf)
        return elf.value
    }
}

class CircularLinkedList<Int>(list: List<Int>) {

    var start = (list.lastIndex downTo 0).fold(null) {last: Node?, index -> Node(list[index], last)}
        ?: throw IllegalArgumentException("linked list may not be empty")

    inner class Node(val value: Int, var link: Node? = null) {
        val next: Node
            get() = link ?: start

        fun removeNext() {
            val newLink = next.next
            if (next == start) {
                start = newLink
            }
            link = newLink
        }
    }
}