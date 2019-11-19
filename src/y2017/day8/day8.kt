package y2017.day8

import cpu.Computer
import cpu.Instruction
import java.io.File

fun main() {
    val instructions = File("src/y2017/day8/input.txt").readLines().map(::readInstruction)
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

fun readInstruction(instruction: String): Instruction = when(instruction.substringAfter(" ").substringBefore(" ")) {
    "inc" -> ConditionalIncrementInstruction(instruction)
    "dec" -> ConditionalDecrementInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

class ConditionalDecrementInstruction(override val instruction: String) : Instruction, ConditionalInstruction {
    override fun execute(computer: Computer) {
        if (condition.evaluate(computer)) computer.operateOnRegister(register) {it - amount}
    }
}

class ConditionalIncrementInstruction(override val instruction: String) : Instruction, ConditionalInstruction {
    override fun execute(computer: Computer) {
        if (condition.evaluate(computer)) computer.operateOnRegister(register) {it + amount}
    }
}

interface ConditionalInstruction {
    val instruction: String
    val register: String
        get() = instruction.substringBefore(" ")
    val amount: Int
        get() = instruction.substringBefore(" if").substringAfterLast(" ").toInt()
    val condition: Condition
        get() = Condition(instruction.substringAfter("if "))
}

class Condition(private val condition: String) {
    private val register: String
        get() = condition.substringBefore(" ")
    private val operator: String
        get() = condition.substringAfter(" ").substringBefore(" ")
    private val value: Int
        get() = condition.substringAfterLast(" ").toInt()
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

