package de.sweller.y2016.day21

import de.sweller.resourceLines

fun main() {
    val operations = resourceLines(2016, 21).map {
        when(it.substringBefore(" ")) {
            "swap" -> SwapOperation(it)
            "rotate" -> RotateOperation(it)
            "reverse" -> ReverseOperation(it)
            "move" -> MoveOperation(it)
            else -> throw IllegalArgumentException("Unsupported operation $it")
        }
    }

    println(Scrambler(operations).scramble("abcdefgh"))
    println(Scrambler(operations).unscramble("fbgdceah"))
}

class Scrambler(private val operations: List<Operation>) {
    fun scramble(password: String): String = operations.fold(password) { pw, operation -> operation.apply(pw) }
    fun unscramble(scrambledPassword: String): String =
        operations.reversed().fold(scrambledPassword) { scrambledPw, operation -> operation.reverse(scrambledPw) }
}
interface Operation {
    val operation: String
    val words
        get() = operation.split(" ")

    fun apply(input: String): String
    fun reverse(input: String): String
}

class SwapOperation(override val operation: String): Operation {
    override fun apply(input: String): String {
        val x: String
        val y: String
        val xPos: Int
        val yPos: Int
        if (words[1] == "letter") {
            x = words[2]
            y = words[5]
            xPos = input.indexOfFirst { it.toString() == x }
            yPos = input.indexOfFirst { it.toString() == y }
        } else {
            xPos = words[2].toInt()
            yPos = words[5].toInt()
            x = input[xPos].toString()
            y = input[yPos].toString()
        }
        return input.replaceRange(xPos, xPos + 1, y).replaceRange(yPos, yPos + 1, x)
    }

    override fun reverse(input: String): String = apply(input)
}

class ReverseOperation(override val operation: String) : Operation {
    override fun apply(input: String): String {
        val startIndex = words[2].toInt()
        val endIndex = words[4].toInt() + 1

        return input.replaceRange(startIndex, endIndex, input.subSequence(startIndex, endIndex).reversed())
    }

    override fun reverse(input: String): String = apply(input)
}

class RotateOperation(override val operation: String) : Operation {
    override fun apply(input: String): String {
        return when(words[1]) {
            "left" -> rotateLeft(input, words[2].toInt())
            "right" -> rotateRight(input, words[2].toInt())
            else -> rotateBasedOn(input, words[6])
        }
    }

    override fun reverse(input: String): String {
        return when(words[1]) {
            "left" -> rotateRight(input, words[2].toInt())
            "right" -> rotateLeft(input, words[2].toInt())
            else -> reverseBasedOn(input, words[6])
        }
    }

    private fun rotateLeft(input: String, steps: Int): String {
        val netSteps = steps % input.length
        return input.drop(netSteps) + input.take(netSteps)
    }

    private fun rotateRight(input: String, steps: Int): String {
        val netSteps = steps % input.length
        return input.takeLast(netSteps) + input.dropLast(netSteps)
    }

    private fun rotateBasedOn(input: String, letter: String): String {
        val index = input.indexOfFirst { it.toString() == letter }
        return rotateRight(input, 1 + index + if (index >= 4) 1 else 0)
    }

    private fun reverseBasedOn(input: String, s: String): String =
        generateSequence(input) {rotateLeft(it, 1)}.find { rotateBasedOn(it, s) == input }
            ?: throw IllegalArgumentException("RotateOperation cannot be reversed")
}

class MoveOperation(override val operation: String) : Operation {
    override fun apply(input: String): String = move(input, words[2].toInt(), words[5].toInt())
    override fun reverse(input: String): String = move(input, words[5].toInt(), words[2].toInt())

    private fun move(input: String, source: Int, dest: Int): String {
        val letter = input.substring(source, source + 1)
        val remainingInput = input.take(source) + input.substring(source + 1)
        return remainingInput.substring(0, dest) + letter + remainingInput.substring(dest)
    }
}
