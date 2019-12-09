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
        memory[read(1, ReadingMode.IMMEDIATE).toInt()] = inputs.removeAt(0)
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

    private val instruction: String
        get() = memory[programCounter].toString().padStart(4, '0')
    private val readingModes: List<ReadingMode>
        get() = instruction.take(2).map { ReadingMode.from(it) }
    private val opcode: Int
        get() = instruction.takeLast(2).toInt()
    private val operand1: Long
        get() = read(1, readingModes[1])
    private val operand2: Long
        get() = read(2, readingModes[0])
    private var target: Long
        get() = read(3, ReadingMode.IMMEDIATE)
        set(value) {
            memory[read(3, ReadingMode.IMMEDIATE).toInt()] = value
        }
    private fun read(offset: Int, readingMode: ReadingMode): Long = when(readingMode) {
            ReadingMode.POSITION -> memory[memory[programCounter + offset].toInt()]
            ReadingMode.IMMEDIATE -> memory[programCounter + offset]
        }
}

enum class ReadingMode {
    POSITION,
    IMMEDIATE;

    companion object {
        fun from(char: Char) = when(char) {
            '0' -> POSITION
            '1' -> IMMEDIATE
            else -> error("unknown reading mode")
        }
    }
}