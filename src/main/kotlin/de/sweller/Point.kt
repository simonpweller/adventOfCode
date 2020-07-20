
import kotlin.math.abs
import kotlin.math.sign

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

    operator fun plus(direction: CardinalDirection): Point = when(direction) {
        CardinalDirection.N -> this.copy(y = y + 1)
        CardinalDirection.S -> this.copy(y = y - 1)
        CardinalDirection.W -> this.copy(x = x - 1)
        CardinalDirection.E -> this.copy(x = x + 1)
    }

    operator fun minus(point: Point): Vector = Vector(this.x - point.x, this.y - point.y)
}

data class Vector(val x: Int, val y: Int) {
    fun reduce(): Vector {
        if (x.sign == 0 || y.sign == 0) return Vector(x.sign, y.sign)
        val gcd = x.toBigInteger().gcd(y.toBigInteger()).toInt()
        return Vector(x / gcd, y / gcd)
    }
}

enum class RelativeDirection {
    U,
    D,
    L,
    R;

    operator fun plus(direction: RelativeDirection): RelativeDirection = when(direction) {
        U -> error("can't turn up")
        D -> error("can't turn down")
        L -> when(this) {
            U -> L
            D -> R
            L -> D
            R -> U
        }
        R -> when(this) {
            U -> R
            D -> L
            L -> U
            R -> D
        }
    }
}

enum class CardinalDirection {
    N,
    E,
    S,
    W
}
