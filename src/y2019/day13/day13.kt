package y2019.day13

import resourceText
import y2019.IntComputer

fun main() {
    val intComp = IntComputer(resourceText(2019, 13))
    val screen = mutableMapOf<Pair<Long, Long>, Long>()
    while (!intComp.isDone) {
        intComp.run().takeOutputs().chunked(3).forEach { screen[Pair(it[0], it[1])] =  it[2] }
    }
    println(screen.values.count {it == 2L})
}