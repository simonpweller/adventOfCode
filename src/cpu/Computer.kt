package cpu

class Computer(var programCounter: Int = 0) {
    val registers = mutableMapOf<String, Int>()
    fun getRegister(register: String): Int = registers.getOrDefault(register, 0)
    fun setRegister(register: String, value: Int): Computer {
        registers[register] = value
        return this
    }
    fun operateOnRegister(register: String, action: (Int) -> Int): Computer {
        registers[register] = action(registers.getOrDefault(register, 0))
        return this
    }

    fun execute(instructions: List<Instruction>): Computer {
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