package y2015.day23

import cpu.Computer
import cpu.Instruction
import java.io.File

fun main() {
    val instructions = File("src/y2015/day23/input.txt").readLines().map(::readInstruction)
    println(Computer().execute(instructions).getRegister("b"))
    println(Computer().setRegister("a", 1).execute(instructions).getRegister("b"))
}


private fun readInstruction(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "hlf" -> HalfInstruction(instruction)
    "tpl" -> TripleInstruction(instruction)
    "inc" -> IncrementInstruction(instruction)
    "jmp" -> JumpInstruction(instruction)
    "jie" -> JumpIfEvenInstruction(instruction)
    "jio" -> JumpIfOneInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

private class HalfInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it / 2}
    }
}

private class TripleInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it * 3}
    }
}

private class IncrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it + 1}
    }
}

private class JumpInstruction(val instruction: String): Instruction {
    override fun execute(computer: Computer) {
        computer.jump(offset)
    }

    val offset: Int
        get() = instruction.substringAfter(" ").toInt()
}

private class JumpIfEvenInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        if (computer.getRegister(register).isEven()) computer.jump(offset)
    }

    val offset: Int
        get() = instruction.substringAfter(", ").toInt()

    private fun Int.isEven(): Boolean = this % 2 == 0
}

private class JumpIfOneInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        if (computer.getRegister(register) == 1) computer.jump(offset)
    }

    val offset: Int
        get() = instruction.substringAfter(", ").toInt()
}

private interface RegisterInstruction {
    val instruction: String
    val register: String
        get() = instruction.substringAfter(" ").substring(0, 1)
}
