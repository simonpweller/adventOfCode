package y2019

class IntComputer(program: String) {
    private val memory = program.split(",").map { it.toLong() }.toLongArray()
    private var programCounter = 0
    var isDone = false
    val inputs = mutableListOf<Long>()
    var outputs = mutableListOf<Long>()

    fun run(): IntComputer {
        while (opcode != 99) {
            when (opcode) {
                1 -> add()
                2 -> multiply()
                3 -> try {
                    input()
                } catch (e: IndexOutOfBoundsException) { return this }
                4 -> output()
                5 -> jumpIfTrue()
                6 -> jumpIfFalse()
                7 -> lessThan()
                8 -> equals()
                else -> throw IllegalArgumentException("opcode $opcode is not supported")
            }
        }
        isDone = true
        return this
    }

    fun readMemory0(): Long = memory[0]
    fun setNoun(noun: Int): IntComputer {
        memory[1] = noun.toLong()
        return this
    }
    fun setVerb(verb: Int): IntComputer {
        memory[2] = verb.toLong()
        return this
    }
    fun addInput(input: Long): IntComputer {
        inputs.add(input)
        return this
    }
    fun addInput(input: List<Long>): IntComputer {
        inputs.addAll(input)
        return this
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
        memory[memory[programCounter + 1].toInt()] = inputs.removeAt(0)
        programCounter += 2
    }

    private fun output() {
        outputs.add(operand1)
        programCounter += 2
    }

    private fun jumpIfTrue() {
        if (operand1 != 0L) programCounter = operand2.toInt() else programCounter += 3
    }

    private fun jumpIfFalse() {
        if (operand1 == 0L) programCounter = operand2.toInt() else programCounter += 3
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
    private val operand1: Long
        get() {
            val mode = memory[programCounter].toString().padStart(3, '0').takeLast(3).take(1).toInt()
            return if (mode == 1) memory[programCounter + 1] else memory[memory[programCounter + 1].toInt()]
        }
    private val operand2: Long
        get() {
            val mode = memory[programCounter].toString().padStart(4, '0').takeLast(4).take(1).toInt()
            return if (mode == 1) memory[programCounter + 2] else memory[memory[programCounter + 2].toInt()]
        }
    private var target: Long
        get() = memory[memory[programCounter + 3].toInt()]
        set(value) {
            memory[memory[programCounter + 3].toInt()] = value
        }
}