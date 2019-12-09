package y2019

class IntComputer(program: String) {
    private val memory = program.split(",").map { it.toLong() }.mapIndexed { index, l -> index.toLong() to l }.toMap().toMutableMap()
    private var programCounter = 0L
    private var relativeBase = 0L
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
                9 -> incrementRelativeBase()
                else -> throw IllegalArgumentException("opcode $opcode is not supported")
            }
        }
        isDone = true
        return this
    }


    fun readMemory0(): Long = memory.getOrElse(0) { 0L }
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
        operand3 = operand1 + operand2
        programCounter += 4
    }

    private fun multiply() {
        operand3 = operand1 * operand2
        programCounter += 4
    }

    private fun input() {
        operand1 = inputs.removeAt(0)
        programCounter += 2
    }

    private fun output() {
        outputs.add(operand1)
        programCounter += 2
    }

    private fun jumpIfTrue() {
        if (operand1 != 0L) programCounter = operand2 else programCounter += 3
    }

    private fun jumpIfFalse() {
        if (operand1 == 0L) programCounter = operand2 else programCounter += 3
    }

    private fun lessThan() {
        operand3 = if (operand1 < operand2) 1 else 0
        programCounter += 4
    }

    private fun equals() {
        operand3 = if (operand1 == operand2) 1 else 0
        programCounter += 4
    }

    private fun incrementRelativeBase() {
        relativeBase += operand1
        programCounter += 2
    }

    private val instruction: String
        get() = memory[programCounter].toString().padStart(5, '0')
    private val readingModes: List<ReadingMode>
        get() = instruction.take(3).map { ReadingMode.from(it) }
    private val opcode: Int
        get() = instruction.takeLast(2).toInt()
    private var operand1: Long
        get() = read(1, readingModes[2])
        set(value) {
            write(1, readingModes[2], value)
        }
    private var operand2: Long
        get() = read(2, readingModes[1])
        set(value) {
            write(2, readingModes[1], value)
        }
    private var operand3: Long
        get() = read(3, readingModes[0])
        set(value) {
            write(3, readingModes[0], value)
        }
    private fun read(offset: Int, readingMode: ReadingMode): Long = when(readingMode) {
            ReadingMode.POSITION -> memory.getOrDefault(read(offset, ReadingMode.IMMEDIATE), 0L)
            ReadingMode.IMMEDIATE -> memory.getOrDefault(programCounter + offset, 0L)
            ReadingMode.RELATIVE -> memory.getOrDefault(read(offset, ReadingMode.IMMEDIATE) + relativeBase, 0L)
        }
    private fun write(offset: Int, readingMode: ReadingMode, value: Long) {
        when(readingMode) {
            ReadingMode.POSITION -> memory[read(offset, ReadingMode.IMMEDIATE)] = value
            ReadingMode.IMMEDIATE -> throw NotImplementedError("writing in immediate mode is not supported")
            ReadingMode.RELATIVE -> memory[read(offset, ReadingMode.IMMEDIATE) + relativeBase] = value
        }
    }
}

enum class ReadingMode {
    POSITION,
    IMMEDIATE,
    RELATIVE;

    companion object {
        fun from(char: Char) = when(char) {
            '0' -> POSITION
            '1' -> IMMEDIATE
            '2' -> RELATIVE
            else -> error("unknown reading mode")
        }
    }
}