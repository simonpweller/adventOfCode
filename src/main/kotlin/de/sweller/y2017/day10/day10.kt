package de.sweller.y2017.day10

import de.sweller.resourceText
import java.lang.Integer.max

fun main() {
    val input = (0 until 256).toList()
    val stringInput = resourceText(2017, 10)
    val lengths = stringInput.split(",").map { it.toInt() }
    println(hash(input, lengths)[0] * hash(input, lengths)[1])
    println(hash(input, processInput(stringInput), 64).run(::processOutput))
}

data class Hash(val values: List<Int>, val currentPosition: Int = 0, val skipSize: Int = 0)

fun hash(values: List<Int>, lengths: List<Int>, rounds: Int = 1): List<Int> {
    var hash = Hash(values)
    repeat(rounds) {
        lengths.forEach { hash = twist(hash, it) }
    }
    return hash.values
}

private fun twist(hash: Hash, length: Int): Hash {
    val (values, currentPosition, skipSize) = hash
    val nextValues = getNextValues(values, currentPosition, length)
    val nextPosition = (currentPosition + length + skipSize) % values.size
    val nextSkipSize = skipSize + 1
    return Hash(nextValues, nextPosition, nextSkipSize)
}

private fun getNextValues(values: List<Int>, currentPosition: Int, length: Int): List<Int> {
    val overflow = max(0, currentPosition + length - values.size)
    val reversedWrappedList = (values.subList(currentPosition, values.size) + values.subList(0, currentPosition))
            .take(length).reversed()

    return reversedWrappedList.takeLast(overflow) +
            values.subList(overflow, currentPosition) +
            reversedWrappedList.take(length - overflow) +
            values.takeLast(values.size - (currentPosition + length) + overflow)
}

private fun processInput(string: String): List<Int> = string.map { it.toInt() } + listOf(17, 31, 73, 47, 23)
private fun processOutput(output: List<Int>) = output.chunked(16)
        .map { block -> block.reduce { a, b -> a.xor(b) } }
        .joinToString("") { num -> num.toString(16).padStart(2, '0') }