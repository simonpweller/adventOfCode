package de.sweller.y2017.day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day10KtTest {

    @Test
    fun `hash should reverse a sublist of length starting at 0`() {
        assertEquals(listOf(1, 0, 2, 3, 4), hash((0 until 5).toList(), listOf(2)))
    }

    @Test
    fun `hash should shift the starting position after the first twist`() {
        assertEquals(listOf(1, 0, 3, 2, 4), hash((0 until 5).toList(), listOf(2, 2)))
    }

    @Test
    fun `hash should shift the starting position by 1 more for each twist after the first`() {
        assertEquals(listOf(1, 0, 2, 4, 3), hash((0 until 5).toList(), listOf(2, 0, 2)))
    }

    @Test
    fun `hash should wrap around at the end of the list`() {
        assertEquals(listOf(4, 3, 0, 1, 2), hash((0 until 5).toList(), listOf(3, 4)))
    }

    @Test
    fun `hash should work for the full example`() {
        assertEquals(listOf(3, 4, 2, 1, 0), hash((0 until 5).toList(), listOf(3, 4, 1, 5)))
    }
}