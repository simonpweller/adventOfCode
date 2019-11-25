package day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@Suppress("FunctionName")
internal class LookAndSayTest {

    @Test
    fun `1 becomes 11`() {
        assertEquals("11", lookAndSay("1"))
    }

    @Test
    fun `11 becomes 21`() {
        assertEquals("21", lookAndSay("11"))
    }

    @Test
    fun `21 becomes 1211`() {
        assertEquals("1211", lookAndSay("21"))
    }

    @Test
    fun `1211 becomes 111221`() {
        assertEquals("111221", lookAndSay("1211"))
    }

    @Test
    fun `111221 becomes 312211`() {
        assertEquals("312211", lookAndSay("111221"))
    }


}