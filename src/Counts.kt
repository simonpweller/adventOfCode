fun main() {
    println(charCounts("abba"))
}

fun charCounts(input: String): Map<Char, Int> =
    input.fold(mapOf()) { acc, c -> acc.plus(Pair(c, acc.getOrDefault(c, 0) + 1))}

fun intCount(input: List<Int>): Map<Int, Int> =
    input.fold(mapOf()) { acc, int -> acc.plus(Pair(int, acc.getOrDefault(int, 0 ) + 1))}