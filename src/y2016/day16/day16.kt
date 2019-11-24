package y2016.day16

import java.io.File

fun main() {
    val input = File("src/y2016/day16/input.txt").readText()
    println(checksum(fakeData( 272, input)))
    println(checksum(fakeData( 35651584, input)))
}

tailrec fun fakeData(length: Int, a: String): String {
    val out = "${a}0${a.reversed().map { if(it == '1') '0' else '1' }.joinToString("")}"
    return if (out.length >= length) out.substring(0, length) else fakeData(length, out)
}

tailrec fun checksum(num: String): String {
    val sum = (0..num.lastIndex step 2).joinToString("") { index -> if (num[index] == num[index + 1]) "1" else "0" }
    return if (sum.length % 2 == 1) sum else checksum(sum)
}