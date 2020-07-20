package de.sweller.y2016.day19

import resourceText

fun main() {
    val numberOfElves = resourceText(2016, 19).toInt()
    println(Circle(numberOfElves).getWinningElfPart1())
    println(Circle(numberOfElves).getWinningElfPart2())
}

class Circle(private val numberOfElves: Int) {
    private val presentCounts = CircularLinkedList((1 .. numberOfElves).toList())

    fun getWinningElfPart1(): Int {
        var elf = presentCounts.start
        do {
            elf.removeNext()
            elf = elf.next
        } while (elf.next != elf)
        return elf.value
    }

    fun getWinningElfPart2(): Int {
        var elf = presentCounts.start
        repeat(numberOfElves / 2 - 1) {elf = elf.next} // jump to first elf to remove
        var skipAnElf = false
        while (elf != elf.next) {
            if (skipAnElf) elf = elf.next
            elf.link = elf.next.next
            skipAnElf = !skipAnElf
        }
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
            link = next.next
        }
    }
}