import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val input = File("src/day5.txt").readText()
    println(getFirstPassword(input))
    println(getSecondPassword(input))
}


fun getFirstPassword(input: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    var password = ""
    var index = 0
    while(password.length < 8) {
        val hash = String.format("%032x", BigInteger(1, md5.digest("$input$index".toByteArray())))
        if (hash.startsWith("00000")) password += hash[5]
        index++
    }
    return password
}

fun getSecondPassword(input: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    var password = "________"
    var index = 0
    while(password.contains('_')) {
        val hash = String.format("%032x", BigInteger(1, md5.digest("$input$index".toByteArray())))
        if (hash.startsWith("00000")) {
            if (hash[5] in '0' until '8') {
                val position = hash[5].toString().toInt()
                if (password[position] == '_') {
                    password = password.replaceRange(position, position + 1, hash[6].toString())
                }
            }
        }
        index++
    }
    return password
}