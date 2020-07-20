package de.sweller.y2016.day3

import resourceLines

fun main() {
    val lines = resourceLines(2016, 3)
        .map { line -> line.trim().split("\\s+".toRegex()).map { it.toInt() } }

    val triangles = lines.map { Triple(it[0], it[1], it[2]) }
    println(triangles.filter { isValid(it) }.size)

    val verticalTriangles = mutableListOf<Triple<Int, Int, Int>>()
    (lines.indices step 3).forEach {lineIndex ->
        (0 until 3).forEach {columnIndex ->
            verticalTriangles.add(Triple(lines[lineIndex][columnIndex], lines[lineIndex + 1][columnIndex], lines[lineIndex + 2][columnIndex]))
        }
    }
    println(verticalTriangles.filter { isValid(it) }.size)
}

fun isValid(triangle: Triple<Int, Int, Int>): Boolean = when {
    triangle.first + triangle.second <= triangle.third -> false
    triangle.first + triangle.third <= triangle.second -> false
    triangle.second + triangle.third <= triangle.first -> false
    else -> true
}