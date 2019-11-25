package day11

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

@Suppress("FunctionName")
internal class Day11Test {

    @Test
    fun `isValid returns true for valid passwords`() {
        assertTrue(isValid("abcdffaa"))
        assertTrue(isValid("ghjaabcc"))
    }

    @Test
    fun `isValid returns false for passwords containing the letters i, o or l`() {
        assertFalse(isValid("abcdffai"))
        assertFalse(isValid("abcdffao"))
        assertFalse(isValid("abcdffal"))
    }

    @Test
    fun `isValid returns false for passwords not containing at least one increasing straight of at least three letters`() {
        assertFalse(isValid("abbceffg"))
    }

    @Test
    fun `isValid returns false for passwords that don't contain at least two different, non-overlapping pairs of letters`() {
        assertFalse(isValid("abcdfgaa"))
        assertFalse(isValid("abcdfaaa"))
    }

    @Test
    fun `next increases the rightmost letter one step`() {
        assertEquals("c", next("b"))
        assertEquals("ab", next("aa"))
    }

    @Test
    fun `next wraps around to a if the rightmost letter was a z and continues until one doesn't wrap around`() {
        assertEquals("aba", next("aaz"))
        assertEquals("baa", next("azz"))
    }

    @Test
    fun `next throws IllegalArgumentException when password can't be incremented further`() {
        assertThrows(IllegalArgumentException::class.java) {
            next("z")
        }
    }

    @Test
    fun `nextValid returns next valid password after the one passed in`() {
        assertEquals("abcdffaa", nextValid("abcdefgh"))
        assertEquals("ghjaabcc", nextValid("ghijklmn"))
    }
}