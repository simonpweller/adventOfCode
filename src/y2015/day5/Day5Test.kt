package day5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day5Test {

    @Test
    fun isNice1() {
        assertTrue(isNice1("aaa"))
    }

    @Test
    fun isNaughty1ForLessThanThreeVowels() {
        assertFalse(isNice1("dvszwmarrgswjxmb"))
    }

    @Test
    fun isNaughty1ForNoDoubleLetter() {
        assertFalse(isNice1("jchzalrnumimnmhp"))
    }

    @Test
    fun isNaughty1IfItContainsForbiddenStrings() {
        assertFalse(isNice1("haegwjzuvuyypxyu"))
    }

    @Test
    fun isNice2() {
        assertTrue(isNice2("qjhvhtzxzqqjkmpb"))
        assertTrue(isNice2("xxyxx"))
    }

    @Test
    fun isNaughty2IfNoRepeatingPair() {
        assertFalse(isNice2("ieodomkazucvgmuy"))
    }

    @Test
    fun isNaughty2IfNoPairWithSingleLetterInBetween() {
        assertFalse(isNice2("uurcxstgmygtbstg"))
    }
}