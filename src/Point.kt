import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    val neighbors: List<Point>
        get() = RelativeDirection.values().map { this + it }
    fun manhattan(point: Point = Point(0, 0) ) = abs(x - point.x) + abs(y - point.y)

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