package de.sweller.y2015.day25

import resourceText

fun main() {
    val input = resourceText(2015, 25)
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