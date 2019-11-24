package y2016.day14

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val input = File("src/y2016/day14/input.txt").readText()
    println(generateKeys(input).take(64).last())
}

fun generateKeys(input: String) = sequence {
    val md5 = MessageDigest.getInstance("MD5")
    yieldAll(generateSequence(0) { it + 1 }
        .filter {index ->
            isKey(String.format("%032x", BigInteger(1, md5.digest("$input$index".toByteArray()))), input, index)
        })
}

fun isKey(hash: String, input: String, index: Int): Boolean {
    val tripletChar =  getTripletChar(hash)
    return if (tripletChar == null) false else hasQuintupletInNext1000Hashes(tripletChar, input, index)
}

fun hasQuintupletInNext1000Hashes(tripletChar: Char, input: String, index: Int): Boolean {
    val md5 = MessageDigest.getInstance("MD5")
    return (index + 1 .. index + 1000)
        .any { newIndex ->
            String.format("%032x", BigInteger(1, md5.digest("$input$newIndex".toByteArray()))).contains(tripletChar.toString().repeat(5))
        }
}

fun getTripletChar(hash: String): Char? = "([a-z\\d])\\1{2,}".toRegex().find(hash)?.value?.first()