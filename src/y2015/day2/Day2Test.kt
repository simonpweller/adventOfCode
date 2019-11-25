package day2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day2Test {

    @Test
    fun wrappingPaper() {
        assertEquals(58, Present(2, 3, 4).wrappingPaper())
        assertEquals(43, Present(1, 1, 10).wrappingPaper())
    }

    @Test
    fun ribbon() {
        assertEquals(34, Present(2,3,4).ribbon())
        assertEquals(14, Present(1,1,10).ribbon())
    }
}