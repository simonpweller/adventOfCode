import java.io.File

fun main() {
    val directions = File("src/day2.txt").readLines().map { line -> line.map { Direction.valueOf(it.toString()) } }
    println(RegularKeypad('5').getCode(directions))
    println(BathroomKeypad('5').getCode(directions))
}

class BathroomKeypad(key: Char) {
    private var keys = mutableListOf(
        mutableListOf(null, null, '1', null, null),
        mutableListOf(null, '2', '3', '4', null),
        mutableListOf('5', '6', '7', '8', '9'),
        mutableListOf(null, 'A', 'B', 'C', null),
        mutableListOf(null, null, 'D', null, null)
    )

    private val key: Char?
    get() = keys[rowIndex][colIndex]

    private var rowIndex = keys.indexOfFirst { it.contains(key) }
    private var colIndex = keys[rowIndex].indexOfFirst { it == key }

    fun getCode(directions: List<List<Direction>>): String {
        var code = ""
        directions.forEach {
            move(it)
            code += key
        }
        return code
    }

    private fun move(directions: List<Direction>) {
        directions.forEach { move(it) }
    }

    private fun move(direction: Direction) {
        when {
            direction == Direction.U && keys.getOrNull(rowIndex - 1)?.getOrNull(colIndex) != null -> rowIndex--
            direction == Direction.D && keys.getOrNull(rowIndex + 1)?.getOrNull(colIndex) != null -> rowIndex++
            direction == Direction.L && keys.getOrNull(rowIndex)?.getOrNull(colIndex - 1) != null -> colIndex--
            direction == Direction.R && keys.getOrNull(rowIndex)?.getOrNull(colIndex + 1) != null -> colIndex++
        }
    }
}

class RegularKeypad(key: Char) {
    private var keys = mutableListOf(
        mutableListOf('1', '2', '3'),
        mutableListOf('4', '5', '6'),
        mutableListOf('7', '8', '9')
    )

    private val key: Char
    get() = keys[rowIndex][colIndex]

    private var rowIndex = keys.indexOfFirst { it.contains(key) }
    private var colIndex = keys[rowIndex].indexOfFirst { it == key }

    fun getCode(directions: List<List<Direction>>): String {
        var code = ""
        directions.forEach {
            move(it)
            code += key
        }
        return code
    }

    private fun move(directions: List<Direction>) {
        directions.forEach { move(it) }
    }

    private fun move(direction: Direction) {
        when {
            direction == Direction.U && keys.getOrNull(rowIndex - 1)?.getOrNull(colIndex) != null -> rowIndex--
            direction == Direction.D && keys.getOrNull(rowIndex + 1)?.getOrNull(colIndex) != null -> rowIndex++
            direction == Direction.L && keys.getOrNull(rowIndex)?.getOrNull(colIndex - 1) != null -> colIndex--
            direction == Direction.R && keys.getOrNull(rowIndex)?.getOrNull(colIndex + 1) != null -> colIndex++
        }
    }
}

enum class Direction {
    U,
    D,
    L,
    R
}