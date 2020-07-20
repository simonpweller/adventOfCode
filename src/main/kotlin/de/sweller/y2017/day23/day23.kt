package de.sweller.y2017.day23

import de.sweller.cpu.Computer
import de.sweller.cpu.Instruction
import isPrime
import resourceLines

fun main() {
    Computer().execute(resourceLines(2017, 23).map(::readInstruction))
    println(multOps)
    println((109300 .. 126300 step 17).count{!isPrime(it)}) // based on manually translating the assembly code
}

var multOps = 0

fun readInstruction(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "set" -> SetInstruction(instruction)
    "sub" -> SubtractInstruction(instruction)
    "mul" -> MultiplyInstruction(instruction)
    "jnz" -> JumpIfNotZeroInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

class SetInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {getSecondValue(computer)}
    }
}

class SubtractInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it - getSecondValue(computer)}
    }
}

class MultiplyInstruction(override val instruction: String) : Instruction, RegisterInterface, FirstValueInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {getFirstValue(computer) * getSecondValue(computer)}
        multOps++
    }
}

class JumpIfNotZeroInstruction(override val instruction: String) : Instruction, RegisterInterface, FirstValueInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        if (getFirstValue(computer) != 0L) {
            computer.jump(getSecondValue(computer).toInt())
        }
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
