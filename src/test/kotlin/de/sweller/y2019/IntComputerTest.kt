package de.sweller.y2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
internal class IntComputerTest {

    @Test
    fun `running programs and reading memory0 works as expected`() {
        assertEquals(2, IntComputer("1,0,0,0,99").run().memory0)
        assertEquals(30, IntComputer("1,1,1,4,99,5,6,0,99").run().memory0)
    }

    @Test
    fun `setting noun and verb works as expected`() {
        val program = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,19,6,23,1,23,6,27,1,13,27,31,2,13,31,35,1,5,35,39,2,39,13,43,1,10,43,47,2,13,47,51,1,6,51,55,2,55,13,59,1,59,10,63,1,63,10,67,2,10,67,71,1,6,71,75,1,10,75,79,1,79,9,83,2,83,6,87,2,87,9,91,1,5,91,95,1,6,95,99,1,99,9,103,2,10,103,107,1,107,6,111,2,9,111,115,1,5,115,119,1,10,119,123,1,2,123,127,1,127,6,0,99,2,14,0,0"
        assertEquals(12490719, IntComputer(program).setNoun(12).setVerb(2).run().memory0)
    }

    @Test
    fun `reading input, giving output, position and immediate mode work as expected`() {
        val program = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"
        assertEquals(999, IntComputer(program).addInput(7).run().takeOutput())
        assertEquals(1000, IntComputer(program).addInput(8).run().takeOutput())
        assertEquals(1001, IntComputer(program).addInput(9).run().takeOutput())
    }

    @Test
    fun `large numbers and relative reading mode are handled`() {
        assertEquals(
            listOf<Long>(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99),
            IntComputer("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99")
                .run().takeOutputs()
        )
        assertEquals(
            1219070632396864L,
            IntComputer("1102,34915192,34915192,7,4,7,99,0").run().takeOutput()
        )
        assertEquals(
            1125899906842624L,
            IntComputer("104,1125899906842624,99").run().takeOutput()
        )
    }
}