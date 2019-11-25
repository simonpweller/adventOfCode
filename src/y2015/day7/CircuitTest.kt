package day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@Suppress("FunctionName")
internal class CircuitTest {
    private val instructions = listOf(
        "123 -> x",
        "456 -> y",
        "x AND y -> d",
        "x OR y -> e",
        "x LSHIFT 2 -> f",
        "y RSHIFT 2 -> g",
        "NOT x -> h",
        "NOT y -> i"
    )
    private val circuit = Circuit(instructions).emulate()

    @Test
    fun `wires connected to numeric inputs should return the input`() {
        assertEquals(123, circuit.getWire("x"))
        assertEquals(456, circuit.getWire("y"))
    }

    @Test
    fun `wires connected to a NOT gate should return the bitwise complement of their input`() {
        assertEquals(65412, circuit.getWire("h"))
        assertEquals(65079, circuit.getWire("i"))
    }

    @Test
    fun `wires connected to an AND gate should return the bitwise AND of the input wires`() {
        assertEquals(72, circuit.getWire("d"))
    }

    @Test
    fun `wires connected to an OR gate should return the bitwise OR of the input wires`() {
        assertEquals(507, circuit.getWire("e"))
    }

    @Test
    fun `wires connected to an LSHIFT gate should return the first input left-shifted by the second input`() {
        assertEquals(492, circuit.getWire("f"))
    }

    @Test
    fun `wires connected to an RSHIFT gate should return the first input right-shifted by the second input`() {
        assertEquals(114, circuit.getWire("g"))
    }

    @Test
    fun `instructions given out of order should be handled`() {
        assertEquals(123, Circuit(instructions.reversed()).emulate().getWire("x"))
        assertEquals(114, Circuit(instructions.reversed()).emulate().getWire("g"))
    }
}