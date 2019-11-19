package cpu

open class Computer(var programCounter: Int = 0) {
    val registers = mutableMapOf<String, Long>()
    fun getRegister(register: String): Long = registers.getOrDefault(register, 0)
    fun setRegister(register: String, value: Long): Computer {
        registers[register] = value
        return this
    }
    fun operateOnRegister(register: String, action: (Long) -> Long): Computer {
        registers[register] = action(registers.getOrDefault(register, 0))
        return this
    }

    open fun execute(instructions: List<Instruction>): Computer {
        while (instructions.size > programCounter) {
            instructions[programCounter].execute(this)
            programCounter++
        }
        return this
    }

    fun jump(offset: Int) {
        programCounter += offset - 1
    }
}

interface Instruction {
    fun execute(computer: Computer)
}