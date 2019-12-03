package y2015.day7

import resourceLines

private const val MAX_SIGNAL = 65535

fun main() {
    val lines = resourceLines(2015, 7)
    val wireAResult = Circuit(lines).emulate().getWire("a")
    println(wireAResult)
    println(Circuit(lines).setWire("b", wireAResult!!).emulate().getWire("a"))
}

class Circuit(lines: List<String>) {
    private val instructions = mutableListOf<Instruction>()
    private val wires = mutableMapOf<String, Int>()

    init {
        lines.forEach{instructions.add(Instruction(it))}
    }

    fun getWire(identifier: String): Int? {
        return wires[identifier]
    }

    fun setWire(identifier: String, value: Int): Circuit {
        wires[identifier] = value
        return this
    }

    fun emulate(): Circuit {
        while(wires.size < instructions.size) {
            instructions
                .filter { !wires.containsKey(it.output)}
                .forEach {
                    val input1 = getNumericInput(it.input1) ?: return@forEach
                    val input2 = getNumericInput(it.input2)
                    when {
                        it.operator == Operator.NUMERIC -> wires[it.output] = input1
                        it.operator == Operator.NOT -> wires[it.output] = MAX_SIGNAL - input1
                        it.operator == Operator.AND && input2 != null -> wires[it.output] = input1.and(input2)
                        it.operator == Operator.OR && input2 != null -> wires[it.output] = input1.or(input2)
                        it.operator == Operator.LSHIFT && input2 != null -> wires[it.output] = input1.shl(input2)
                        it.operator == Operator.RSHIFT && input2 != null -> wires[it.output] = input1.shr(input2)
                    }
                }
        }
        return this
    }

    private fun getNumericInput(input: String): Int? {
        return input.toIntOrNull() ?: getWire(input)
    }
}

class Instruction(instructionString: String) {
    val operator: Operator?
    val output: String
    var input1 = ""
    var input2 = ""

    init {
        val (input, output) = instructionString.split(" -> ")
        this.output = output
        val inputs = input.split(" ")
        when (inputs.size) {
            1 -> {
                input1 = inputs[0]
                operator = Operator.NUMERIC
            }
            2 -> {
                operator = Operator.NOT
                input1 = inputs[1]
            }
            else -> {
                input1 = inputs[0]
                operator = Operator.valueOf(inputs[1])
                input2 = inputs[2]
            }
        }
    }
}

enum class Operator {
    NUMERIC,
    NOT,
    AND,
    OR,
    LSHIFT,
    RSHIFT
}



