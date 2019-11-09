import java.io.File

fun main() {
    val directions = File("src/day2.txt").readLines().map { line -> line.map { Direction.valueOf(it.toString()) } }
    println(RegularKeypad('5').getCode(directions))
    println(BathroomKeypad('5').getCode(directions))
}

abstract class Keypad {
    protected abstract var keys: MutableList<MutableList<out Char?>>
    protected abstract var rowIndex: Int
    protected abstract var colIndex: Int
    private val key: Char?
    get() = keys[rowIndex][colIndex]

    fun getCode(directions: List<List<Direction>>): String {
        var code = ""
        directions.forEach {
            goToKey(it)
            code += key
        }
        return code
    }

    private fun goToKey(directions: List<Direction>) {
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

class BathroomKeypad(initialKey: Char): Keypad() {
    override var keys = mutableListOf(
        mutableListOf(null, null, '1', null, null),
        mutableListOf(null, '2', '3', '4', null),
        mutableListOf('5', '6', '7', '8', '9'),
        mutableListOf(null, 'A', 'B', 'C', null),
        mutableListOf(null, null, 'D', null, null)
    )

    override var rowIndex = keys.indexOfFirst { it.contains(initialKey) }
    override var colIndex = keys[rowIndex].indexOfFirst { it == initialKey }
}

class RegularKeypad(initialKey: Char): Keypad() {
    override var keys: MutableList<MutableList<out Char?>> = mutableListOf(
        mutableListOf('1', '2', '3'),
        mutableListOf('4', '5', '6'),
        mutableListOf('7', '8', '9')
    )

    override var rowIndex = keys.indexOfFirst { it.contains(initialKey) }
    override var colIndex = keys[rowIndex].indexOfFirst { it == initialKey }
}
