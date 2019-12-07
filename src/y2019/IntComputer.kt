package y2019

class IntComputer(program: IntArray) {
    private val memory = program.clone()
    private var programCounter = 0
    val inputs = mutableListOf<Int>()
    val outputs = mutableListOf<Int>()

    fun run(): Int {
        while (opcode != 99) {
            when (opcode) {
                1 -> add()
                2 -> multiply()
                3 -> input()
                4 -> output()
                5 -> jumpIfTrue()
                6 -> jumpIfFalse()
                7 -> lessThan()
                8 -> equals()
                else -> throw IllegalArgumentException("opcode $opcode is not supported")
            }
        }
        return memory[0]
    }

    private fun add() {
        target = operand1 + operand2
        programCounter += 4
    }

    private fun multiply() {
        target = operand1 * operand2
        programCounter += 4
    }

    private fun input() {
        memory[memory[programCounter + 1]] = inputs.removeAt(0)
        programCounter += 2
    }

    private fun output() {
        outputs.add(operand1)
        programCounter += 2
    }

    private fun jumpIfTrue() {
        if (operand1 != 0) programCounter = operand2 else programCounter += 3
    }

    private fun jumpIfFalse() {
        if (operand1 == 0) programCounter = operand2 else programCounter += 3
    }

    private fun lessThan() {
        target = if (operand1 < operand2) 1 else 0
        programCounter += 4
    }

    private fun equals() {
        target = if (operand1 == operand2) 1 else 0
        programCounter += 4
    }

    private val opcode: Int
        get() = memory[programCounter].toString().padStart(2, '0').takeLast(2).toInt()
    private val operand1: Int
        get() {
            val mode = memory[programCounter].toString().padStart(3, '0').takeLast(3).take(1).toInt()
            return if (mode == 1) memory[programCounter + 1] else memory[memory[programCounter + 1]]
        }
    private val operand2: Int
        get() {
            val mode = memory[programCounter].toString().padStart(4, '0').takeLast(4).take(1).toInt()
            return if (mode == 1) memory[programCounter + 2] else memory[memory[programCounter + 2]]
        }
    private var target: Int
        get() = memory[memory[programCounter + 3]]
        set(value) {
            memory[memory[programCounter + 3]] = value
        }
}