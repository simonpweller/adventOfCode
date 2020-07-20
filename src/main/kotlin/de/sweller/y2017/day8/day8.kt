package de.sweller.y2017.day8

import de.sweller.cpu.Computer
import de.sweller.cpu.Instruction
import resourceLines

fun main() {
    val instructions = resourceLines(2017, 8).map(::readInstruction)
    val computer1 = Computer().execute(instructions)
    println(computer1.registers.values.max())

    val computer2 = Computer()
    println(instructions.flatMap {
        computer2.execute(it).registers.values
    }.max())
}

fun Computer.execute(instruction: Instruction): Computer {
    instruction.execute(this)
    return this
}

private fun readInstruction(instruction: String): Instruction = when(instruction.substringAfter(" ").substringBefore(" ")) {
    "inc" -> ConditionalIncrementInstruction(instruction)
    "dec" -> ConditionalDecrementInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

private class ConditionalDecrementInstruction(override val instruction: String) : Instruction, ConditionalInstruction {
    override fun execute(computer: Computer) {
        if (condition.evaluate(computer)) computer.operateOnRegister(register) {it - amount}
    }
}

private class ConditionalIncrementInstruction(override val instruction: String) : Instruction, ConditionalInstruction {
    override fun execute(computer: Computer) {
        if (condition.evaluate(computer)) computer.operateOnRegister(register) {it + amount}
    }
}

private interface ConditionalInstruction {
    val instruction: String
    val register: String
        get() = instruction.substringBefore(" ")
    val amount: Int
        get() = instruction.substringBefore(" if").substringAfterLast(" ").toInt()
    val condition: Condition
        get() = Condition(instruction.substringAfter("if "))
}

private class Condition(private val condition: String) {
    private val register: String
        get() = condition.substringBefore(" ")
    private val operator: String
        get() = condition.substringAfter(" ").substringBefore(" ")
    private val value: Long
        get() = condition.substringAfterLast(" ").toLong()
    fun evaluate(computer: Computer): Boolean {
        val registerValue = computer.getRegister(register)
        return when(operator) {
            ">" -> registerValue > value
            ">=" -> registerValue >= value
            "==" -> registerValue == value
            "!=" -> registerValue != value
            "<" -> registerValue < value
            "<=" -> registerValue <= value
            else -> throw java.lang.IllegalArgumentException("Unknown operator")
        }
    }
}

