package de.sweller.y2017.day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

    @Test
    fun `getSeverity should return 24 for the example`() {
        assertEquals(24, getSeverity(mapOf(0 to Scanner(3), 1 to Scanner(2), 4 to Scanner(4), 6 to Scanner(4))))
    }
}