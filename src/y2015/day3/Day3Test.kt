package day3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day3Test {

    @Test
    fun part1() {
        assertEquals(2, part1(">"))
        assertEquals(4, part1("^>v<"))
        assertEquals(2, part1("^v^v^v^v^v"))
    }

    @Test
    fun part2() {
        assertEquals(3, part2("^v"))
        assertEquals(3, part2("^>v<"))
        assertEquals(11, part2("^v^v^v^v^v"))
    }
}