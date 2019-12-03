package y2019.day2

import resourceText
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

fun main() {
    val program = resourceText(2019, 2).split(",").map { it.toInt() }.toIntArray()
    println(IntComputer(program, 12, 2).run())
    println(part2(program))
}

fun part2(program: IntArray): Int {
    (0 .. 99).forEach { noun ->
        (0 .. 99).forEach { verb ->
                if (IntComputer(program, noun, verb).run() == 19690720) return 100 * noun + verb
        }
    }
    throw RuntimeException("No combination of noun and verb found that returns the correct value")
}

class IntComputer(program: IntArray, noun: Int, verb: Int) {
    private val memory = program.clone().apply { set(1, noun) }.apply { set(2, verb) }
    private var programCounter = 0

    fun run(): Int {
        while (opcode != 99) {
            when(opcode) {
                1 -> add()
                2 -> multiply()
                else -> throw IllegalArgumentException("opcode $opcode is not supported")
            }
            programCounter += 4
        }
        return memory[0]
    }

    private fun multiply() {
        target = operand1 * operand2
    }

    private fun add() {
        target = operand1 + operand2
    }

    private val opcode: Int
        get() = memory[programCounter]
    private val operand1: Int
        get() = memory[memory[programCounter + 1]]
    private val operand2: Int
        get() = memory[memory[programCounter + 2]]
    private var target
        get() = memory[memory[programCounter + 3]]
        set(value) {
            memory[memory[programCounter + 3]] = value
        }
}