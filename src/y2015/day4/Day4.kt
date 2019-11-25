package day4

import java.io.File
import java.security.MessageDigest
import java.math.BigInteger

fun main() {
    val input = File("src/y2015/day4/input.txt").readText()
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
