package y2017.day3

import java.io.File
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.system.exitProcess

fun main() {
    val input = File("src/y2017/day3/input.txt").readText().toInt()
    println(part1(input))
    part2(input)
}

fun part1(num: Int): Int {
    val squareSize = squareSize(num)
    val squareIndex = (squareSize - 1) / 2
    val positionInSquare = positionInSquare(num)
    val distanceFromMiddle = abs(positionInSquare % (squareSize - 1) - (squareSize - 1) / 2)
    return squareIndex + distanceFromMiddle
}

fun positionInSquare(num: Int): Int {
    val sizePreviousSquare = squareSize(num) - 2
    return num - sizePreviousSquare * sizePreviousSquare
}

fun squareSize(num: Int): Int {
    val ceil = ceil(sqrt(num.toDouble())).toInt()
    return if (ceil % 2 == 0) ceil + 1 else ceil
}

fun part2(input: Int): Int {
    val resultMap = mutableMapOf<Pair<Int, Int>, Int>()
    var layerSize = 0
    var x = 0
    var y = 0
    resultMap[Pair(x, y)] = 1

    fun write() {
        val sumOfNeighbors = getSumOfNeighbors(resultMap, x, y)
        resultMap[Pair(x, y)] = sumOfNeighbors
        if (sumOfNeighbors > input) {
            println(sumOfNeighbors)
            exitProcess(0)
        }
    }

    while (true) {
        layerSize++
        x++
        while (y < layerSize) {
            write()
            y++
        }
        while (x > -layerSize) {
            write()
            x--
        }
        while (y > -layerSize) {
            write()
            y--
        }
        while (x < layerSize) {
            write()
            x++
        }
        write()
    }
}

val offsets = (-1..1).flatMap { xOffset -> (-1..1).map { yOffset -> Pair(xOffset, yOffset) } }.filter { it != Pair(0, 0) }
fun getSumOfNeighbors(map: Map<Pair<Int, Int>, Int>, x: Int, y: Int): Int =
    offsets.map { map.getOrDefault(Pair(x + it.first, y + it.second), 0) }.sum()