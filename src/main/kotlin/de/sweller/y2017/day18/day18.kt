package de.sweller.y2017.day18

import de.sweller.cpu.Computer
import de.sweller.cpu.Instruction
import de.sweller.resourceLines

fun main() {
    InterruptibleComputer().execute(resourceLines(2017, 18).map(::readInstructionPart1))

    val part2Instructions = resourceLines(2017, 18).map(::readInstructionPart2)
    val program1 = ConcurrentComputer()
    val program0 = ConcurrentComputer()
    program0.partnerProgram = program1
    program1.partnerProgram = program0
    program1.setRegister("p", 1)
    do {
        program0.execute(part2Instructions)
        program1.execute(part2Instructions)
    } while (program0.queue.isNotEmpty() || program1.queue.isNotEmpty())
    println(program1.numberOfSends)
}

class InterruptibleComputer(programCounter: Int = 0): Computer(programCounter) {
    override fun execute(instructions: List<Instruction>): Computer {
        try {
            super.execute(instructions)
        } finally {
            return this
        }
    }
}

class ConcurrentComputer(programCounter: Int = 0, var partnerProgram: ConcurrentComputer? = null): Computer(programCounter) {
    var queue = listOf<Long>()
    var numberOfSends = 0

    override fun execute(instructions: List<Instruction>): Computer {
        while (instructions.size > programCounter) {
            if (
                programCounter < 0 ||
                programCounter > instructions.lastIndex ||
                (queue.isEmpty() && instructions[programCounter] is ReceiveInstruction)
            ) {
                return this
            }
            instructions[programCounter].execute(this)
            programCounter++
        }
        return this
    }
}

private fun readInstructionPart1(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "snd" -> SoundInstruction(instruction)
    "set" -> SetInstruction(instruction)
    "add" -> AddInstruction(instruction)
    "mul" -> MultiplyInstruction(instruction)
    "mod" -> ModuloInstruction(instruction)
    "rcv" -> RecoveryInstruction(instruction)
    "jgz" -> JumpIfGreaterThanZeroInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

private fun readInstructionPart2(instruction: String): Instruction = when(instruction.substringBefore(" ")) {
    "snd" -> SendInstruction(instruction)
    "set" -> SetInstruction(instruction)
    "add" -> AddInstruction(instruction)
    "mul" -> MultiplyInstruction(instruction)
    "mod" -> ModuloInstruction(instruction)
    "rcv" -> ReceiveInstruction(instruction)
    "jgz" -> JumpIfGreaterThanZeroInstruction(instruction)
    else -> throw IllegalArgumentException("Unknown instruction type")
}

var mostRecentSound = 0L

fun playSound(frequency: Long) {
    mostRecentSound = frequency
}

private class SoundInstruction(override val instruction: String) : Instruction, FirstValueInterface {
    override fun execute(computer: Computer) {
        playSound(this.getFirstValue(computer))
    }
}

private class SetInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {this.getSecondValue(computer)}
    }
}

private class AddInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it + this.getSecondValue(computer)}
    }
}

private class MultiplyInstruction(override val instruction: String) : Instruction, RegisterInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {it * this.getSecondValue(computer)}
    }
}

private class ModuloInstruction(override val instruction: String) : Instruction, RegisterInterface, FirstValueInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        computer.operateOnRegister(register) {this.getFirstValue(computer) % this.getSecondValue(computer)}
    }
}

private class RecoveryInstruction(override val instruction: String) : Instruction, FirstValueInterface {
    override fun execute(computer: Computer) {
        if (this.getFirstValue(computer) != 0L) {
            println(mostRecentSound)
            throw Exception("That's enough now")
        }
    }
}

private class JumpIfGreaterThanZeroInstruction(override val instruction: String) : Instruction, FirstValueInterface, SecondValueInterface {
    override fun execute(computer: Computer) {
        if (this.getFirstValue(computer) > 0) computer.jump(this.getSecondValue(computer).toInt())
    }
}

class SendInstruction(override val instruction: String) : Instruction, FirstValueInterface {
    override fun execute(computer: Computer) {
        if (computer is ConcurrentComputer) {
            computer.numberOfSends++
            computer.partnerProgram!!.queue = computer.partnerProgram!!.queue.plus(this.getFirstValue(computer))
        } else {
            throw IllegalStateException("Executing SendInstruction on a machine that's not a ConcurrentComputer")
        }
    }
}

class ReceiveInstruction(override val instruction: String) : Instruction, RegisterInterface {
    override fun execute(computer: Computer) {
        if (computer is ConcurrentComputer) {
            computer.setRegister(register, computer.queue.first())
            computer.queue = computer.queue.subList(1, computer.queue.size)
        } else {
            throw IllegalStateException("Executing SendInstruction on a machine that's not a ConcurrentComputer")
        }
    }
}

private interface FirstValueInterface {
    val instruction: String
    fun getFirstValue(computer: Computer): Long {
        val value = instruction.substringAfter(" ").substringBefore(" ")
        return if (value.toLongOrNull() == null) { computer.getRegister(value) } else { value.toLong() }
    }
}

private interface RegisterInterface {
    val instruction: String
    val register: String
        get() = instruction.substringAfter(" ").substringBefore(" ")
}

private interface SecondValueInterface {
    val instruction: String
    fun getSecondValue(computer: Computer): Long {
        val value = instruction.substringAfterLast(" ")
        return if (value.toLongOrNull() == null) { computer.getRegister(value) } else { value.toLong() }
    }
}
