package y2019.day13

import resourceText
import y2019.IntComputer

fun main() {
//    println(part1(resourceText(2019, 13)))
    val targets = mutableListOf<Long>(17, 16, 16, 17, 10, 2, 6, 6, 4, 4, 10, 8, 8, 12, 12, 12, 12, 12, 12, 12, 12, 12,
        12, 12, 12, 12, 20, 20, 12, 12, 22, 22, 22, 22, 20, 18, 24, 32, 32, 14, 31, 30, 28, 28, 28, 28, 28, 28, 28, 28, 8, 29, 30,
        30, 26, 26, 30, 27, 20, 18, 18, 18, 18, 16, 16, 16, 16, 16,16,16,16,7,29,30,20,20,12,22,24,21,20, 26, 25, 24, 25, 20, 25, 3, 2, 30, 16, 29, 7, 16, 7, 29, 15, 14,
        14, 8, 15, 29, 7, 17, 18, 18, 18, 18, 27, 5
    )
    val arcade = Arcade(resourceText(2019, 13), 2, targets)
    arcade.run()
    println(arcade.segmentDisplay)
}

private class Arcade(program: String, quarters: Int, val targets: MutableList<Long>) {
    val intComp = IntComputer(program).apply { this.memory0 = quarters.toLong() }
    val screen = mutableMapOf<Pair<Long, Long>, Long>()
    var nextTarget = targets.removeAt(0)
    var segmentDisplay = 0L
    val pixels: List<List<Pair<Long, Long>>> = (0L..24L).map { y -> (0L..34L).map { x -> Pair(x, y) } }
    val ballPosition: Pair<Long, Long>
        get() = screen.filterValues { it == 4L }.keys.first()
    val playerPosition: Pair<Long, Long>
        get() = screen.filterValues { it == 3L }.keys.first()
    var hitsNextTurn = false
    var lastBallPosition = 0L
    var lastScore = 0L

    fun run(): Pair<Long, Long> {
        tick()
        while (!intComp.isDone) {
            print()
            when {
                playerPosition.first > nextTarget -> left()
                playerPosition.first < nextTarget -> right()
                else -> neutral()
            }
            tick()
        }
        return Pair(lastScore, lastBallPosition)
    }

    fun tick() {
        if (hitsNextTurn) {
            if (targets.size > 0) {
                nextTarget = targets.removeAt(0)
            }
            hitsNextTurn = false
        }
        intComp.run().takeOutputs().chunked(3).forEach {
            if (it[0] == -1L && it[1] == 0L) {
                segmentDisplay = it[2]
            } else screen[Pair(it[0], it[1])] = it[2]
        }
        if (ballPosition.second == 22L) {
            hitsNextTurn = true
        }
    }

    fun left() {
        intComp.addInput(-1L)
    }

    fun right() {
        intComp.addInput(1L)
    }

    fun neutral() {
        intComp.addInput(0L)
    }

    fun print() {
        println(pixels.joinToString("\n") { row -> row.joinToString("", transform = ::pixel) })
        println(segmentDisplay)
        println("player: $playerPosition")
        println("ball: $ballPosition")
        println("target: $nextTarget")
        lastBallPosition = ballPosition.first
        lastScore = segmentDisplay
    }

    private fun pixel(pixel: Pair<Long, Long>): String = when(screen[pixel]) {
        0L -> " "
        1L -> "#"
        2L -> "*"
        3L -> "_"
        4L -> "O"
        else -> error("unknown pixel")
    }
}

private fun part1(program: String): Int {
    val intComp = IntComputer(program)
    val screen = mutableMapOf<Pair<Long, Long>, Long>()
    intComp.run().takeOutputs().chunked(3).forEach { screen[Pair(it[0], it[1])] = it[2] }
    return screen.values.count { it == 2L }
}