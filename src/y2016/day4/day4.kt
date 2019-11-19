package y2016.day4

import java.io.File

fun main() {
    val validRooms = File("src/y2016/day4/day4.txt").readLines().map(::Room).filter { it.isValid() }
    println(validRooms.sumBy{it.sectorId})
    println(validRooms.find { it.realName.contains("north") }?.sectorId)
}

class Room(private val encryptedName: String) {
    val sectorId = encryptedName.substringAfterLast("-").substringBefore("[").toInt()
    val realName: String
    get() = encryptedName.substringBeforeLast("-").map {
        when(it) {
            '-' -> ' '
            else -> {
                var newChar = it
                repeat(sectorId) { newChar = rotateForward(newChar)}
                newChar
            }
        }
    }.joinToString("")

    private val letterMap = encryptedName.substringBeforeLast("-").replace("-", "")
        .fold(mapOf<Char, Int>()) { map, char -> map.plus(Pair(char, map.getOrDefault(char, 0) + 1))}
    private val checksum = encryptedName.substringAfter("[").substringBefore("]")

    fun isValid(): Boolean = checksum == calculateCorrectChecksum()

    private fun rotateForward(char: Char): Char = if (char == 'z') 'a' else char.inc()

    private fun calculateCorrectChecksum(): String {
        return letterMap.toList().sortedBy { p -> p.first }.sortedByDescending { p -> p.second }
            .joinToString("") { p -> p.first.toString() }.substring(0, 5)
    }
}