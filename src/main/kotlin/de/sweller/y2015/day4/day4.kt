package de.sweller.y2015.day4

import de.sweller.resourceText
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val input = resourceText(2015, 4)
    println(solve(input, "00000"))
    println(solve(input, "000000"))
}

fun solve(input: String, prefix: String): Int {
    val md5 = MessageDigest.getInstance("MD5")
    var num = 1
    while(true) {
        val hash = String.format("%032x", BigInteger(1, md5.digest("$input$num".toByteArray())))
        if (hash.startsWith(prefix)) return num
        num++
    }
}
