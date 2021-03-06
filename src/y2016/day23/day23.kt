package y2016.day23

import resourceLines

fun main() {
    val instructions = resourceLines(2016, 23).map(::readInstruction).toMutableList()
    val computer = Computer(instructions)
    computer.setRegister("a", 7)
    computer.execute()
    val res = computer.getRegister("a")
    println(res)
}

class Computer(val instructions: MutableList<Instruction>) {
    var programCounter: Int = 0
    private val registers = mutableMapOf<String, Long>()
    fun getRegister(register: String): Long = registers.getOrDefault(register, 0)
    fun setRegister(register: String, value: Long): Computer {
        registers[register] = value
        return this
    }
    fun operateOnRegister(register: String, action: (Long) -> Long): Computer {
        registers[register] = action(registers.getOrDefault(register, 0))
        return this
    }

    fun jump(offset: Int) {
        programCounter += offset - 1
    }

    fun execute(): Computer {
        while (instructions.size > programCounter) {
            instructions[programCounter].execute(this)
            programCounter++
        }
        return this
    }
}

interface Instruction {
    fun execute(computer: Computer)
    fun toggle(): Instruction
}

private fun readInstruction(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "cpy" -> CopyInstruction(instruction)
    "inc" -> IncrementInstruction(instruction)
    "dec" -> DecrementInstruction(instruction)
    "jnz" -> JumpIfNotZeroInstruction(instruction)
    "tgl" -> ToggleInstruction(instruction)
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

    override fun toggle(): Instruction = JumpIfNotZeroInstruction(instruction)

    override val register: String
        get() = instruction.substringAfterLast(" ")
}

private class IncrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it + 1}
    }

    override fun toggle(): Instruction = DecrementInstruction(instruction)
}

private class DecrementInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it - 1}
    }

    override fun toggle(): Instruction = IncrementInstruction(instruction)
}

private class JumpIfNotZeroInstruction(override val instruction: String): Instruction, InputInstruction {
    val offset: String
        get() = instruction.substringAfterLast(" ")

    override fun execute(computer: Computer) {
        val offsetValue = if (offset.toIntOrNull() == null) computer.getRegister(offset).toInt() else offset.toInt()
        if (getInput(computer) != 0L) computer.jump(offsetValue)
    }

    override fun toggle(): Instruction = CopyInstruction(instruction)
}

private class ToggleInstruction(override val instruction: String): Instruction, RegisterInstruction {
    override fun execute(computer: Computer) {
        val instructionIndex = computer.programCounter + computer.getRegister(register).toInt()
        try {
            computer.instructions[instructionIndex] = computer.instructions[instructionIndex].toggle()
        } catch (e: IndexOutOfBoundsException) {}
    }

    override fun toggle(): Instruction = IncrementInstruction(instruction)
}
