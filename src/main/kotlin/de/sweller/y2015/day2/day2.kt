package de.sweller.y2015.day2

import de.sweller.resourceLines

fun main() {
    val lines = resourceLines(2015, 2)
    println(part1(lines))
    println(part2(lines))
}

fun part1(lines: List<String>): Int {
    return lines.map{ toPresent(it).wrappingPaper()}.sum()
}

fun part2(lines: List<String>): Int {
    return lines.map{ toPresent(it).ribbon()}.sum()
}

fun toPresent(line: String): Present {
    val (l, w, h) = line.split('x').map{it.toInt()}
    return Present(l, w, h)
}

class Present(private val l: Int, private val w: Int, private val h: Int) {

    fun wrappingPaper(): Int {
        return surfaceArea() + slack()
    }

    private fun surfaceArea(): Int {
        return 2*l*w + 2*w*h + 2*h*l
    }

    private fun slack(): Int {
        return listOf(l*w, w*h, h*l).min() as Int
    }

    fun ribbon(): Int {
        return wrappingRibbon() + bowRibbon()
    }

    private fun wrappingRibbon(): Int {
        return (listOf(l, w, h).sum() - listOf(l, w, h).max() as Int) * 2
    }

    private fun bowRibbon(): Int {
        return l*w*h
    }
}
