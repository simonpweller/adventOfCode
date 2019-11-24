package y2016.day14

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val input = File("src/y2016/day14/input.txt").readText()
    println(generateKeys(input).take(64).last())
    println(generateKeys(input, true).take(64).last())
}

class Hashes(private val input: String, private val stretch: Boolean) {
    private val map = mutableMapOf<Int, String>()
    private val md5 = MessageDigest.getInstance("MD5")

    fun get(index: Int) =
        map.getOrPut(index) {
            var hash = String.format("%032x", BigInteger(1, md5.digest("$input$index".toByteArray())))
            if (stretch) {
                repeat(2016) { hash = String.format("%032x", BigInteger(1, md5.digest(hash.toByteArray())))}
            }
            hash
        }
}

fun generateKeys(input: String, stretch: Boolean = false) = sequence {
    val hashes = Hashes(input, stretch)
    yieldAll(generateSequence(0) { it + 1 }.filter { index -> isKey(hashes, index)})
}

private fun isKey(hashes: Hashes, index: Int): Boolean {
    val searchString = getTripletChar(hashes.get(index))?.toString()?.repeat(5)
    return if (searchString == null) false else (index + 1..index + 1000).any { hashes.get(it).contains(searchString) }
}

fun getTripletChar(hash: String): Char? = "([a-z\\d])\\1{2,}".toRegex().find(hash)?.value?.first()