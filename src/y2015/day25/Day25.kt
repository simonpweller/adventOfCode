package day25

import java.io.File

fun main() {
    val input = File("src/y2015/day25/input.txt").readText()
    val targetRow = input.substringAfter("row ").substringBefore(",").toInt()
    val targetCol = input.substringAfter("column ").substringBefore(".").toInt()
    println(codeAt(targetRow, targetCol, 20151125))
}

fun codeAt(targetRow: Int, targetCol: Int, startingCode: Long): Long {
    var currentCode = startingCode
    val fullDiagonals = targetCol + targetRow - 2
    val stepsInFullDiagonals = fullDiagonals * (fullDiagonals + 1) / 2
    val totalSteps = stepsInFullDiagonals + targetCol - 1
    repeat(totalSteps) { currentCode = getNextCode(currentCode) }
    return currentCode
}

fun getNextCode(currentCode: Long): Long = currentCode * 252533 % 33554393