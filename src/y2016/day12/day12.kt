package y2016.day12

import cpu.*

import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val instructions = File("src/y2016/day12/day12.txt").readLines().map(::readInstruction)
    println(Computer().execute(instructions).getRegister("a"))
    println(Computer().setRegister("c", 1).execute(instructions).getRegister("a"))
}

private fun readInstruction(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "cpy" -> CopyInstruction(instruction)
    "inc" -> IncrementInstruction(instruction)
    "dec" -> DecrementInstruction(instruction)
    "jnz" -> JumpIfNotZeroInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

interface RegisterInstruction {
    val instruction: String
    val register: String
        get() = instruction.substringAfter(" ").substring(0, 1)
}

interface InputInstruction {
    val instruction: String
    fun getInput(computer: Computer): Int {
        val input = instruction.substringAfter(" ").substringBefore(" ")
        return if (input.toIntOrNull() == null) { computer.getRegister(input) } else { input.toInt() }
    }
}

private class CopyInstruction(override val instruction: String) : Instruction, RegisterInstruction, InputInstruction {
    override fun execute(computer: Computer) {
        computer.setRegister(register, getInput(computer))
    }

    override val register: String
        get() = instruction.substringAfterLast(" ")
}

private class IncrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.setRegister(register, computer.getRegister(register) + 1)
    }
}

private class DecrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.setRegister(register, computer.getRegister(register) - 1)
    }
}

private class JumpIfNotZeroInstruction(override val instruction: String): Instruction, InputInstruction {
    val offset: Int
        get() = instruction.substringAfterLast(" ").toInt()

    override fun execute(computer: Computer) {
        if (getInput(computer) != 0) computer.jump(offset)
    }
}