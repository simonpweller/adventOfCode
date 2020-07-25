package de.sweller.y2017.day19

import de.sweller.resourceLines
import de.sweller.y2017.day19.Diagram.FlowDirection.*
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException

fun main() {
    val diagram = Diagram(resourceLines(2017, 19))
    println(diagram.flow())
}

class Diagram(input: List<String>) {
    private val rows = input.map { row -> row.toCharArray().map { it.toCell() } }
    private var location = CellLocation(0, rows[0].indexOfFirst { it is VerticalPipe })
    private var flowDirection = DOWN

    fun flow(): Pair<String, Int> {
        var stepsTaken = 0
        val encounteredLetters = mutableListOf<Char>()
        while (true) {
            when (val currentCell = cellAt(location)) {
                is CrossingPipes -> flowDirection = when {
                    flowDirection != DOWN && cellAt(location + UP) !is NoPipe -> UP
                    flowDirection != LEFT && cellAt(location + RIGHT) !is NoPipe -> RIGHT
                    flowDirection != UP && cellAt(location + DOWN) !is NoPipe -> DOWN
                    flowDirection != RIGHT && cellAt(location + LEFT) !is NoPipe -> LEFT
                    else -> throw IllegalStateException("Nowhere to flow")
                }
                is Letter -> encounteredLetters += currentCell.letter
                is NoPipe -> return Pair(encounteredLetters.joinToString(""), stepsTaken)
            }
            location += flowDirection
            stepsTaken++
        }
    }

    private fun cellAt(location: CellLocation): Cell {
        return try {
            rows[location.row][location.col]
        } catch (e: IndexOutOfBoundsException) {
            NoPipe
        }
    }

    private data class CellLocation(val row: Int, val col: Int) {
        operator fun plus(flowDirection: FlowDirection): CellLocation = when (flowDirection) {
            DOWN -> this.copy(row = row + 1)
            RIGHT -> this.copy(col = col + 1)
            UP -> this.copy(row = row - 1)
            LEFT -> this.copy(col = col - 1)
        }
    }

    private enum class FlowDirection {
        DOWN,
        RIGHT,
        UP,
        LEFT
    }

    private fun Char.toCell(): Cell = when (this) {
        '-' -> HorizontalPipe
        '|' -> VerticalPipe
        '+' -> CrossingPipes
        ' ' -> NoPipe
        else -> Letter(this)
    }

    private interface Cell
    private object VerticalPipe : Cell
    private object HorizontalPipe : Cell
    private object NoPipe : Cell
    private object CrossingPipes : Cell
    private class Letter(val letter: Char) : Cell
}
