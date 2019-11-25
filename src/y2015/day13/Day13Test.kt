package day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13Test {

    @Test
    fun parseHappinessImpact() {
        val lines = listOf(
            "Alice would gain 54 happiness units by sitting next to Bob.",
            "Alice would lose 79 happiness units by sitting next to Carol.",
            "Alice would lose 2 happiness units by sitting next to David."
        )

        assertEquals(54, parseHappinessImpact(lines).getValue("Alice")["Bob"])
        assertEquals(-79, parseHappinessImpact(lines).getValue("Alice")["Carol"])
        assertEquals(-2, parseHappinessImpact(lines).getValue("Alice")["David"])
    }
}