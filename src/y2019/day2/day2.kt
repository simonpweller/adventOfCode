package y2019.day2

import java.io.File
import kotlin.system.exitProcess

fun main() {
    val program = File("src/y2019/day2/input.txt").readText().split(",").map { it.toInt() }.toIntArray()
    println(testInitialValues(program, 12, 2))
    (0 .. 99).forEach { noun ->
        (0 .. 99).forEach { verb ->
            if (testInitialValues(program, noun, verb) == 19690720) {
                println(100 * noun + verb)
                exitProcess(0)
            }
        }
    }
}

fun testInitialValues(program: IntArray, noun: Int, verb: Int): Int {
    val programToTest = program.clone()
    programToTest[1] = noun
    programToTest[2] = verb
    val intComputer = IntComputer(programToTest)
    intComputer.run()
    return intComputer.program[0]
}

class IntComputer(val program: IntArray) {
    var programCounter = 0
    private val opcode: Int
        get() = program[programCounter]
    private val operand1: Int
        get() = program[programCounter + 1]
    private val operand2: Int
        get() = program[programCounter + 2]
    private val target: Int
        get() = program[programCounter + 3]

    fun run(): Int {
        while (true) {
            when(opcode) {
                1 -> add()
                2 -> multiply()
                99 -> return 0
                else -> return -1
            }
            programCounter += 4
        }
    }

    private fun multiply() {
        program[target] = program[operand1] * program[operand2]
    }

    private fun add() {
        program[target] = program[operand1] + program[operand2]
    }
}