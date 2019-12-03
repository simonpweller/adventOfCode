package y2015.day18

import resourceLines

fun main() {
    val lines = resourceLines(2015, 18)

    val grid = Grid(100, 100)
    setUpGrid(grid, lines)
    repeat((1..100).count()) {grid.animate()}
    println(grid.getCells().filter { it.state == CellState.ON }.size)

    val brokenGrid = Grid(100, 100, true)
    setUpGrid(brokenGrid, lines)
    repeat((1..100).count()) {brokenGrid.animate()}
    println(brokenGrid.getCells().filter { it.state == CellState.ON }.size)
}

private fun setUpGrid(grid: Grid, lines: List<String>) {
    lines.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, cell ->
            grid.getCell(rowIndex, colIndex)!!.state = when (cell) {
                '#' -> CellState.ON
                else -> CellState.OFF
            }
        }
    }
}

class Grid (internal val height: Int, internal val width: Int, internal val isBroken: Boolean = false) {
    private val rows: Array<Array<Cell>> = Array(width) { rowIndex -> Array(height) { colIndex -> Cell(rowIndex, colIndex, this) } }
    fun animate() {
        getCells().forEach { it.calculateNextState() }
        getCells().forEach { it.updateState() }
    }

    fun getCell(row: Int, col: Int): Cell? = try {
        rows[row][col]
    } catch (e: ArrayIndexOutOfBoundsException) {
        null
    }

    fun getCells(): List<Cell> =
        (0 until height).map {rowIndex ->
            (0 until width).map { colIndex ->
                getCell(rowIndex, colIndex)
            }
        }.flatten().filterNotNull()

    fun getNeighbors(row: Int, col: Int): List<Cell> =
        (-1..1).map { rowOffset ->
            (-1..1).map { colOffset ->
                if (rowOffset == 0 && colOffset == 0) null else getCell(row + rowOffset, col + colOffset)
            }
        }.flatten().filterNotNull()
}

data class Cell(val row: Int, val col: Int, val grid: Grid) {
    var state: CellState = CellState.OFF
    var nextState: CellState = CellState.OFF

    fun calculateNextState() {
        if (grid.isBroken && (row == 0 || row == grid.width - 1) && (col == 0 || col == grid.height - 1)) {
            nextState = CellState.ON
            return
        }

        val switchedOnNeighbors = grid.getNeighbors(row, col).filter { it.state == CellState.ON }.size
        nextState = if (state == CellState.ON) {
            if (switchedOnNeighbors == 2 || switchedOnNeighbors == 3) CellState.ON else CellState.OFF
        } else {
            if (switchedOnNeighbors == 3) CellState.ON else CellState.OFF
        }
    }

    fun updateState() {
        state = nextState
    }
}

enum class CellState {
    ON,
    OFF
}
