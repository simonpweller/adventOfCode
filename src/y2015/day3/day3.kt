package y2015.day3

import resourceText

fun main() {
    val input = resourceText(2015, 3)
    println(part1(input))
    println(part2(input))
}

fun part1(directions: String): Int {
    val visitedHouses = mutableSetOf(House(0, 0))
    val santa = Santa()
    directions.forEach {
        santa.move(it)
        visitedHouses.add(santa.currentHouse())
    }
    return visitedHouses.size
}

fun part2(directions: String): Int {
    val visitedHouses = mutableSetOf(House(0, 0))
    val santa = Santa()
    val roboSanta = Santa()
    directions.withIndex().forEach { (index, direction) ->
        if (index % 2 == 1) {
            santa.move(direction)
            visitedHouses.add(santa.currentHouse())
        } else {
            roboSanta.move(direction)
            visitedHouses.add(roboSanta.currentHouse())
        }
    }
    return visitedHouses.size
}

data class House(val x: Int, val y: Int)

class Santa(private var x: Int = 0, private var y: Int = 0) {
    fun move(direction: Char) {
        when (direction) {
            '>' -> x++
            '<' -> x--
            '^' -> y++
            'v' -> y--
        }
    }

    fun currentHouse(): House {
        return House(x, y)
    }
}