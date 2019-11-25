
package day8

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Suppress("FunctionName")
internal class DayTest {

    @Test
    fun `totalCharacters should calculate the right number of total characters`() {
        val lines = listOf("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"")
        assertEquals(23, totalCharacters(lines))
    }

    @Test
    fun `charactersInMemory should calculate the right number of in memory characters`() {
        val lines = listOf("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"")
        assertEquals(11, charactersInMemory(lines))
    }

    @Test
    fun `charactersInMemory should strip surrounding double quotes`() {
        val lines = listOf("\"\"")
        assertEquals(0, charactersInMemory(lines))
    }

    @Test
    fun `charactersInMemory should keep the string data`() {
        val lines = listOf("\"abc\"")
        assertEquals(3, charactersInMemory(lines))
    }

    @Test
    fun `charactersInMemory should replace escaped quotes with unescaped quotes`() {
        val lines = listOf("\"aaa\\\"aaa\"")
        assertEquals(7, charactersInMemory(lines))
    }

    @Test
    fun `charactersInMemory should replace escaped hexadecimal characters with their ascii representation`() {
        val lines = listOf("\"\\x27\"")
        assertEquals(1, charactersInMemory(lines))
    }

    @Test
    fun `encodedCharacters should calculate the right number of in memory characters`() {
        val lines = listOf("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"")
        assertEquals(42, encodedCharacters(lines))
    }

    @Test
    fun `encodedCharacters should encode the surrounding double quotes`() {
        val lines = listOf("\"\"")
        assertEquals(6, encodedCharacters(lines))
    }

    @Test
    fun `encodedCharacters should keep the string data`() {
        val lines = listOf("\"abc\"")
        assertEquals(9, encodedCharacters(lines))
    }

    @Test
    fun `encodedCharacters should replace escaped quotes with unescaped quotes`() {
        val lines = listOf("\"aaa\\\"aaa\"")
        assertEquals(16, encodedCharacters(lines))
    }

    @Test
    fun `encodedCharacters should replace escaped hexadecimal characters with their ascii representation`() {
        val lines = listOf("\"\\x27\"")
        assertEquals(11, encodedCharacters(lines))
    }
}