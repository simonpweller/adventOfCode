package y2016.day12

import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val lines = File("src/y2016/day12/day12.txt").readLines()
    val computerPart1 = Computer()
    computerPart1.execute(lines)
    println(computerPart1.getRegister("a"))

    val computerPart2 = Computer()
    computerPart2.setRegister("c", 1)
    computerPart2.execute(lines)
    println(computerPart2.getRegister("a"))
}

class Computer(var programCounter: Int = 0) {
    private val registers = mutableMapOf<String, Int>()
    fun getRegister(register: String): Int = registers.getOrDefault(register, 0)
    fun setRegister(register: String, value: Int) { registers[register] = value }

    fun execute(rawInstructions: List<String>) {
        val instructions = rawInstructions.map { readInstruction(it) }

        while (instructions.size > programCounter) {
            val jumped = instructions[programCounter].execute(this)
            if (!jumped) programCounter++
        }
    }

    fun jump(offset: Int) {
        programCounter += offset
    }

    private fun readInstruction(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
        "cpy" -> CopyInstruction(instruction)
        "inc" -> IncrementInstruction(instruction)
        "dec" -> DecrementInstruction(instruction)
        "jnz" -> JumpIfNotZeroInstruction(instruction)
        else -> throw IllegalArgumentException("Unknown instruction type")
    }
}

private class CopyInstruction(override val instruction: String) : Instruction, RegisterInstruction, InputInstruction {
    override fun execute(computer: Computer): Jumped {
        computer.setRegister(register, getInput(computer))
        return false
    }

    override val register: String
        get() = instruction.substringAfterLast(" ")
}

private typealias Jumped = Boolean

private class IncrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer): Jumped {
        computer.setRegister(register, computer.getRegister(register) + 1)
        return false
    }
}

private class DecrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer): Jumped {
        computer.setRegister(register, computer.getRegister(register) - 1)
        return false
    }
}

private class JumpIfNotZeroInstruction(override val instruction: String): Instruction, OffsetInstruction, InputInstruction {
    override fun execute(computer: Computer): Jumped {
        return if (getInput(computer) != 0) {
            computer.jump(offset)
            true
        } else {
            false
        }
    }
}

private interface Instruction {
    fun execute(computer: Computer): Jumped
}

private interface RegisterInstruction {
    val instruction: String
    val register: String
        get() = instruction.substringAfter(" ").substring(0, 1)
}

private interface InputInstruction {
    val instruction: String
    fun getInput(computer: Computer): Int {
        val input = instruction.substringAfter(" ").substringBefore(" ")
        return if (input.toIntOrNull() == null) { computer.getRegister(input) } else { input.toInt() }
    }
}

private interface OffsetInstruction {
    val instruction: String
    val offset: Int
        get() = instruction.substringAfterLast(" ").toInt()
}