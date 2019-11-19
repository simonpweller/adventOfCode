package cpu

class Computer(var programCounter: Int = 0) {
    private val registers = mutableMapOf<String, Int>()
    fun getRegister(register: String): Int = registers.getOrDefault(register, 0)
    fun setRegister(register: String, value: Int) { registers[register] = value }

    fun execute(instructions: List<Instruction>) {
        while (instructions.size > programCounter) {
            instructions[programCounter].execute(this)
            programCounter++
        }
    }

    fun jump(offset: Int) {
        programCounter += offset - 1
    }
}

interface Instruction {
    fun execute(computer: Computer)
}