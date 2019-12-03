import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    fun manhattan() = abs(x) + abs(y)

    operator fun plus(direction: RelativeDirection): Point = when(direction) {
        RelativeDirection.U -> this.copy(y = y + 1)
        RelativeDirection.D -> this.copy(y = y - 1)
        RelativeDirection.L -> this.copy(x = x - 1)
        RelativeDirection.R -> this.copy(x = x + 1)
    }
}

enum class RelativeDirection {
    U,
    D,
    L,
    R
}