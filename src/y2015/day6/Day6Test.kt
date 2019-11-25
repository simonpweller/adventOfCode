package day6

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day6Test {
    @Test
    fun parseLine() {
        val instruction = parseLine("turn off 660,55 through 986,197")
        assertEquals(Action.TURN_OFF, instruction.action)
        assertEquals(660, instruction.x0)
        assertEquals(986, instruction.x1)
        assertEquals(55, instruction.y0)
        assertEquals(197, instruction.y1)
    }
}