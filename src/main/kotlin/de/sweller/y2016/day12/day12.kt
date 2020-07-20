package de.sweller.y2016.day12

import de.sweller.cpu.Computer
import de.sweller.cpu.Instruction
import de.sweller.resourceLines

fun main() {
    val instructions = resourceLines(2016, 12).map(::readInstruction)
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

private interface RegisterInstruction {
    val instruction: String
    val register: String
        get() = instruction.substringAfter(" ").substring(0, 1)
}

private interface InputInstruction {
    val instruction: String
    fun getInput(computer: Computer): Long {
        val input = instruction.substringAfter(" ").substringBefore(" ")
        return if (input.toLongOrNull() == null) { computer.getRegister(input) } else { input.toLong() }
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
        computer.operateOnRegister(register) {it + 1}
    }
}

private class DecrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it - 1}
    }
}

private class JumpIfNotZeroInstruction(override val instruction: String): Instruction, InputInstruction {
    val offset: Int
        get() = instruction.substringAfterLast(" ").toInt()

    override fun execute(computer: Computer) {
        if (getInput(computer) != 0L) computer.jump(offset)
    }
}