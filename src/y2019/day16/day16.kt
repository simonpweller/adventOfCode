package y2019.day16

import resourceText

fun main() {
    val input = resourceText(2019, 16)
    println(part1(input))
}

private fun part1(input: String) = FFT(input).transform(100).subList(0, 8).joinToString("")

class FFT(val input: String) {
    private val basePattern = listOf(0, 1, 0, -1)
    private val multiplicationMaps = (1 .. input.length).map { reps ->
        basePattern.flatMap { sequenceOf(it).repeat().take(reps).toList() }.asSequence().repeat().drop(1).take(input.length).toList()
    }

    fun transform(phases: Int): List<Int> {
        var output = input.map { it.toString().toInt() }
        repeat(phases) {
            output = (0 .. input.lastIndex).map { digit ->
                output.foldIndexed(0) {index, acc, i -> acc + (i * multiplicationMaps[digit][index]) }.toString().takeLast(1).toInt()
            }
        }
        return output
    }
}

fun <T> Sequence<T>.repeat() : Sequence<T> = sequence {
    while(true) yieldAll(this@repeat)
}
