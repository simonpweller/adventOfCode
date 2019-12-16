package y2019.day16

import resourceText

fun main() {
    val input = resourceText(2019, 16)
    println(FFT(input).transform(100).subList(0, 8).joinToString(""))
}

class FFT(val input: String) {
    private val basePattern = listOf(0, 1, 0, -1)

    private fun pattern(reps: Int, size: Int): List<Int> =
        basePattern.flatMap { sequenceOf(it).repeat().take(reps).toList() }.asSequence().repeat().drop(1).take(size).toList()

    fun transform(phases: Int): List<Int> {
        var output = input.map { it.toString().toInt() }
        repeat(phases) {
            output = (0 .. input.lastIndex).map { index ->
                output.zip(pattern(index + 1, input.length)) { a: Int, b: Int -> a * b}.sum().toString().takeLast(1).toInt()
            }
        }
        return output
    }
}

fun <T> Sequence<T>.repeat() : Sequence<T> = sequence {
    while(true) yieldAll(this@repeat)
}
