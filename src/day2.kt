import java.io.File

fun main() {
    val position = RegularKeypad('5')
    File("src/day2.txt").forEachLine { line ->
        position.move(line.map { Direction.valueOf(it.toString()) })
        print(position.key)
    }
}



class RegularKeypad(key: Char) {
    val key: Char
    get() = keys[rowIndex][colIndex]

    private var keys = mutableListOf(
        mutableListOf('1', '2', '3'),
        mutableListOf('4', '5', '6'),
        mutableListOf('7', '8', '9')
    )

    private var rowIndex = keys.indexOfFirst { it.contains(key) }
    private var colIndex = keys[rowIndex].indexOfFirst { it == key }

    fun move(directions: List<Direction>) {
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