package y2017.day18

import cpu.Computer
import cpu.Instruction
import java.io.File
import kotlin.system.exitProcess

fun main() {
    val instructions = File("src/y2017/day18/input.txt").readLines().map(::readInstruction)
    Computer().execute(instructions)
}

private fun readInstruction(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "snd" -> SoundInstruction(instruction)
    "set" -> SetInstruction(instruction)
    "add" -> AddInstruction(instruction)
    "mul" -> MultiplyInstruction(instruction)
    "mod" -> ModuloInstruction(instruction)
    "rcv" -> RecoveryInstruction(instruction)
    "jgz" -> JumpIfGreaterThanZeroInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

var mostRecentSound = 0L

fun playSound(frequency: Long) {
    mostRecentSound = frequency
}

private class SoundInstruction(override val instruction: String) : Instruction, FirstValueInterface {
    override fun execute(computer: Computer) {
        playSound(this.getFirstValue(computer))
    }
}

private class SetInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {this.getSecondValue(computer)}
    }
}

private class AddInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it + this.getSecondValue(computer)}
    }
}

private class MultiplyInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it * this.getSecondValue(computer)}
    }
}

private class ModuloInstruction(override val instruction: String) : Instruction, RegisterInterface, FirstValueInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {this.getFirstValue(computer) % this.getSecondValue(computer)}
    }
}

private class RecoveryInstruction(override val instruction: String) : Instruction, FirstValueInterface {
    override fun execute(computer: Computer) {
        if (this.getFirstValue(computer) != 0L) {
            println(mostRecentSound)
            exitProcess(0)
        }
    }
}

private class JumpIfGreaterThanZeroInstruction(override val instruction: String) : Instruction, FirstValueInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        if (this.getFirstValue(computer) > 0) computer.jump(this.getSecondValue(computer).toInt())
    }
}

private interface FirstValueInterface {
    val instruction: String
    fun getFirstValue(computer: Computer): Long {
        val value = instruction.substringAfter(" ").substringBefore(" ")
        return if (value.toLongOrNull() == null) { computer.getRegister(value) } else { value.toLong() }
    }
}

private interface RegisterInterface {
    val instruction: String
    val register: String
        get() = instruction.substringAfter(" ").substringBefore(" ")
}

private interface SecondValueInterface {
    val instruction: String
    fun getSecondValue(computer: Computer): Long {
        val value = instruction.substringAfterLast(" ")
        return if (value.toLongOrNull() == null) { computer.getRegister(value) } else { value.toLong() }
    }
}
