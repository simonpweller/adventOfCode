package day8

import java.io.File

fun main() {
    val screen = Screen(50, 6)
    File("src/day8.txt").forEachLine { line ->
        when {
            line.startsWith("rect") -> {
                val wide = line.substringAfter(" ").substringBefore("x").toInt()
                val tall = line.substringAfter("x").toInt()
                screen.rect(wide, tall)
            }
            line.startsWith("rotate column") -> {
                val colIndex = line.substringAfter("x=").substringBefore(" ").toInt()
                val shifts = line.substringAfter("by ").toInt()
                screen.rotateCol(colIndex, shifts)
            }
            line.startsWith("rotate row") -> {
                val rowIndex = line.substringAfter("y=").substringBefore(" ").toInt()
                val shifts = line.substringAfter("by ").toInt()
                screen.rotateRow(rowIndex, shifts)
            }
        }
    }
    println(screen.checkCapacity())
    screen.print()
}

class Screen(private val width: Int, height: Int) {
    private val rows = MutableList(height) { MutableList(width) {0} }

    fun rect(wide: Int, tall: Int): Screen {
        (0 until tall).forEach { rowIndex -> (0 until wide).forEach { colIndex -> rows[rowIndex][colIndex] = 1 } }
        return this
    }

    fun rotateRow(rowIndex: Int, shifts: Int): Screen {
        rows[rowIndex] = rotate(rows[rowIndex], shifts)
        return this
    }

    fun rotateCol(colIndex: Int, shifts: Int): Screen {
        val col = rows.map { it[colIndex] }
        val rotatedCol = rotate(col, shifts)
        rows.forEachIndexed { rowIndex, row -> row[colIndex] = rotatedCol[rowIndex]}
        return this
    }

    fun print() {
        rows.forEach { println(it.map {cell -> if (cell == 0) '.' else '#'}.joinToString("")) }
    }

    fun checkCapacity(): Int = rows.map { it.sum() }.sum()

    private fun rotate(list: List<Int>, shifts: Int): MutableList<Int> =
        (list.subList(list.size - shifts, list.size) + list.subList(0, list.size - shifts)).toMutableList()
}
