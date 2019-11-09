import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val input = File("src/day5.txt").readText()
    println(getPassword(input, ::passwordModifierPart1))
    println(getPassword(input, ::passwordModifierPart2))
}

fun getPassword(input: String, passwordModifier: (String, String) -> String): String {
    val md5 = MessageDigest.getInstance("MD5")
    var password = "________"
    var index = 0
    while(password.contains('_')) {
        val hash = String.format("%032x", BigInteger(1, md5.digest("$input$index".toByteArray())))
        if (hash.startsWith("00000")) password = passwordModifier(password, hash)
        index++
    }
    return password
}

private fun passwordModifierPart1(password: String, hash: String): String = password.replaceFirst('_', hash[5])

private fun passwordModifierPart2(password: String, hash: String): String {
    val value = hash.substring(6, 7)
    val position = hash[5].toString().toIntOrNull()
    return if (position is Int && position in 0 until 8 && password[position] == '_')
        password.replaceRange(position, position + 1, value)
        else password
}